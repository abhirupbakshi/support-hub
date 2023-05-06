package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <h3>Class Complaint</h3>
 * This class represents a complaint made by (in case it's {@link User}) / assigned to
 * (in case it's {@link Engineer}) an {@link Employee}.
 */
@Entity
@Table(name = "Complaints")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Complaint {

    /**
     * <h4>Optional</h4>
     * Id of the complaint.
     * Auto generated, cannot be set after the object is created.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complaint_id")
    @Setter(value = AccessLevel.NONE)
    private int id;

    /**
     * <h4>Optional</h4>
     * Creation date of the complaint.
     * Auto generated, cannot be set after the object is created.
     */
    @Column(name = "creation_date", nullable = false)
    @Setter(value = AccessLevel.NONE)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    /**
     * <h4>Required</h4>
     * Description of the complaint.
     */
    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    /**
     * <h4>Required</h4>
     * The {@link User} who made the complaint.
     * Cannot be set after the object is created.
     * If the user is deleted, the complaint will not be deleted.
     */
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "created_by_user_email")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Setter(value = AccessLevel.NONE)
    User createdBy;

    /**
     * <h4>Required</h4>
     * The {@link Category} which the complaint belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    @NonNull
    private Category category;

    /**
     * <h4>Required</h4>
     * The {@link Status} which the complaint belongs to.
     */
    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    @NonNull
    private Status status;

    /**
     * <h4>Optional</h4>
     * The {@link Engineer}s who are assigned to the complaint.
     */
    @ManyToMany(targetEntity = Engineer.class)
    @JoinTable(
            name = "Assigned_Engineers",
            joinColumns = @JoinColumn(name = "complaint_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_email")
    )
    @Builder.Default
    private List<Engineer> assignedEngineers = new ArrayList<>();

    /**
     * <h4>Optional</h4>
     * {@link Update}s that are added to the complaint.
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Updates", joinColumns = @JoinColumn(name = "complain_id"))
    @Builder.Default
    List<Update> updates = new ArrayList<>();
}
