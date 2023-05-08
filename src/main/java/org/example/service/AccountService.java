package org.example.service;

import org.example.persistence.entity.Account;
import org.example.persistence.entity.Address;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import jakarta.persistence.FetchType;
import java.util.List;

/**
 * <h3>Class AccountService</h3>
 * This interface is used to declare the methods that are common between:
 * <ul>
 *     <li>{@link HODService}</li>
 *     <li>{@link EngineerService}</li>
 *     <li>{@link UserService}</li>
 * </ul>
 * @param <T> The type, that is either an {@link Employee} or its child, for which the service is used.
 */
public interface AccountService <T extends Employee> extends EmployeeService<T> {

    /**
     * Gets the details of the account with the associated employee. The {@link FetchType#LAZY} fields in the children of employee
     * don't get set to null as it's the responsibility of the implementing class.
     * @param email The email of the account. Throws IllegalArgumentException if it's null.
     * @param password The password of the account. Throws IllegalArgumentException if it's null.
     * @return The details of the {@link Account}.
     * @throws NotFoundException If the account does not exist.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws AuthorizationException If the user is not authorized to do the task.
     */
     Account getAccountDetails(String email, String password)
             throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Creates a new account.
     * @param email The email of the account. Throws IllegalArgumentException if it's null.
     * @param forename The forename of the account. Throws IllegalArgumentException if it's null.
     * @param surname The surname of the account. Throws IllegalArgumentException if it's null.
     * @param addresses The addresses of the account. Throws IllegalArgumentException if it's null or empty.
     * @param phones The phones of the account. Throws IllegalArgumentException if it's null or empty.
     * @param password The password of the account. Throws IllegalArgumentException if it's null.
     * @throws AuthorizationException If the user is not authorized to do the task.
     * @throws AlreadyExistException If the account already exists.
     */
    void createAccount(
            String email, String forename, String surname,
            List<Address> addresses, List<Phone> phones, String password)
            throws AuthorizationException, AlreadyExistException;

    /**
     * Changes the password of the account. User has to be authorized and authenticated to change the password.
     * @param email The email of the account. Throws IllegalArgumentException if it's null.
     * @param currentPassword The current password of the account. Throws IllegalArgumentException if it's null.
     * @param newPassword The new password of the account. Throws IllegalArgumentException if it's null.
     * @throws AuthorizationException If the user is not authorized to do the task.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws NotFoundException If the account does not exist.
     */
    void updatePassword(String email, String currentPassword, String newPassword)
            throws AuthorizationException, AuthenticationException, NotFoundException;

    /**
     * Deletes the account.User has to be authorized and authenticated to delete the account.
     * @param email The email of the account. Throws IllegalArgumentException if it's null.
     * @param password The password of the account. Throws IllegalArgumentException if it's null.
     * @throws AuthorizationException If the user is not authorized to do the task.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws NotFoundException If the account does not exist.
     */
    void deleteAccount(String email, String password)
            throws AuthorizationException, AuthenticationException, NotFoundException;
}
