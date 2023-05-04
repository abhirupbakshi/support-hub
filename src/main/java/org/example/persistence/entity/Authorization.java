package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "authorization_level")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Authorization {

    @Id
    @Column(name = "employee_type", nullable = false)
    @NonNull
    private String employeeType;

    @Column(name = "level", nullable = false)
    @NonNull
    private int level;
}
