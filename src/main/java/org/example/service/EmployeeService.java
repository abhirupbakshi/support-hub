package org.example.service;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.NotFoundException;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;

/**
 * <h3>Interface EmployeeService</h3>
 * This interface is used to declare the methods that are common between:
 * <ul>
 *     <li>{@link HODService}</li>
 *     <li>{@link EngineerService}</li>
 *     <li>{@link UserService}</li>
 *     <li>{@link AccountService}</li>
 * </ul>
 * @param <T> The type, that is either an {@link Employee} or its child, for which the service is used.
 */
public interface EmployeeService <T extends Employee> {

    /**
     * Changes the forename of an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param newForename The new forename of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void changeForename(String email, byte[] password, String newForename)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Changes the surname of an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param newSurname The new surname of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void changeSurname(String email, byte[] password, String newSurname)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Adds the address to an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param address The new address of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void addAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Updates the address of an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param oldAddress The old address of the employee. Throws IllegalArgumentException if it is null.
     * @param newAddress The new address of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void updateAddress(String email, byte[] password, Address oldAddress, Address newAddress)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Removes an address from an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param address The address of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void removeAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Adds the phone to an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param phone The new phone of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void addPhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Updates the phone of an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param oldPhone The old phone of the employee. Throws IllegalArgumentException if it is null.
     * @param newPhone The new phone of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void updatePhone(String email, byte[] password, Phone oldPhone, Phone newPhone)
            throws NotFoundException, AuthenticationException, AuthorizationException;

    /**
     * Removes a phone from an employee if authorized and authenticated.
     * @param email The email of the employee. Throws IllegalArgumentException if it is null.
     * @param password The password of the employee. Throws IllegalArgumentException if it is null.
     * @param phone The phone of the employee. Throws IllegalArgumentException if it is null.
     * @throws NotFoundException If the employee is not found
     * @throws AuthenticationException If the employee is not authenticated
     * @throws AuthorizationException If the employee is not authorized to do the task
     */
    void removePhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException;
}
