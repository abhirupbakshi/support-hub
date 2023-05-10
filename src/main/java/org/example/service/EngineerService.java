package org.example.service;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.exception.NotFoundException;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import java.util.List;

/**
 * <h3>Interface EngineerService</h3>
 * This is a {@link Engineer} specific service. It extends {@link AccountService}
 */
public interface EngineerService extends AccountService<Engineer> {

    /**
     * Returns a complaint with the given id that has been assigned to the given employee.
     * All the {@link FetchType#LAZY} fields are set to null.
     * @param email The email of the employee. Throws IllegalArgumentException it is null.
     * @param password The password of the employee. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @return The {@link Complaint}
     * @throws NotFoundException If the employee is not found or the complaint with the given
     * id is not associated with the employee.
     * @throws AuthenticationException If the employee is not authenticated.
     * @throws AuthorizationException If the employee is not authorized.
     */
    Complaint getAssignedComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Returns a list of complaints that has been assigned to the given employee.
     * All the {@link FetchType#LAZY} fields are set to null.
     * @param email The email of the employee. Throws IllegalArgumentException it is null.
     * @param password The password of the employee. Throws IllegalArgumentException it is null.
     * @return The a {@link List} of {@link Complaint}
     * @throws NotFoundException If the employee is not found.
     * @throws AuthenticationException If the employee is not authenticated.
     * @throws AuthorizationException If the employee is not authorized.
     */
    List<Complaint> getAllAssignedComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Changes the status of a complaint.
     * @param email The email of the employee. Throws IllegalArgumentException it is null.
     * @param password The password of the employee. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @param status The new status of the complaint.
     * @throws AuthenticationException If the employee is not authenticated.
     * @throws AuthorizationException If the employee is not authorized.
     * @throws NotFoundException If the employee, complaint or status is not found. Or, if
     * the complaint is not associated with the employee
     */
    void changeComplaintStatus(String email, String password, int complaintId, String status)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Adds an update to a complaint.
     * @param email The email of the employee. Throws IllegalArgumentException it is null.
     * @param password The password of the employee. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @param update The new update of the complaint.
     * @throws AuthenticationException If the employee is not authenticated.
     * @throws AuthorizationException If the employee is not authorized.
     * @throws NotFoundException If the employee or complaint is not found. Or, if
     * the complaint is not associated with the employee
     */
    void addUpdateToComplaint(String email, String password, int complaintId, String update)
            throws AuthenticationException, AuthorizationException, NotFoundException;
}
