package org.example.service;

import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.entity.HOD;
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
     * Assign an existing complaint to an existing engineer
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param complaintId The id of the complaint.
     * @param engineerEmail The email of the engineer. Throws IllegalArgumentException if it is null
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD, complaint or engineer is not found.
     * @throws AlreadyExistException If the engineer is already assigned to the complaint
     */
    void assignExistingComplainToExistingEngineer(
            String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, AuthorizationException, NotFoundException, AlreadyExistException;

    /**
     * Removes an existing complaint from an existing engineer
     * @param email The email of the HOD. Throws IllegalArgumentException if it is null
     * @param password The password of the HOD. Throws IllegalArgumentException if it is null
     * @param complaintId The id of the complaint.
     * @param engineerEmail The email of the engineer. Throws IllegalArgumentException if it is null
     * @throws AuthenticationException If the email or password is incorrect
     * @throws AuthorizationException If the HOD is not authorized to do the task.
     * @throws NotFoundException If the HOD, complaint or engineer is not found or the complaint is
     * not assigned to the engineer
     */
    void removeExistingComplainFromExistingEngineer(
            String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, NotFoundException, AuthorizationException;
}
