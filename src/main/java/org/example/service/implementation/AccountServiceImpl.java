package org.example.service.implementation;

import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.InvalidCredentialException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.EmployeeRepository;
import org.example.persistence.repository.implementation.AccountRepositoryImpl;
import org.example.persistence.repository.implementation.EngineerRepositoryImpl;
import org.example.persistence.repository.implementation.HODRepositoryImpl;
import org.example.persistence.repository.implementation.UserRepositoryImpl;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.utilities.Password;

import java.util.List;
import java.util.Objects;

public abstract class AccountServiceImpl <T extends Employee> extends EmployeeServiceImpl <T> {

    protected Employee employeeDirector(
            Class<? extends Employee> className,
            String email, String forename, String surname,
            List<Address> addresses, List<Phone> phones) {

        if(className.equals(HOD.class))
            return  HOD.builder()
                    .employeeEmail(email)
                    .forename(forename)
                    .surname(surname)
                    .addresses(addresses)
                    .phones(phones)
                    .build();

        if(className.equals(Engineer.class))
            return  Engineer.builder()
                    .employeeEmail(email)
                    .forename(forename)
                    .surname(surname)
                    .addresses(addresses)
                    .phones(phones)
                    .build();

        if(className.equals(User.class))
            return User.builder()
                    .employeeEmail(email)
                    .forename(forename)
                    .surname(surname)
                    .addresses(addresses)
                    .phones(phones)
                    .build();

        return null;
    }

    protected void createAccount(
            Class<? extends Employee> requesterClass,
            Class<? extends Employee> onRequestedClass,
            String email, String forename, String surname,
            List<Address> addresses, List<Phone> phones, String password)
            throws AuthorizationException, AlreadyExistException {

        if(requesterClass == null)
                throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
                throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
                throw new IllegalArgumentException("email parameter cannot be null");
        if(forename == null)
                throw new IllegalArgumentException("forename parameter cannot be null");
        if(surname == null)
                throw new IllegalArgumentException("surname parameter cannot be null");
        if(addresses == null)
                throw new IllegalArgumentException("addresses parameter cannot be null");
        if(phones == null)
                throw new IllegalArgumentException("phones parameter cannot be null");
        if(password == null)
                throw new IllegalArgumentException("password parameter cannot be null");

        if(addresses.size() == 0)
                throw new IllegalArgumentException("addresses parameter cannot be empty");
        if(phones.size() == 0)
                throw new IllegalArgumentException("phones parameter cannot be empty");
        if(password.length() == 0)
                throw new IllegalArgumentException("password parameter cannot be empty");

        authorize(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName().toLowerCase() + "_create_account");

        new AccountRepositoryImpl().createAccount(employeeDirector(
                requesterClass, email, forename, surname, addresses, phones), Password.encrypt(password));
    }

    void updatePassword(
            Class<? extends Employee> requesterClass,
            Class<? extends Employee> onRequestedClass,
            String email, String currentPassword, String newPassword)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        if(requesterClass == null)
                throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
                throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
                throw new IllegalArgumentException("email parameter cannot be null");
        if(currentPassword == null)
                throw new IllegalArgumentException("oldPassword parameter cannot be null");
        if(newPassword == null)
                throw new IllegalArgumentException("newPassword parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName().toLowerCase() + "_update_password",
                email, Password.encrypt(currentPassword));

        try {
            new AccountRepositoryImpl().setPassword(
                email, Password.encrypt(currentPassword), Password.encrypt(newPassword)
            );
        } catch (InvalidCredentialException ignored) { }
    }

    void deleteAccount(
            Class<? extends Employee> requesterClass,
            Class<? extends Employee> onRequestedClass,
            String email, String password)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        if(email == null)
                throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
                throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName().toLowerCase() + "_delete_account",
                email, Password.encrypt(password));

        new AccountRepositoryImpl().deleteAccount(email);
    }

    Account getAccountDetails(
            Class<? extends Employee> requesterClass,
            Class<? extends Employee> onRequestedClass,
            String email, String password)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
                throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
                throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
                throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
                throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName().toLowerCase() + "_read_account",
                email, Password.encrypt(password));

        Account account = new AccountRepositoryImpl().get(email);

        try {
            account.setPassword(Password.encrypt(password), null);
        } catch (InvalidCredentialException ignored) { }

        return account;
    }
}
