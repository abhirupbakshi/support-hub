package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Class UserDTO</h3>
 * This class represents any user employee.
 */
@Entity
@DiscriminatorValue("User")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class User extends Employee {

    /**
     * <h4>Optional</h4>
     * Created complaints by the user.
     */
    @OneToMany(targetEntity = Complaint.class, mappedBy = "createdBy", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @Builder.Default
    List<Complaint> createdComplaints = new ArrayList<>();
}
