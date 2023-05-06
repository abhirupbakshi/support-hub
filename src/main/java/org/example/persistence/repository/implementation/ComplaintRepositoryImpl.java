package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.ComplaintRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

/**
 * <h3>Class ComplaintRepositoryImpl</h3>
 * This class is used to implement the methods of {@link ComplaintRepository}.
 */
public class ComplaintRepositoryImpl implements ComplaintRepository {

    @Override
    public Complaint get(int id) throws NotFoundException {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Complaint result = em.find(Complaint.class, id);
            em.getTransaction().commit();

            if (result == null)
                throw new NotFoundException("No complain found with id: " + id);

            return result.setAssignedEngineers(null);
        }
    }

    @Override
    public List<Complaint> getAll() {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            List<Complaint> result = em.createQuery("SELECT c FROM Complaint c", Complaint.class)
                    .getResultList();
            em.getTransaction().commit();

            result.forEach(c -> c.setAssignedEngineers(null));

            return result;
        }
    }

    @Override
    public List<Engineer> getAssignedEngineers(int complaintId) throws NotFoundException {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Complaint result = em.find(Complaint.class, complaintId);
            em.getTransaction().commit();

            if (result == null)
                throw new NotFoundException("No complaint found with id: " + complaintId);

            result.getAssignedEngineers().forEach(em::detach);

            return result.getAssignedEngineers();
        }
    }

    @Override
    public void createComplain(String description, String createdByUserEmail, String categoryName, String statusName) throws NotFoundException {

        if(description == null)
            throw new IllegalArgumentException("description parameter cannot be null");
        if(createdByUserEmail == null)
            throw new IllegalArgumentException("createdByUserEmail parameter cannot be null");
        if(categoryName == null)
            throw new IllegalArgumentException("categoryName parameter cannot be null");
        if(statusName == null)
            throw new IllegalArgumentException("statusName parameter cannot be null");

        try(EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            User createdBy = em.find(User.class, createdByUserEmail);
            Category category = em.find(Category.class, categoryName);
            Status status = em.find(Status.class, statusName);
            em.getTransaction().commit();

            if(createdBy == null)
                throw new NotFoundException("User not found with email: " + createdByUserEmail);
            if(category == null)
                throw new NotFoundException("Category not found: " + categoryName);
            if(status == null)
                throw new NotFoundException("Status not found: " + statusName);

            Complaint complaint = Complaint.builder()
                    .description(description)
                    .createdBy(createdBy)
                    .category(category)
                    .status(status)
                    .build();

            em.getTransaction().begin();
            em.persist(complaint);
            em.getTransaction().commit();
        }
    }

    @Override
    public void setDescription(int id, String description) throws NotFoundException {

        if(description == null)
            throw new IllegalArgumentException("description parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            if(em.createQuery("UPDATE Complaint c SET c.description = :description WHERE c.id = :id")
                    .setParameter("description", description)
                    .setParameter("id", id)
                    .executeUpdate() < 1) {
                throw new NotFoundException("No complaint found with id: " + id);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public void setCategory(int id, String categoryName) throws NotFoundException {

        if(categoryName == null)
            throw new IllegalArgumentException("categoryName parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Category category = em.find(Category.class, categoryName);
            em.getTransaction().commit();

            if(category == null)
                throw new NotFoundException("Category not found: " + categoryName);

            em.getTransaction().begin();

            if(em.createQuery("UPDATE Complaint c SET c.category = :category WHERE c.id = :id")
                    .setParameter("category", category)
                    .setParameter("id", id)
                    .executeUpdate() < 1) {
                throw new NotFoundException("No complaint found with id: " + id);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public void setStatus(int id, String statusName) throws NotFoundException {

        if(statusName == null)
            throw new IllegalArgumentException("statusName parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Status status = em.find(Status.class, statusName);
            em.getTransaction().commit();

            if(status == null)
                throw new NotFoundException("Status not found: " + statusName);

            em.getTransaction().begin();

            if(em.createQuery("UPDATE Complaint c SET c.status = :status WHERE c.id = :id")
                    .setParameter("status", status)
                    .setParameter("id", id)
                    .executeUpdate() < 1) {
                throw new NotFoundException("No complaint found with id: " + id);
            }

            em.getTransaction().commit();
        }
    }

    @Override
    public void assignExistingEngineer(String email, int complainId) throws NotFoundException, AlreadyExistException {
        new EngineerRepositoryImpl().assignExistingComplain(email, complainId);
    }

    @Override
    public void removeAssignedEngineer(String email, int complainId) throws NotFoundException {
        new EngineerRepositoryImpl().removeAssignedComplain(email, complainId);
    }

    @Override
    public void addUpdate(int complaintId, String message) throws NotFoundException {

        if(message == null)
            throw new IllegalArgumentException("Message cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            Complaint complaint = em.find(Complaint.class, complaintId);

            if (complaint == null)
                throw new NotFoundException("No complain found with id: " + complaintId);

            complaint.getUpdates().add(Update.builder().message(message).build());

            em.getTransaction().commit();
        }
    }
}