package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.example.persistence.exception.InvalidCredentialException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * <h3>Class Account</h3>
 * This class represents an account of an {@link Employee}'s child classes.
 */
@Entity
@Table(name = "Accounts")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Accessors(chain = true)
public class Account {

    /**
     * <h4>Optional</h4>
     * Used as primary key for the database, auto generated.
     * Cannot be set after the object is created.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    /**
     * <h4>Optional</h4>
     * The creation date of the account.
     * Cannot be set after the object is created.
     */
    @Column(name = "creation_date", nullable = false)
    @Builder.Default
    private LocalDateTime creationDate = LocalDateTime.now();

    /**
     * <h4>Required</h4>
     * Password of the account.
     * To set the value, use {@link #setPassword(byte[], byte[])}
     */
    @Column(name = "password", nullable = false)
    @Getter(value = AccessLevel.NONE)
    @NonNull
    private byte[] password;

    /**
     * <h4>Required</h4>
     * Employee of the account.
     * Cannot be set after the object is created.
     */
    @OneToOne(targetEntity = Employee.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_email", nullable = false)
    @NonNull
    private Employee employee;

    /**
     * Checks if the provided password matches the account's password.
     * @param password the password to check
     * @return true if the password matches, else false
     */
    public boolean isPasswordEqual(byte[] password) {

        return Arrays.equals(this.password, password);
    }

    /**
     * Sets the password of the account. To set the password of the account, the caller
     * needs to provide the current password. If the provided password matches, then
     * account's password will be updated.
     * @param oldPassword the current password
     * @param newPassword the new password
     * @throws InvalidCredentialException if the provided password does not match
     */
    public void setPassword(byte[] oldPassword, byte[] newPassword) throws InvalidCredentialException {

        if (!isPasswordEqual(oldPassword)) {
            throw new InvalidCredentialException("Provided old password does not match");
        }

        this.password = newPassword;
    }
}
