package org.example.persistence.repository;

import org.example.persistence.entity.Account;
import org.example.persistence.entity.Employee;
import org.example.persistence.exception.InvalidCredentialException;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * This is a {@link Account} specific repository.
 */
public interface AccountRepository {

    /**
     * Returns the account whose employee has the given email.
     * @param email The email the employee.
     * @return The account whose employee has the given email.
     * @throws NotFoundException If no account is found with the given email.
     */
    Account get(String email) throws NotFoundException;

    /**
     * Returns all the accounts
     * @return All the accounts as a {@link List}.
     */
    List<Account> getAll();

    /**
     * Creates an account for the given employee
     * @param employee The employee
     * @param password The password
     */
    void createAccount(Employee employee, byte[] password);

    /**
     * Sets the password for the given email. The Old password needs to be passed to change the password.
     * @param email The email of the employee
     * @param oldPassword The old password
     * @param newPassword The new password
     * @throws NotFoundException If no account is not found with the given email
     * @throws InvalidCredentialException If the given old password is incorrect. In this case, the password doesn't get changed
     */
    void setPassword(String email, byte[] oldPassword, byte[] newPassword)
            throws NotFoundException, InvalidCredentialException;

    /**
     * Deletes the account with the given email.
     * <br>
     * Note: For deletion, this method fetches the account multiple times. Improvements need to be made to make this more efficient.
     * @param email The email of the employee
     * @throws NotFoundException If no account is found with the given email
     */
    void deleteAccount(String email) throws NotFoundException;
}
