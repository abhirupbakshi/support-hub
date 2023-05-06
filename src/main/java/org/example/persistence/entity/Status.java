package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class Status</h3>
 * This class represents a status of a {@link Complaint}.
 * It's an independent entity and only intended to be used as a status indicator
 * of a complaint.
 */
@Entity
@Table(name = "Statuses")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Status {

    /**
     * <h4>Optional</h4>
     * Name of the status.
     * Also used as primary key in the database.
     * Cannot be set after the object is created.
     */
    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "status_name")
    @NonNull
    private String name;

    /**
     * <h4>Optional</h4>
     */
    @Column(name = "description")
    private String description;
}
