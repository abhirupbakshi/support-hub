package org.example.persistence.repository;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.NotFoundException;
import jakarta.persistence.FetchType;
import java.util.List;

/**
 * <h3>Interface EmployeeRepository</h3>
 * This interface is used to declare the methods that are common between {@link Employee}, and it's child repositories.
 * This interface has been extended by the following interfaces:
 * <ul>
 *     <li>{@link EngineerRepository}</li>
 *     <li>{@link UserRepository}</li>
 *     <li>{@link HODRepository}</li>
 * </ul>
 * And those are meant to represent specific methods for each type of Employee (child) repository.
 * @param <T> The type, that is either an Employee or its child, for which the repository is used.
 */
public interface EmployeeRepository <T extends Employee> {

    /**
     * Returns the T-type employee with the given email.
     * All the {@link FetchType} "LAZY" fields in the returned object are set to null.
     * @param email The email the employee. Throws IllegalArgumentException if email is null.
     * @return The T-type employee with the given email.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    T get(String email) throws NotFoundException;

    /**
     * Returns all the T-type employees.
     * All the {@link FetchType} "LAZY" fields in the returned objects are set to null.
     * @return All the T-type employees as a {@link List}.
     */
    List<T> getAll();

    /**
     * Updates the forename of a T-type employee with the given email.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param forename The new forename of the employee. Throws IllegalArgumentException if forename is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void setForename(String email, String forename) throws NotFoundException;

    /**
     * Updates the surname of a T-type employee with the given email.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param surname The new surname of the employee. Throws IllegalArgumentException if surname is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void setSurname(String email, String surname) throws NotFoundException;

    /**
     * Adds an address to the T-type employee with the given email.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param address The new address to be added. Throws IllegalArgumentException if address is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void addAddress(String email, Address address) throws NotFoundException;

    /**
     * Removes an address from a T-type employee with the given email.
     * If the provided address is not found or null, nothing happens.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param address The address to be removed.
     * @throws NotFoundException If no T-type employee is found with the given email
     */
    void removeAddress(String email, Address address) throws NotFoundException;

    /**
     * Updates the address of a T-type employee with the given email.
     * If the provided old address is not found or null, nothing happens.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param oldAddress The old address to be updated.
     * @param newAddress The new address to be updated with. Throws IllegalArgumentException if newAddress is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void updateAddress(String email, Address oldAddress, Address newAddress) throws NotFoundException;

    /**
     * Adds a phone to the T-type employee with the given email.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param phone The new phone to be added. Throws IllegalArgumentException if phone is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void addPhone(String email, Phone phone) throws NotFoundException;

    /**
     * Removes a phone from a T-type employee with the given email.
     * If the provided phone is not found or null, nothing happens.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param phone The phone to be removed.
     * @throws NotFoundException If no T-type employee is found with the given email
     */
    void removePhone(String email, Phone phone) throws NotFoundException;

    /**
     * Updates the phone of a T-type employee with the given email.
     * If the provided old phone is not found or null, nothing happens.
     * @param email The email of the employee. Throws IllegalArgumentException if email is null.
     * @param oldPhone The old phone to be updated.
     * @param newPhone The new phone to be updated with. Throws IllegalArgumentException if newPhone is null.
     * @throws NotFoundException If no T-type employee is found with the given email.
     */
    void updatePhone(String email, Phone oldPhone, Phone newPhone) throws NotFoundException;
}
