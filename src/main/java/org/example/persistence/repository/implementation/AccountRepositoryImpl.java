package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.persistence.entity.Account;
import org.example.persistence.entity.Employee;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.InvalidCredentialException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.AccountRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

/**
 * <h3>Class AccountRepositoryImpl</h3>
 * This is a implementation class for {@link AccountRepository}.
 */
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account get(String email) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try(EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            Account account = em.createQuery("""
                            SELECT a
                            FROM Account a
                            WHERE a.employee.employeeEmail = :email
                            """, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();

            em.getTransaction().commit();

            return account;
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Account not found for email: " + email, exception);
        }
    }

    @Override
    public List<Account> getAll() {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            List<Account> result = em.createQuery("SELECT a FROM Account a", Account.class)
                    .getResultList();
            em.getTransaction().commit();

            return result;
        }
    }

    @Override
    public void createAccount(Employee employee, byte[] password) throws AlreadyExistException {

        if(employee == null)
            throw new IllegalArgumentException("employee parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");

        try {
            get(employee.getEmployeeEmail());
            throw new AlreadyExistException("Account already exists for employee: " + employee.getEmployeeEmail());
        }
        catch (NotFoundException ignored) { }

        Account account = Account.builder()
                .employee(employee)
                .password(password)
                .build();

        employee.setAccount(account);

        try(EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.persist(account);
            em.getTransaction().commit();
        }
    }

    @Override
    public void setPassword(String email, byte[] currentPassword, byte[] newPassword)
            throws NotFoundException, InvalidCredentialException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(currentPassword == null)
            throw new IllegalArgumentException("currentPassword parameter cannot be null");
        if(newPassword == null)
            throw new IllegalArgumentException("newPassword parameter cannot be null");

        Account account = get(email);
        account.setPassword(currentPassword, newPassword);

        try(EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();
        }
    }

    @Override
    public void deleteAccount(String email) throws NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");

        try(EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            try {
                new EngineerRepositoryImpl().getAssignedComplains(email).forEach(complaint -> {
                    try {
                        new ComplaintRepositoryImpl()
                                .removeAssignedEngineer(email, complaint.getId());
                    } catch (NotFoundException ignored) { }
                });
            } catch (NotFoundException ignored) { }

            Account account = em.createQuery("""
                    SELECT a
                    FROM Account a
                    WHERE a.employee.employeeEmail = :email
                    """, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();

            em.remove(account);

            em.getTransaction().commit();
        }
        catch (NoResultException exception) {
            throw new NotFoundException("Account not found for email: " + email, exception);
        }
    }
}
