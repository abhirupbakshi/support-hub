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

@Entity
@Table(name = "complain")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "complain_id")
    @Setter(value = AccessLevel.NONE)
    private int id;

    @Column(name = "creation_date", nullable = false)
    @Setter(value = AccessLevel.NONE)
    @Builder.Default
    private LocalDateTime createdOn = LocalDateTime.now();

    @Column(name = "description", nullable = false)
    @NonNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Setter(value = AccessLevel.NONE)
    User createdBy;

    @ManyToOne
    @JoinColumn(name = "category", nullable = false)
    @NonNull
    private Category category;

    @ManyToOne
    @JoinColumn(name = "status", nullable = false)
    @NonNull
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "assigned_engineers",
            joinColumns = @JoinColumn(name = "complain_id"),
            inverseJoinColumns = @JoinColumn(name = "engineer_email")
    )
    @Builder.Default
    private List<Engineer> assignedEngineers = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "updates", joinColumns = @JoinColumn(name = "complain_id"))
    @Builder.Default
    List<Update> updates = new ArrayList<>();
}
