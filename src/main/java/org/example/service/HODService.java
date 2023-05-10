package org.example.service;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import java.util.List;

/**
 * <h3>Interface HODService</h3>
 * This is a {@link HOD} specific service. It extends {@link AccountService}
 */
public interface HODService extends AccountService<HOD> {

    /**
     * Get all engineers from the database
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @return A {@link List} of all {@link Engineer}s
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD is not found
     */
    List<Engineer> getAllEngineers(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Get all complaints from the database
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @return A {@link List} of all {@link Complaint}s
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD is not found
     */
    List<Complaint> getAllComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Returns a complaint with the given id.
     * All the {@link FetchType#LAZY} fields are set to null.
     * @param email The email of the HOD. Throws IllegalArgumentException it is null.
     * @param password The password of the HOD. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @return The {@link Complaint}
     * @throws NotFoundException If the HOD or complaint is not found
     * @throws AuthenticationException If the HOD is not authenticated.
     * @throws AuthorizationException If the HOD is not authorized.
     */
    Complaint getComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Get all complaints assigned to an employee
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param engineerEmail The email of the employee. Throws IllegalArgumentException if it is null
     * @return A {@link List} of all {@link Complaint}s assigned to the employee
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD or employee is not found
     */
    List<Complaint> getComplaintsAssignedToEngineer(String email, String password, String engineerEmail)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Assign an existing complaint to an existing employee
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param complaintId The id of the complaint.
     * @param engineerEmail The email of the employee. Throws IllegalArgumentException if it is null
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD, complaint or employee is not found.
     * @throws AlreadyExistException If the employee is already assigned to the complaint
     */
    void assignExistingComplainToExistingEngineer(
            String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, AuthorizationException, NotFoundException, AlreadyExistException;

    /**
     * Removes an existing complaint from an existing employee
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param complaintId The id of the complaint.
     * @param engineerEmail The email of the employee. Throws IllegalArgumentException if it is null
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD, complaint or employee is not found or the complaint is
     * not assigned to the employee
     */
    void removeExistingComplainFromExistingEngineer(
            String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, NotFoundException, AuthorizationException;

    /**
     * Creates an employee account
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param engineerEmail The email of the employee. Throws IllegalArgumentException if it is null
     * @param engineerForename The forename of the employee. Throws IllegalArgumentException if it is null
     * @param engineerSurname The surname of the employee. Throws IllegalArgumentException if it is null
     * @param addresses The list of address, of the employee. Throws IllegalArgumentException if it is null
     * @param phones The list of phones, of the employee. Throws IllegalArgumentException if it is null
     * @param engineerPassword The password for the employee. Throws IllegalArgumentException if it is null
     * @throws AuthenticationException If the HOD email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD is not found
     * @throws AlreadyExistException If the employee account already exists
     */
    void createEngineerAccount(
            String email, String password,
            String engineerEmail, String engineerForename, String engineerSurname,
            List<Address> addresses, List<Phone> phones, String engineerPassword)
            throws AuthenticationException, AuthorizationException, NotFoundException, AlreadyExistException;
}
