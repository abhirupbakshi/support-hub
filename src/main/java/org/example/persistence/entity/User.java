package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("user")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class User extends Employee {

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.PERSIST)
    @Builder.Default
    List<Complaint> createdComplaints = new ArrayList<>();
}
