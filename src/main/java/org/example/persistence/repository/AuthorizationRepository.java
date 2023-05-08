package org.example.persistence.repository;

import org.example.persistence.entity.Authorization;

/**
 * <h3>Interface AuthorizationRepository</h3>
 * This is a {@link Authorization} specific repository.
 */
public interface AuthorizationRepository {

    /**
     * Returns a boolean value for a permission that indicates whether the employee has that permission or not.
     * If the permission does not exist, returns null.
     * @param employeeType Employee type. Throws IllegalArgumentException it's null
     * @param permissionName Permission name. Throws IllegalArgumentException it's null
     * @return A boolean value indicating if the permission is granted or not.
     * If the permission does not exist, returns null.
     */
    Boolean getPermission(String employeeType, String permissionName);
}
