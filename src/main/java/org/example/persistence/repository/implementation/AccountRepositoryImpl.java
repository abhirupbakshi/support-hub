package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.example.persistence.entity.Account;
import org.example.persistence.entity.Employee;
import org.example.persistence.exception.InvalidCredentialException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.AccountRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

/**
 * This is the implementation class for {@link AccountRepository}.
 */
public class AccountRepositoryImpl implements AccountRepository {

    @Override
    public Account get(String email) throws NotFoundException {
        try(EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();

            Account account = em.createQuery("""
                            SELECT a
                            FROM Account a
                            WHERE a.employee.email = :email
                            """, Account.class)
                    .setParameter("email", email)
                    .getSingleResult();

            em.getTransaction().commit();

            return account;
        }
        catch (NoResultException e) {
            throw new NotFoundException("Account not found for email: " + email);
        }
    }

    @Override
    public List<Account> getAll() {

        try (EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            List<Account> result = em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
            em.getTransaction().commit();

            return result;
        }
    }

    @Override
    public void createAccount(Employee employee, byte[] password) {

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
    public void setPassword(String email, byte[] oldPassword, byte[] newPassword)
            throws NotFoundException, InvalidCredentialException {

        Account account = get(email);
        account.setPassword(oldPassword, newPassword);

        try(EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            em.merge(account);
            em.getTransaction().commit();
        }
        catch (NoResultException e) {
            throw new NotFoundException("Account not found for email: " + email);
        }
    }

    @Override
    public void deleteAccount(String email) throws NotFoundException {

        Account account = get(email);

        try(EntityManager em = EMUtils.getEM()) {
            em.getTransaction().begin();
            em.remove(em.find(Account.class, account.getId()));
            em.getTransaction().commit();
        }
        catch (NoResultException e) {
            throw new NotFoundException("Account not found for email: " + email);
        }
    }
}
