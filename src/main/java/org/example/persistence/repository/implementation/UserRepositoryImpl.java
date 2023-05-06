package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.UserRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

public class UserRepositoryImpl extends EmployeeRepositoryImpl<User> implements UserRepository {

    @Override
    public User get(String email) throws NotFoundException {

        return super.get(email, User.class).setCreatedComplaints(null);
    }

    @Override
    public List<User> getAll() {

        List<User> users = super.getAll(User.class);
        users.forEach(user -> user.setCreatedComplaints(null));
        return users;
    }

    @Override
    public List<Complaint> getCreatedComplains(String email) throws NotFoundException {

        if (email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            User result = em.find(User.class, email);
            em.getTransaction().commit();

            if (result == null)
                throw new NotFoundException("No user found with email: " + email);

            result.getCreatedComplaints().forEach(em::detach);

            return result.getCreatedComplaints();
        }
    }

    @Override
    public void setForename(String email, String forename) throws NotFoundException {

        super.setForename(email, forename, User.class);
    }

    @Override
    public void setSurname(String email, String surname) throws NotFoundException {

        super.setSurname(email, surname, User.class);
    }

    @Override
    public void addAddress(String email, Address address) throws NotFoundException {

        super.addAddress(email, address, User.class);
    }

    @Override
    public void removeAddress(String email, Address address) throws NotFoundException {

        super.removeAddress(email, address, User.class);
    }

    @Override
    public void updateAddress(String email, Address oldAddress, Address newAddress) throws NotFoundException {

        super.updateAddress(email, oldAddress, newAddress, User.class);
    }

    @Override
    public void addPhone(String email, Phone phone) throws NotFoundException {

        super.addPhone(email, phone, User.class);
    }

    @Override
    public void removePhone(String email, Phone phone) throws NotFoundException {

        super.removePhone(email, phone, User.class);
    }

    @Override
    public void updatePhone(String email, Phone oldPhone, Phone newPhone) throws NotFoundException {

        super.updatePhone(email, oldPhone, newPhone, User.class);
    }
}
