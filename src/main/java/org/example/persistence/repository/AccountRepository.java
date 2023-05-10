package org.example.persistence.repository;

import org.example.persistence.entity.Account;
import org.example.persistence.entity.Employee;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.InvalidCredentialException;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * <h3>Interface AccountRepository</h3>
 * This is a {@link Account} specific repository.
 */
public interface AccountRepository {

    /**
     * Returns the account whose employee has the given email.
     * @param email The email the employee. Throws IllegalArgumentException if it's null
     * @return The {@link Account} whose employee has the given email.
     * @throws NotFoundException If no account is found with the given email.
     */
    Account get(String email) throws NotFoundException;

    /**
     * Returns all the accounts
     * @return All the {@link Account}s as a {@link List}.
     */
    List<Account> getAll();

    /**
     * Creates an account for the given employee
     * @param employee The employee. Throws IllegalArgumentException if it's null
     * @param password The password. Throws IllegalArgumentException if it's null
     * @throws AlreadyExistException If the account already exists with the given email
     */
    void createAccount(Employee employee, byte[] password) throws AlreadyExistException;

    /**
     * Sets the password for an account whose employee's email is provided.
     * The current password needs to be passed to change the password.
     * @param email The email of the employee. Throws IllegalArgumentException if it's null
     * @param currentPassword The current password. Throws IllegalArgumentException if it's null
     * @param newPassword The new password. Throws IllegalArgumentException if it's null
     * @throws NotFoundException If no account is found with the given email
     * @throws InvalidCredentialException If the given current password is incorrect.
     * In this case, the password doesn't get changed
     */
    void setPassword(String email, byte[] currentPassword, byte[] newPassword)
            throws NotFoundException, InvalidCredentialException;

    /**
     * Deletes the account with the given email.
     * <br>
     * Note: For employee deletion, improvements need to be made to make this more efficient.
     * @param email The email of the employee. Throws IllegalArgumentException if it's null
     * @throws NotFoundException If no account is found with the given email
     */
    void deleteAccount(String email) throws NotFoundException;
}
