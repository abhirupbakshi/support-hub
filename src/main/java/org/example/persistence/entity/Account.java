package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.persistence.exception.InvalidCredentialException;
import java.time.LocalDateTime;
import java.util.Arrays;

@Entity
@Table(name = "accounts")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Accessors(chain = true)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "creation_date")
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    @Column(name = "password", nullable = false)
    @Getter(value = AccessLevel.NONE)
    @NonNull
    private byte[] password;

    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL)
    @NonNull
    private Employee employee;

    public boolean isPasswordEqual(byte[] password) {

        return Arrays.equals(this.password, password);
    }

    public void setPassword(byte[] oldPassword, byte[] newPassword) throws InvalidCredentialException {

        if (!isPasswordEqual(oldPassword)) {
            throw new InvalidCredentialException("Provided old password does not match");
        }

        this.password = newPassword;
    }
}
