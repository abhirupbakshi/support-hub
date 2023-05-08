package org.example.service;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.User;
import org.example.persistence.exception.NotFoundException;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import java.util.List;

/**
 * <h3>Interface UserService</h3>
 * This is a {@link User} specific service. It extends {@link AccountService}
 */
public interface UserService extends AccountService<User> {

    /**
     * Returns a list of complaints that has been created by the given user.
     * All the {@link FetchType#LAZY} fields are set to null.
     * @param email The email of the user. Throws IllegalArgumentException it is null.
     * @param password The password of the user. Throws IllegalArgumentException it is null.
     * @return The a {@link List} of {@link Complaint}
     * @throws NotFoundException If the user is not found.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws AuthorizationException If the user is not authorized.
     */
    List<Complaint> getAllCreatedComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Returns a complaint with the given id that has been created by the given user.
     * All the {@link FetchType#LAZY} fields are set to null.
     * @param email The email of the user. Throws IllegalArgumentException it is null.
     * @param password The password of the user. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @return The {@link Complaint}
     * @throws NotFoundException If the user is not found or the complaint with the given
     * id is created by the user.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws AuthorizationException If the user is not authorized.
     * @throws NotFoundException If the user or complaint is not found or the complaint with the given
     * id is not created by the user.
     */
    Complaint getCreatedComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Creates a complaint. The status of the complaint will be set to "Open" automatically
     * @param email The email of the user. Throws IllegalArgumentException it is null.
     * @param password The password of the user. Throws IllegalArgumentException it is null.
     * @param description The description of the complaint.
     * @param category The category of the complaint.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws AuthorizationException If the user is not authorized.
     * @throws NotFoundException If the user or category is not found.
     */
    void registerComplaint(String email, String password, String description, String category)
            throws AuthenticationException, AuthorizationException, NotFoundException;

    /**
     * Changes the status of the complaint to "Resolved".
     * @param email The email of the user. Throws IllegalArgumentException it is null.
     * @param password The password of the user. Throws IllegalArgumentException it is null.
     * @param complaintId The id of the complaint.
     * @throws AuthenticationException If the user is not authenticated.
     * @throws AuthorizationException If the user is not authorized.
     * @throws NotFoundException If the user or complaint is not found or the complaint with the given
     * id is not created by the user.
     */
    void changeComplaintStatusToResolved(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException;
}
