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

/**
 * <h3>Class Engineer</h3>
 * This class represents engineer employee.
 */
@Entity
@DiscriminatorValue("Engineer")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Engineer extends Employee {

    /**
     * <h4>Optional</h4>
     * Assigned complaints to the engineer.
     */
    @ManyToMany(targetEntity = Complaint.class, mappedBy = "assignedEngineers")
    @Builder.Default
    private List<Complaint> assignedComplaints = new ArrayList<>();
}
