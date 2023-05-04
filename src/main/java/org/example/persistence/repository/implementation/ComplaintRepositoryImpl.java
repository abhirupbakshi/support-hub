package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.ComplaintRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

public class ComplaintRepositoryImpl implements ComplaintRepository {

    @Override
    public Complaint get(int id) throws NotFoundException {

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            Complaint result = em.find(Complaint.class, id);
            em.getTransaction().commit();

            if (result == null) {
                throw new NotFoundException("No complain found with id: " + id);
            }

            return result.setAssignedEngineers(null);
        } catch (IllegalArgumentException exception) {
            throw new NotFoundException("No complain found with id: " + id, exception);
        }
    }

    @Override
    public List<Complaint> getAll() {

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            List<Complaint> result = em.createQuery("SELECT c FROM Complaint c", Complaint.class).getResultList();
            em.getTransaction().commit();

            result.forEach(c -> {
                c.setAssignedEngineers(null);
            });

            return result;
        }
    }

    @Override
    public List<Engineer> getAssignedEngineers(int id) throws NotFoundException {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Complaint result = em.find(Complaint.class, id);
            em.getTransaction().commit();

            if (result == null) {
                throw new NotFoundException("No complaint found with id: " + id);
            }

            List<Engineer> engineers = result.getAssignedEngineers();
            engineers.forEach(em::detach);

            return engineers;
        } catch (IllegalArgumentException exception) {
            throw new NotFoundException("No complaint found with id: " + id, exception);
        }
    }

    @Override
    public void createComplain(String description, User createdBy, String categoryName, String statusName) throws NotFoundException {

        if(createdBy == null) {
            throw new IllegalArgumentException("Created by user cannot be null");
        }

        try(EntityManager em = EMUtils.getEM()) {

            Category category = em.find(Category.class, categoryName);
            Status status = em.find(Status.class, statusName);

            if(category == null) {
                throw new NotFoundException("Category not found: " + categoryName);
            }

            if(status == null) {
                throw new NotFoundException("Status not found: " + statusName);
            }

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

        try (EntityManager em = EMUtils.getEM()) {
            Category category = em.find(Category.class, categoryName);

            if(category == null) {
                throw new NotFoundException("Category not found: " + categoryName);
            }

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

        try (EntityManager em = EMUtils.getEM()) {
            Status status = em.find(Status.class, statusName);

            if(status == null) {
                throw new NotFoundException("Status not found: " + statusName);
            }

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
    public void addUpdate(int complainId, String message) throws NotFoundException {

        if(message == null) {
            throw new IllegalArgumentException("Message cannot be null");
        }

        // For checking if the complaint exist or not
        get(complainId);

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();

            Complaint complaint = em.find(Complaint.class, complainId);
            complaint.getUpdates().add(Update.builder().message(message).build());
            em.merge(complaint);

            em.getTransaction().commit();
        }
    }
}
