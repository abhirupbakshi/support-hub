package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;

@Embeddable
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class Update {

    @Column(name = "message", nullable = false)
    @NonNull
    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on", nullable = false)
    @Setter(value = AccessLevel.NONE)
    @Builder.Default
    LocalDateTime createdOn = LocalDateTime.now();
}
