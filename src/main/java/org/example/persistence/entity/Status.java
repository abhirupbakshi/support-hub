package org.example.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "status")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Status {

    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "status_name")
    @NonNull
    private String name;

    @Column(name = "description")
    private String description;
}
