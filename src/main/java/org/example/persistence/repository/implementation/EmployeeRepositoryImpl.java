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
 * <h3>Class EmployeeRepositoryImpl</h3>
 * This class is used to implement the methods of {@link EmployeeRepository} but with a generic context such that
 * it has all the implementation for each method present in the EmployeeRepository, but with an extra parameter to
 * each method, that is {@link Class<T>} whose concrete implementation is intended to be provided by the class that
 * extends this class.
 * For the Class<T> parameters, it throws IllegalArgumentException if it is null.
 * <br>
 * Hence, it doesn't implement EmployeeRepository itself, and is only meant to be extended by the {@link Employee} or
 * it's children classes.
 * @param <T> The type, that is either Employee or its child; implemented by the class that extends this class.
 */
abstract class EmployeeRepositoryImpl <T extends Employee> {

    protected T get(String email, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            T result = em.find(tClass, email);
            em.getTransaction().commit();

            if (result == null)
                throw new NotFoundException("No employee found with email: " + email);

            return result;
        }
    }

    protected List<T> getAll(Class<T> tClass) {

        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            List<T> result = em.createQuery("SELECT e FROM " + tClass.getSimpleName() + " e", tClass)
                    .getResultList();

            em.getTransaction().commit();

            return result;
        }
    }

    protected void setForename(String email, String forename, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(forename == null)
            throw new IllegalArgumentException("forename parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            if(em.createQuery("UPDATE " + tClass.getSimpleName() + " e SET e.forename = :forename WHERE e.employeeEmail = :email")
                    .setParameter("forename", forename)
                    .setParameter("email", email)
                    .executeUpdate() < 1) {
                        throw new NotFoundException("No employee found with email: " + email);
                    }

            em.getTransaction().commit();
        }
    }

    protected void setSurname(String email, String surname, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(surname == null)
            throw new IllegalArgumentException("surname parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            if(em.createQuery("UPDATE " + tClass.getSimpleName() + " e SET e.surname = :surname WHERE e.employeeEmail = :email")
                    .setParameter("surname", surname)
                    .setParameter("email", email)
                    .executeUpdate() < 1) {
                        throw new NotFoundException("No employee found with email: " + email);
                    }

            em.getTransaction().commit();
        }
    }

    protected void addAddress(String email, Address address, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(address == null)
            throw new IllegalArgumentException("address parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        T result = get(email, tClass);

        result.setAddresses(address);

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void removeAddress(String email, Address address, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        if(address == null)
            return;

        T result = get(email, tClass);

        if(!result.removeAddress(address))
            return;

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void updateAddress(String email, Address oldAddress, Address newAddress, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(newAddress == null)
            throw new IllegalArgumentException("newAddress parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        if(oldAddress == null)
            return;

        T result = get(email, tClass);

        if(result.getAddresses().contains(oldAddress)) {

            result.setAddresses(newAddress);
            result.removeAddress(oldAddress);

            try (EntityManager em = EMUtils.getEM()) {

                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
    }

    protected void addPhone(String email, Phone phone, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(phone == null)
            throw new IllegalArgumentException("phone parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        T result = get(email, tClass);

        result.setPhones(phone);

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void removePhone(String email, Phone phone, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        if(phone == null)
            return;

        T result = get(email, tClass);

        if(!result.removePhone(phone))
            return;

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.merge(result);
            em.getTransaction().commit();
        }
    }

    protected void updatePhone(String email, Phone oldPhone, Phone newPhone, Class<T> tClass) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(newPhone == null)
            throw new IllegalArgumentException("newPhone parameter cannot be null");
        if(tClass == null)
            throw new IllegalArgumentException("tClass parameter cannot be null");

        if(oldPhone == null)
            return;

        T result = get(email, tClass);

        if(result.getPhones().contains(oldPhone)) {

            result.setPhones(newPhone);
            result.removePhone(oldPhone);

            try (EntityManager em = EMUtils.getEM()) {

                em.getTransaction().begin();
                em.merge(result);
                em.getTransaction().commit();
            }
        }
    }
}
