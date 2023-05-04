package org.example.persistence.repository;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.entity.User;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.implementation.EngineerRepositoryImpl;
import java.util.List;

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
     * @return The list of {@link Complaint}
     */
    List<Complaint> getAll();

    List<Engineer> getAssignedEngineers(int id) throws NotFoundException;

    /**
     * Create a new complaint.
     * @param description The description of the complaint
     * @param createdBy The user who created the complaint. If it's null, an IllegalArgumentException will be thrown
     * @param categoryName The name of the category
     * @param statusName The name of the status
     * @throws NotFoundException If no category or status was found with the given name
     */
    void createComplain(String description, User createdBy, String categoryName, String statusName) throws NotFoundException;

    /**
     * Set the description of a complaint
     * @param id The id of the complaint
     * @param description The new description of the complaint
     * @throws NotFoundException If no complaint found with the given id
     */
    void setDescription(int id, String description) throws NotFoundException;

    /**
     * Set the category of a complaint
     * @param id The id of the complaint
     * @param categoryName The new category name of the complaint
     * @throws NotFoundException If no complaint or category found with the given id or name respectively
     */
    void setCategory(int id, String categoryName) throws NotFoundException;

    /**
     * Set the status of a complaint
     * @param id The id of the complaint
     * @param statusName The new status name of the complaint
     * @throws NotFoundException If no complaint or status found with the given id or name respectively
     */
    void setStatus(int id, String statusName) throws NotFoundException;

    /**
     * Assign an engineer to a complaint. Same as {@link EngineerRepositoryImpl#assignExistingComplain(String, int)}
     * as these two methods are bidirectional.
     * @param email The email of the engineer
     * @param complainId The id of the complaint
     * @throws NotFoundException If the engineer or the complaint does not exist
     * @throws AlreadyExistException If the engineer already has the complaint assigned
     */
    void assignExistingEngineer(String email, int complainId) throws NotFoundException, AlreadyExistException;

    /**
     * Remove an engineer from a complaint. Same as {@link EngineerRepositoryImpl#removeAssignedComplain(String, int)}
     * as these two methods are bidirectional.
     * @param email The email of the engineer
     * @param complainId The id of the complaint
     * @throws NotFoundException If the engineer or the complaint does not exist, or the engineer does not have
     * the complaint previously assigned to them
     */
    void removeAssignedEngineer(String email, int complainId) throws NotFoundException;

    /**
     * Add a new update to a complaint.
     * Note: For deletion, this method fetches the complaint multiple times. Improvements need to be made to make this more efficient.
     * @param complainId The id of the complaint
     * @param message The new message of the update. If it's null, an IllegalArgumentException will be thrown
     * @throws NotFoundException If no complaint found with the given id
     */
    void addUpdate(int complainId, String message) throws NotFoundException;
}
