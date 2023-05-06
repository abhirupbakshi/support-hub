package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * <h3>Class Address</h3>
 * This class represents address of the employee. This is an embeddable class as it's intended
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
public class Address {

    /**
     * <h4>Required</h4>
     * Address type (e.g home or office).
     */
    @Column(name = "address_type", nullable = false)
    @NonNull
    private String addressType;

    /**
     * <h4>Optional</h4>
     */
    @Column(name = "city")
    private String city;

    /**
     * <h4>Optional</h4>
     */
    @Column(name = "state")
    private String state;

    /**
     * <h4>Optional</h4>
     */
    @Column(name = "country")
    private String country;
}
