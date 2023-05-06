package org.example.persistence.repository;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.entity.Update;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.implementation.EngineerRepositoryImpl;
import java.util.List;

/**
 * <h3>Interface ComplaintRepository</h3>
 * This interface is used to declare the methods that are used for {@link Complaint}.
 */
public interface ComplaintRepository {

    /**
     * Get a complaint by id. All the {@link FetchType} "LAZY"
     * fields are set to null.
     * @param id The id of the complaint
     * @return The {@link Complaint}
     * @throws NotFoundException If no complaint found with the given id
     */
    Complaint get(int id) throws NotFoundException;

    /**
     * Get all complaints. All the {@link FetchType} "LAZY"
     * fields are set to null.
     * @return The {@link List} of {@link Complaint}
     */
    List<Complaint> getAll();

    /**
     * Returns the assigned engineers of a complaint
     * @param complaintId The id of the complaint
     * @return The {@link List} of {@link Engineer} assigned to the complaint
     * @throws NotFoundException If no complaint found with the given id
     */
    List<Engineer> getAssignedEngineers(int complaintId) throws NotFoundException;

    /**
     * Create a new complaint.
     * @param description The description of the complaint. Throws IllegalArgumentException if it's null
     * @param createdByUserEmail The user email who created the complaint. If it's null, an IllegalArgumentException will be thrown
     * @param categoryName The name of the category. Throws IllegalArgumentException if it's null
     * @param statusName The name of the status. Throws IllegalArgumentException if it's null
     * @throws NotFoundException If the provided user or the category or the status was not found
     */
    void createComplain(String description, String createdByUserEmail,
                        String categoryName, String statusName) throws NotFoundException;

    /**
     * Set the description of a complaint
     * @param id The id of the complaint
     * @param description The new description of the complaint, Throws IllegalArgumentException if it's null
     * @throws NotFoundException If no complaint found with the given id
     */
    void setDescription(int id, String description) throws NotFoundException;

    /**
     * Set the category of a complaint
     * @param id The id of the complaint
     * @param categoryName The new category name of the complaint. Throws IllegalArgumentException if it's null
     * @throws NotFoundException If no complaint or category found with the given id or name respectively
     */
    void setCategory(int id, String categoryName) throws NotFoundException;

    /**
     * Set the status of a complaint
     * @param id The id of the complaint
     * @param statusName The new status name of the complaint. Throws IllegalArgumentException if it's null
     * @throws NotFoundException If no complaint or status found with the given id or name respectively
     */
    void setStatus(int id, String statusName) throws NotFoundException;

    /**
     * Assign an engineer to a complaint. Same as {@link EngineerRepositoryImpl#assignExistingComplain(String, int)} as
     * {@link Engineer} and {@link Complaint} are in a bidirectional relation.
     * @param email The email of the engineer. Throws IllegalArgumentException if it's null
     * @param complainId The id of the complaint
     * @throws NotFoundException If the engineer or the complaint does not exist
     * @throws AlreadyExistException If the engineer already has the complaint assigned to them
     */
    void assignExistingEngineer(String email, int complainId) throws NotFoundException, AlreadyExistException;

    /**
     * Remove an engineer from a complaint. Same as {@link EngineerRepositoryImpl#removeAssignedComplain(String, int)} as
     * {@link Engineer} and {@link Complaint} are in a bidirectional relation.
     * @param email The email of the engineer. Throws IllegalArgumentException if it's null
     * @param complainId The id of the complaint.
     * @throws NotFoundException If the engineer or the complaint does not exist, or the engineer does not have
     * the complaint previously assigned to them
     */
    void removeAssignedEngineer(String email, int complainId) throws NotFoundException;

    /**
     * Add a new {@link Update} to a complaint.
     * @param complaintId The id of the complaint
     * @param message The new message of the update. If it's null, an IllegalArgumentException will be thrown
     * @throws NotFoundException If no complaint found with the given id
     */
    void addUpdate(int complaintId, String message) throws NotFoundException;
}
