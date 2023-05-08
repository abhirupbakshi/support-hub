package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class AuthorizationKey</h3>
 * This clas is used as the primary key for the {@link Authorization} table.
 */
@Embeddable
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
public class AuthorizationKey {

    /**
     * <h4>Required</h4>
     * The employee type for which the permission is granted or rejected.
     */
    @Column(name = "employee_type")
    @NonNull
    private String employeeType;

    /**
     * <h4>Required</h4>
     * The name of the permission.
     */
    @Column(name = "permission_name")
    @NonNull
    private String permissionName;
}
