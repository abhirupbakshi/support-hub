package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class Phone</h3>
 * This class represents phone numbers of the employee. This is an embeddable class as it's intended
 * to be a part of the {@link Employee} class.
 */
@Embeddable
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class Phone {

    /**
     * <h4>Required</h4>
     */
    @Column(name = "number", nullable = false)
    @NonNull
    private String number;

    /**
     * <h4>Required</h4>
     * Phone number type (e.g home or office).
     */
    @Column(name = "phone_number_type", nullable = false)
    @NonNull
    private String phoneNumberType;
}
