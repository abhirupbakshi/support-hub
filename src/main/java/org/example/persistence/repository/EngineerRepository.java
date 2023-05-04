package org.example.persistence.repository;

import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * This is a {@link Engineer} specific repository. It extends {@link EmployeeRepository}
 */
public interface EngineerRepository extends EmployeeRepository <Engineer> {

    /**
     * Get all assigned complains of an engineer
     * @param email The email of the engineer
     * @return A {@link List} of assigned complains
     * @throws NotFoundException If the engineer does not exist
     */
    List<Complaint> getAssignedComplains(String email) throws NotFoundException;

    /**
     * Assign a complaint to an engineer that already exists
     * @param email The email of the engineer
     * @param complainId The id of the complaint
     * @throws NotFoundException If the engineer or the complaint does not exist
     * @throws AlreadyExistException If the engineer already has the complaint assigned
     */
    void assignExistingComplain(String email, int complainId) throws NotFoundException, AlreadyExistException;

    /**
     * Remove an assigned complaint from an engineer
     * @param email The email of the engineer
     * @param complainId The id of the complaint
     * @throws NotFoundException If the engineer or the complaint does not exist, or the engineer does not have
     * the complaint previously assigned to them
     */
    void removeAssignedComplain(String email, int complainId) throws NotFoundException;
}
