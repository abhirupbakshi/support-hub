package org.example.persistence.repository;

import org.example.persistence.entity.Authorization;
import org.example.persistence.exception.NotFoundException;
import java.util.List;

/**
 * <h3>Interface AuthorizationRepository</h3>
 * This is a {@link Authorization} specific repository.
 */
public interface AuthorizationRepository {

    /**
     * Get authorization level by employee type
     * @param employeeType Employee type. Throws IllegalArgumentException it's null
     * @return Authorization level
     * @throws NotFoundException If the employee type is not found
     */
    int get(String employeeType) throws NotFoundException;

    /**
     * Get employee types by authorization level
     * @param level Authorization level
     * @return Employee types as {@link List}
     */
    List<String> get(int level);
}
