package org.example.persistence.repository;

import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * <h3>Interface EngineerRepository</h3>
 * This is a {@link Engineer} specific repository. It extends {@link EmployeeRepository}
 */
public interface EngineerRepository extends EmployeeRepository <Engineer> {

    /**
     * Get all assigned complaints for an employee
     * @param email The email of the employee. Throws IllegalArgumentException if the email is null.
     * @return A {@link List} of assigned complains
     * @throws NotFoundException If the employee does not exist
     */
    List<Complaint> getAssignedComplains(String email) throws NotFoundException;

    /**
     * Assign an existing complaint to an existing employee.
     * @param email The email of the employee. Throws IllegalArgumentException if the email is null.
     * @param complainId The id of the complaint.
     * @throws NotFoundException If the employee or the complaint does not exist
     * @throws AlreadyExistException If the employee already has the complaint assigned to them
     */
    void assignExistingComplain(String email, int complainId) throws NotFoundException, AlreadyExistException;

    /**
     * Remove an assigned complaint from an employee
     * @param email The email of the employee. Throws IllegalArgumentException if the email is null.
     * @param complainId The id of the complaint.
     * @throws NotFoundException If the employee or the complaint does not exist, or the employee does not have
     * the complaint previously assigned to them
     */
    void removeAssignedComplain(String email, int complainId) throws NotFoundException;
}
