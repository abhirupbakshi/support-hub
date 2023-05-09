package org.example.service.implementation;

import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.implementation.ComplaintRepositoryImpl;
import org.example.persistence.repository.implementation.UserRepositoryImpl;
import org.example.service.UserService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.utilities.Password;
import java.util.List;

/**
 * <h3>Class UserServiceImpl</h3>
 * This is a {@link UserService} implementation which also extends {@link AccountServiceImpl}.
 */
public class UserServiceImpl extends AccountServiceImpl<User> implements UserService {

    @Override
    public Account getAccountDetails(String email, String password)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        return super.getAccountDetails(User.class, User.class, email, password);
    }

    @Override
    public void createAccount(String email, String forename, String surname,
                              List<Address> addresses, List<Phone> phones, String password)
            throws AuthorizationException, AlreadyExistException {

        super.createAccount(User.class, User.class, email, forename, surname, addresses, phones, password);
    }

    @Override
    public void updatePassword(String email, String currentPassword, String newPassword)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.updatePassword(User.class, User.class, email, currentPassword, newPassword);
    }

    @Override
    public void deleteAccount(String email, String password)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.deleteAccount(User.class, User.class, email, password);
    }

    @Override
    public void changeForename(String email, byte[] password, String newForename)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.changeForename(User.class, User.class, email, password, newForename);
    }

    @Override
    public void changeSurname(String email, byte[] password, String newSurname)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.changeSurname(User.class, User.class, email, password, newSurname);
    }

    @Override
    public void addAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addAddress(User.class, User.class, email, password, address);
    }

    @Override
    public void updateAddress(String email, byte[] password, Address oldAddress, Address newAddress)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updateAddress(User.class, User.class, email, password, oldAddress, newAddress);
    }

    @Override
    public void removeAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removeAddress(User.class, User.class, email, password, address);
    }

    @Override
    public void addPhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addPhone(User.class, User.class, email, password, phone);
    }

    @Override
    public void updatePhone(String email, byte[] password, Phone oldPhone, Phone newPhone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updatePhone(User.class, User.class, email, password, oldPhone, newPhone);
    }

    @Override
    public void removePhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removePhone(User.class, User.class, email, password, phone);
    }

    @Override
    public List<Complaint> getAllCreatedComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(User.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_read_registered",
                email, Password.encrypt(password));

        List<Complaint> complaints = new UserRepositoryImpl().getCreatedComplains(email);
        complaints.forEach(c -> c.setAssignedEngineers(null));
        return complaints;
    }

    @Override
    public Complaint getCreatedComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        List<Complaint> complaints = getAllCreatedComplaints(email, password);

        for(Complaint complaint : complaints) {
            if(complaint.getId() == complaintId)
                return complaint;
        }

        throw new NotFoundException("Complaint not found with id " + complaintId);
    }

    @Override
    public void registerComplaint(String email, String password, String description, String category)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(description == null)
            throw new IllegalArgumentException("description parameter cannot be null");
        if(category == null)
            throw new IllegalArgumentException("category parameter cannot be null");

        authorizeAndAuthenticate(User.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_create_register",
                email, Password.encrypt(password));

        new ComplaintRepositoryImpl().createComplain(description, email, category, "Open");
    }

    @Override
    public void changeComplaintStatusToResolved(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        getCreatedComplaint(email, password, complaintId);

        authorizeAndAuthenticate(User.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_update_status-resolved",
                email, Password.encrypt(password));


        new ComplaintRepositoryImpl().setStatus(complaintId, "Resolved");
    }
}
