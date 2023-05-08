package org.example.service.implementation;

import org.example.persistence.entity.*;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.EmployeeRepository;
import org.example.persistence.repository.implementation.*;
import org.example.service.EmployeeService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import java.util.Objects;

/**
 * <h3>Class EmployeeServiceImpl</h3>
 * This class is used to implement the methods of {@link EmployeeService} but with a generic context such that
 * it has all the implementation for each method present in the EmployeeService, but with two extra parameter to
 * each method, that is, two {@link Class<T>}s whose concrete implementation is intended to be provided by the class that
 * extends this class. This first extra parameter represents the class of the service requester and the second extra
 * parameter represents the class on which the service has been requested.
 * For the Class<T> parameters, it throws IllegalArgumentException if it is null.
 * <br>
 * Hence, it doesn't implement EmployeeService itself, and is only meant to be extended and following classes extends
 * this:
 * <ul>
 *     <li>{@link AccountServiceImpl}</li>
 *     <li>{@link }</li>
 *     <li>{@link }</li>
 * </ul>
 * @param <T> The type, that is either {@link Employee} or its child; implemented by the class that extends this class.
 */
abstract class EmployeeServiceImpl <T extends Employee> {

    protected void authenticate(String email, byte[] password)
            throws AuthenticationException, NotFoundException {

        if(!new AccountRepositoryImpl().get(email).isPasswordEqual(password))
            throw new AuthenticationException("Wrong password for email: " + email);
    }

    protected void authorize(Class<? extends Employee> requesterClass,
                             Class<?> onRequestedClass,
                             String permissionName)
            throws AuthorizationException {

        Boolean permission = new AuthorizationRepositoryImpl().getPermission(
                requesterClass.getSimpleName(), permissionName);

        if(permission == null) {
            if(!requesterClass.equals(onRequestedClass))
                throw new AuthorizationException(requesterClass.getSimpleName() + " don't have permission");
        }
        else if(!permission)
            throw new AuthorizationException(requesterClass.getSimpleName() + " don't have permission");
    }

    protected void authorizeAndAuthenticate(Class<? extends Employee> requesterClass,
                                          Class<?> onRequestedClass,
                                          String permissionName,
                                          String email, byte[] password)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        authorize(requesterClass, onRequestedClass, permissionName);
        authenticate(email, password);
    }

    protected EmployeeRepository<? extends Employee> employeeRepositoryImplDirector(
            Class<? extends Employee> className) {

        if(className.equals(HOD.class))
            return new HODRepositoryImpl();

        if(className.equals(Engineer.class))
            return new EngineerRepositoryImpl();

        if(className.equals(User.class))
            return new UserRepositoryImpl();

        return null;
    }

    protected void changeForename(Class<? extends Employee> requesterClass,
                                  Class<? extends Employee> onRequestedClass,
                                  String email, byte[] password, String newForename)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(newForename == null)
            throw new IllegalArgumentException("newForename parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                .toLowerCase() + "_update_forename", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).setForename(email, newForename);
    }

    protected void changeSurname(Class<? extends Employee> requesterClass,
                                  Class<? extends Employee> onRequestedClass,
                                  String email, byte[] password, String newSurname)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(newSurname == null)
            throw new IllegalArgumentException("newSurname parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_surname", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).setSurname(email, newSurname);
    }

    protected void addAddress(Class<? extends Employee> requesterClass,
                                 Class<? extends Employee> onRequestedClass,
                                 String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(address == null)
            throw new IllegalArgumentException("address parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_address", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).addAddress(email, address);
    }

    protected void updateAddress(Class<? extends Employee> requesterClass,
                                 Class<? extends Employee> onRequestedClass,
                                 String email, byte[] password, Address oldAddress, Address newAddress)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(oldAddress == null)
            throw new IllegalArgumentException("oldAddress parameter cannot be null");
        if(newAddress == null)
            throw new IllegalArgumentException("newAddress parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_address", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).updateAddress(email, oldAddress, newAddress);
    }

    protected void removeAddress(Class<? extends Employee> requesterClass,
                                 Class<? extends Employee> onRequestedClass,
                                 String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(address == null)
            throw new IllegalArgumentException("address parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_address", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).removeAddress(email, address);
    }

    protected void addPhone(Class<? extends Employee> requesterClass,
                              Class<? extends Employee> onRequestedClass,
                              String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(phone == null)
            throw new IllegalArgumentException("phone parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_phone", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).addPhone(email, phone);
    }

    protected void updatePhone(Class<? extends Employee> requesterClass,
                                 Class<? extends Employee> onRequestedClass,
                                 String email, byte[] password, Phone oldPhone, Phone newPhone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(oldPhone == null)
            throw new IllegalArgumentException("oldPhone parameter cannot be null");
        if(newPhone == null)
            throw new IllegalArgumentException("newPhone parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_phone", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).updatePhone(email, oldPhone, newPhone);
    }

    protected void removePhone(Class<? extends Employee> requesterClass,
                                 Class<? extends Employee> onRequestedClass,
                                 String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        if(requesterClass == null)
            throw new IllegalArgumentException("requesterClass parameter cannot be null");
        if(onRequestedClass == null)
            throw new IllegalArgumentException("onRequestedClass parameter cannot be null");
        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(phone == null)
            throw new IllegalArgumentException("phone parameter cannot be null");

        authorizeAndAuthenticate(requesterClass, onRequestedClass,
                onRequestedClass.getSimpleName()
                        .toLowerCase() + "_update_phone", email, password);

        Objects.requireNonNull(employeeRepositoryImplDirector(requesterClass)).removePhone(email, phone);
    }
}
