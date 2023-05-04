package org.example.persistence.repository;

import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.User;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * This is a {@link User} specific repository. It extends {@link EmployeeRepository}
 */
public interface UserRepository extends EmployeeRepository<User> {

    /**
     * Get all created complains of a user
     * @param email The email of the user
     * @return A {@link List} of created complains
     * @throws NotFoundException If the user does not exist
     */
    List<Complaint> getCreatedComplains(String email) throws NotFoundException;
}
