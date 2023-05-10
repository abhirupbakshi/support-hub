package org.example.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import java.util.List;
import java.util.Objects;

/**
 * <h3>Class Employee</h3>
 * This class represents different kinds of employee, hence it's an abstract class.
 */
@Entity
@Table(name = "Employees")
@DiscriminatorColumn(name = "employee_type")
@SuperBuilder
@NoArgsConstructor(force = true)
@Getter
@Setter
@Accessors(chain = true)
public abstract class Employee {

    /**
     * <h4>Required</h4>
     * Email of the employee.
     * Also used as primary key in the database.
     */
    @Id
    @Setter(value = AccessLevel.NONE)
    @Column(name = "employee_email")
    @NonNull
    private String employeeEmail;

    /**
     * <h4>Required</h4>
     * First name of the employee.
     */
    @Column(name = "forename", nullable = false)
    @NonNull
    private String forename;

    /**
     * <h4>Required</h4>
     * Surname of the employee.
     */
    @Column(name = "surname", nullable = false)
    @NonNull
    private String surname;

    /**
     * <h4>Required</h4>
     * Addresses of the employee. An employee can have multiple addresses. But
     * there has to be at least one address and addresses cannot be null
     */
    @ElementCollection(targetClass = Address.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Addresses", joinColumns = @JoinColumn(name = "employee_email"))
    @Singular
    @NonNull
    private List<Address> addresses;

    /**
     * <h4>Required</h4>
     * Phone numbers of the employee. An employee can have multiple phone numbers. But
     * there has to be at least one number and phones cannot be null
     */
    @ElementCollection(targetClass = Phone.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Phones", joinColumns = @JoinColumn(name = "employee_email"))
    @Singular
    @NonNull
    private List<Phone> phones;

    /**
     * <h4>Required</h4>
     * Account of the employee.
     */
    @OneToOne(targetEntity = Account.class, mappedBy = "employee")
    // @NonNull is not used here to escape circular dependency between Account and Employee
    private Account account;

    protected Employee(EmployeeBuilder<?, ?> b) {
        Objects.requireNonNull(b.addresses, "Employee address cannot be null");
        Objects.requireNonNull(b.phones, "Employee phone cannot be null");

        if(b.addresses.isEmpty())
            throw new IllegalArgumentException("Employee must have at least one address");
        if(b.phones.isEmpty())
            throw new IllegalArgumentException("Employee must have at least one phone");

        this.employeeEmail = b.employeeEmail;
        this.forename = b.forename;
        this.surname = b.surname;
        this.addresses = b.addresses;
        this.phones = b.phones;
        this.account = b.account;
    }

    /**
     * Setter for the addresses
     * @param address The address
     */
    public void setAddresses(Address address) {

        this.addresses.add(address);
    }

    /**
     * Setter for the phones
     * @param phone The phone
     */
    public void setPhones(Phone phone) {

        this.phones.add(phone);
    }

    /**
     * Removes an address. If it's the only remaining address, it throws IllegalStateException
     * @param address The address to remove
     * @return True if the address was removed
     */
    public boolean removeAddress(Address address) {

        if(address != null) {

            if(this.addresses.contains(address) && this.addresses.size() == 1)
                throw new IllegalStateException("Cannot remove the only address");

            return this.addresses.remove(address);
        }
        else
            return false;
    }

    /**
     * Removes a phone. If it's the only remaining phone, it throws IllegalStateException
     * @param phone The address to remove
     * @return True if the phone was removed
     */
    public boolean removePhone(Phone phone) {

        if(phone != null) {

            if(this.phones.contains(phone) && this.phones.size() == 1)
                throw new IllegalStateException("Cannot remove the only phone");

            return this.phones.remove(phone);
        }
        else
            return false;
    }
}
