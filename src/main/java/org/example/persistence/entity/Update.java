package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

/**
 * <h3>Class Update</h3>
 * This class represents an update added to a {@link Complaint}.
 * This is an embeddable class as it's intended
 * to be a part of the Complaint class.
 */
@Embeddable
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class Update {

    /**
     * <h4>Required</h4>
     * Message of the update.
     */
    @Column(name = "message", nullable = false)
    @NonNull
    private String message;

    /**
     * <h4>Optional</h4>
     * Creation date of the update.
     * Auto generated, cannot be set after the object is created.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    @Setter(value = AccessLevel.NONE)
    @Builder.Default
    LocalDateTime createdOn = LocalDateTime.now();
}
