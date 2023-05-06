package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.utilities.EMUtils;
import org.example.persistence.repository.EngineerRepository;
import java.util.List;

/**
 * <h3>Class EngineerRepositoryImpl</h3>
 * This is a {@link EngineerRepository} implementation which also extends {@link EmployeeRepositoryImpl}.
 */
public class EngineerRepositoryImpl extends EmployeeRepositoryImpl<Engineer> implements EngineerRepository {

    @Override
    public Engineer get(String email) throws NotFoundException {

        return super.get(email, Engineer.class).setAssignedComplaints(null);
    }

    @Override
    public List<Engineer> getAll() {

        List<Engineer> engineers = super.getAll(Engineer.class);
        engineers.forEach(engineer -> engineer.setAssignedComplaints(null));
        return engineers;
    }

    @Override
    public List<Complaint> getAssignedComplains(String email) throws NotFoundException {

        if (email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Engineer result = em.find(Engineer.class, email);
            em.getTransaction().commit();

            if (result == null)
                throw new NotFoundException("No engineer found with email: " + email);

            result.getAssignedComplaints().forEach(em::detach);

            return result.getAssignedComplaints();
        }
    }

    @Override
    public void setForename(String email, String forename) throws NotFoundException {

        super.setForename(email, forename, Engineer.class);
    }

    @Override
    public void setSurname(String email, String surname) throws NotFoundException {

        super.setSurname(email, surname, Engineer.class);
    }

    @Override
    public void addAddress(String email, Address address) throws NotFoundException {

        super.addAddress(email, address, Engineer.class);
    }

    @Override
    public void removeAddress(String email, Address address) throws NotFoundException {

        super.removeAddress(email, address, Engineer.class);
    }

    @Override
    public void updateAddress(String email, Address oldAddress, Address newAddress) throws NotFoundException {

        super.updateAddress(email, oldAddress, newAddress, Engineer.class);
    }

    @Override
    public void addPhone(String email, Phone phone) throws NotFoundException {

        super.addPhone(email, phone, Engineer.class);
    }

    @Override
    public void removePhone(String email, Phone phone) throws NotFoundException {

        super.removePhone(email, phone, Engineer.class);
    }

    @Override
    public void updatePhone(String email, Phone oldPhone, Phone newPhone) throws NotFoundException {

        super.updatePhone(email, oldPhone, newPhone, Engineer.class);
    }

    @Override
    public void assignExistingComplain(String email, int complainId) throws NotFoundException, AlreadyExistException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            Engineer engineer = em.find(Engineer.class, email);
            Complaint complaint = em.find(Complaint.class, complainId);

            if(engineer == null)
                throw new NotFoundException("No engineer found with email: " + email);
            if(complaint == null)
                throw new NotFoundException("No complaint found with id: " + complainId);
            if(engineer.getAssignedComplaints().contains(complaint) || complaint.getAssignedEngineers().contains(engineer))
                    throw new AlreadyExistException("Complaint with id: " + complainId +
                            " is already assigned to the engineer with email: " + email);

            engineer.getAssignedComplaints().add(complaint);
            complaint.getAssignedEngineers().add(engineer);

            em.getTransaction().commit();
        }
    }

    @Override
    public void removeAssignedComplain(String email, int complainId) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            Engineer engineer = em.find(Engineer.class, email);
            Complaint complaint = em.find(Complaint.class, complainId);

            if(engineer == null)
                throw new NotFoundException("No engineer found with email: " + email);
            if(complaint == null)
                throw new NotFoundException("No complaint found with id: " + complainId);
            if(!engineer.getAssignedComplaints().contains(complaint) || !complaint.getAssignedEngineers().contains(engineer))
                throw new NotFoundException("Complaint with id: " + complainId +
                        " is not assigned to the engineer with email: " + email);

            engineer.getAssignedComplaints().remove(complaint);
            complaint.getAssignedEngineers().remove(engineer);

            em.getTransaction().commit();
        }
    }
}
