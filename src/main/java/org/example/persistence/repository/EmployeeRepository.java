package org.example.persistence.repository;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.Employee;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.NotFoundException;
import jakarta.persistence.FetchType;
import java.util.List;

/**
 * This interface is used to declare the methods that are common between Employee, and it's child repositories.
 * This interface has been extended by the following interfaces:
 * <ul>
 *     <li>{@link EngineerRepository}</li>
 *     <li>{@link UserRepository}</li>
 *     <li>{@link HODRepository}</li>
 * </ul>
 *
 * And those above interfaces are meant to represent specific methods for each type of Employee child repository.
 * @param <T> The type that either Employee or it's child for which the repository is used.
 */
public interface EmployeeRepository <T extends Employee> {

    /**
     * Returns the concrete T-type employee with the given email. All the {@link FetchType} "LAZY"
     * fields (in the Employee, or it's subclass) are set to null.
     * @param email The email the employee.
     * @return The concrete T-type employee with the given email.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    T get(String email) throws NotFoundException;

    /**
     * Returns all the concrete T-type employees. All the {@link FetchType} "LAZY"
     * fields (in the Employee, or it's subclass) are set to null.
     * @return All the concrete T-type employees as a {@link List}.
     */
    List<T> getAll();

    /**
     * Updates the forename of the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param forename The new forename of the employee.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    void setForename(String email, String forename) throws NotFoundException;

    /**
     * Updates the surname of the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param surname The new forename of the employee.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    void setSurname(String email, String surname) throws NotFoundException;

    /**
     * Adds an address to the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param address The new address to be added.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    void addAddress(String email, Address address) throws NotFoundException;

    /**
     * Removes an address from the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param address The address to be removed.
     * @throws NotFoundException If no concrete T-type employee is found with the given email, or
     * if the given address is not present.
     */
    void removeAddress(String email, Address address) throws NotFoundException;

    /**
     * Updates the address of the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param oldAddress The old address to be updated.
     * @param newAddress The new address to be updated with.
     * @throws NotFoundException If no concrete T-type employee is found with the given email, or
     * if the given old address is not present.
     */
    void updateAddress(String email, Address oldAddress, Address newAddress) throws NotFoundException;

    /**
     * Adds a phone to the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param phone The new phone to be added.
     * @throws NotFoundException If no concrete T-type employee is found with the given email.
     */
    void addPhone(String email, Phone phone) throws NotFoundException;

    /**
     * Removes a phone from the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param phone The phone to be removed.
     * @throws NotFoundException If no concrete T-type employee is found with the given email, or
     * if the given phone is not present.
     */
    void removePhone(String email, Phone phone) throws NotFoundException;

    /**
     * Updates the phone of the concrete T-type employee with the given email.
     * @param email The email of the employee.
     * @param oldPhone The old phone to be updated.
     * @param newPhone The new phone to be updated with.
     * @throws NotFoundException If no concrete T-type employee is found with the given email, or
     * if the given old phone is not present.
     */
    void updatePhone(String email, Phone oldPhone, Phone newPhone) throws NotFoundException;
}
