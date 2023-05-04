package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Embeddable
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class Phone {

    @Column(name = "number", nullable = false)
    @NonNull
    private String number;

    @Column(name = "phone_number_type", nullable = false)
    @NonNull
    private String phoneNumberType;
}
