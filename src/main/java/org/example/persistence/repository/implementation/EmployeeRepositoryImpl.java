package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.Address;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.utilities.EMUtils;
import org.example.persistence.repository.EmployeeRepository;
import java.util.List;

/**
 * This class is used to implement the methods of {@link EmployeeRepository} but with a generic context. Hence,
 * it does not implement EmployeeRepository itself, and this abstract class is only meant to be extended by the
 * {@link Employee} and it's children.
 * @param <T> The type that either Employee or it's child for which the repository is used.
 */
abstract class EmployeeRepositoryImpl <T extends Employee> {

    protected T get(String email, Class<T> tClass) throws NotFoundException {

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            T result = em.find(tClass, email);
            em.getTransaction().commit();

            if (result == null) {
                throw new NotFoundException("No employee found with email: " + email);
            }

            return result;
        } catch (IllegalArgumentException exception) {
            throw new NotFoundException("No employee found with email: " + email, exception);
        }
    }

    protected List<T> getAll(Class<T> tClass) {

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            List<T> result = em.createQuery("SELECT e FROM " + tClass.getSimpleName() + " e", tClass).getResultList();
            em.getTransaction().commit();

            return result;
        }
    }

    protected void setForename(String email, String forename, Class<T> tClass) throws NotFoundException {
        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();

            if(em.createQuery("UPDATE " + tClass.getSimpleName() + " e SET e.forename = :forename WHERE e.email = :email")
                    .setParameter("forename", forename)
                    .setParameter("email", email)
                    .executeUpdate() < 1) {
                        throw new NotFoundException("No employee found with email: " + email);
                    }

            em.getTransaction().commit();
        }
    }

    protected void setSurname(String email, String surname, Class<T> tClass) throws NotFoundException {
        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();

            if(em.createQuery("UPDATE " + tClass.getSimpleName() + " e SET e.surname = :surname WHERE e.email = :email")
                    .setParameter("surname", surname)
                    .setParameter("email", email)
                    .executeUpdate() < 1) {
                        throw new NotFoundException("No employee found with email: " + email);
                    }

            em.getTransaction().commit();
        }
    }

    protected void addAddress(String email, Address address, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);
        result.getAddresses().add(address);

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void removeAddress(String email, Address address, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);

        if(result.getAddresses().remove(address)) {
            try (EntityManager em = EMUtils.getEM()) {
                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
        else {
            throw new NotFoundException("Address not found :" + address);
        }
    }

    protected void updateAddress(String email, Address oldAddress, Address newAddress, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);

        if(result.getAddresses().remove(oldAddress)) {
            result.getAddresses().add(newAddress);

            try (EntityManager em = EMUtils.getEM()) {
                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
        else {
            throw new NotFoundException("Address not found: " + oldAddress);
        }
    }

    protected void addPhone(String email, Phone phone, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);
        result.getPhones().add(phone);

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void removePhone(String email, Phone phone, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);

        if(result.getPhones().remove(phone)) {
            try (EntityManager em = EMUtils.getEM()) {
                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
        else {
            throw new NotFoundException("Phone not found :" + phone);
        }
    }

    protected void updatePhone(String email, Phone oldPhone, Phone newPhone, Class<T> tClass) throws NotFoundException {
        T result = get(email, tClass);

        if(result.getPhones().remove(oldPhone)) {
            result.getPhones().add(newPhone);

            try (EntityManager em = EMUtils.getEM()) {
                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
        else {
            throw new NotFoundException("Phone not found: " + oldPhone);
        }
    }
}
