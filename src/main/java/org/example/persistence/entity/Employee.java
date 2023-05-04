package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.util.List;

@Entity
@Table(name = "employee")
@DiscriminatorColumn(name = "employee_type")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
    public abstract class Employee {

    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "employee_email")
    @NonNull
    private String email;

    @Column(name = "forename", nullable = false)
    @NonNull
    private String forename;

    @Column(name = "surname", nullable = false)
    @NonNull
    private String surname;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "employee_email"))
    @NonNull
    private List<Address> addresses;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "phone", joinColumns = @JoinColumn(name = "employee_email"))
    @NonNull
    private List<Phone> phones;

    @OneToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
