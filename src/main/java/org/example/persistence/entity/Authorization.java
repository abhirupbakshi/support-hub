package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class Authorization</h3>
 * This class represents a authorization of a {@link Employee}. It has a {@link AuthorizationKey} as
 * it's primary key for the database and a boolean value indicating whether the permission is granted or not.
 */
@Entity
@Table(name = "Authorizations")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
public class Authorization {

    /**
     * <h4>Required</h4>
     * Compound primary key for the database that also represents a particular permission for a
     * particular employee.
     */
    @EmbeddedId
    private AuthorizationKey key;

    /**
     * <h4>Required</h4>
     * The boolean value indicating whether the permission is granted or not.
     */
    @Column(name = "value")
    @NonNull
    private boolean value;
}
