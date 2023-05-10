package org.example.service.implementation;

import jakarta.persistence.FetchType;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.implementation.ComplaintRepositoryImpl;
import org.example.persistence.repository.implementation.EngineerRepositoryImpl;
import org.example.service.EngineerService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.utilities.Password;
import java.util.List;

/**
 * <h3>Class EngineerServiceImpl</h3>
 * This is a {@link EngineerService} implementation which also extends {@link AccountServiceImpl}.
 */
public class EngineerServiceImpl extends AccountServiceImpl<Engineer>
        implements EngineerService {

    /**
     * This method uses {@link AccountServiceImpl#getAccountDetails(Class, Class, String, String)}
     * and returns an account of an employee with the {@link FetchType#LAZY} fields set to null.
     */
    @Override
    public Account getAccountDetails(String email, String password)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        Account account = super.getAccountDetails(Engineer.class, Engineer.class, email, password);
        ((Engineer) account.getEmployee()).setAssignedComplaints(null);
        return account;
    }

    @Override
    public void changeForename(String email, byte[] password, String newForename)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.changeForename(Engineer.class, Engineer.class, email, password, newForename);
    }

    @Override
    public void changeSurname(String email, byte[] password, String newSurname)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        super.changeSurname(Engineer.class, Engineer.class, email, password, newSurname);
    }

    @Override
    public void addAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addAddress(Engineer.class, Engineer.class, email, password, address);
    }

    @Override
    public void updateAddress(String email, byte[] password, Address oldAddress, Address newAddress)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updateAddress(Engineer.class, Engineer.class, email, password, oldAddress, newAddress);
    }

    @Override
    public void removeAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removeAddress(Engineer.class, Engineer.class, email, password, address);
    }

    @Override
    public void addPhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addPhone(Engineer.class, Engineer.class, email, password, phone);
    }

    @Override
    public void updatePhone(String email, byte[] password, Phone oldPhone, Phone newPhone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updatePhone(Engineer.class, Engineer.class, email, password, oldPhone, newPhone);
    }

    @Override
    public void removePhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removePhone(Engineer.class, Engineer.class, email, password, phone);
    }

    @Override
    public void createAccount(String email, String forename, String surname,
                              List<Address> addresses, List<Phone> phones, String password)
            throws AuthorizationException, AlreadyExistException {

        super.createAccount(Engineer.class, Engineer.class, email, forename, surname,
                addresses, phones, password);
    }

    @Override
    public void updatePassword(String email, String currentPassword, String newPassword)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.updatePassword(Engineer.class, Engineer.class, email, currentPassword, newPassword);
    }

    @Override
    public void deleteAccount(String email, String password)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.deleteAccount(Engineer.class, Engineer.class, email, password);
    }

    @Override
    public List<Complaint> getAllAssignedComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(Engineer.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_read_assigned",
                email, Password.encrypt(password));

        List<Complaint> complaints = new EngineerRepositoryImpl().getAssignedComplains(email);
        complaints.forEach(c -> c.setAssignedEngineers(null));
        return complaints;
    }

    @Override
    public Complaint getAssignedComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        List<Complaint> complaints = getAllAssignedComplaints(email, password);

        for(Complaint complaint : complaints) {
            if(complaint.getId() == complaintId)
                return complaint;
        }

        throw new NotFoundException("Complaint not found with id " + complaintId);
    }

    @Override
    public void changeComplaintStatus(String email, String password, int complaintId, String status)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        getAssignedComplaint(email, password, complaintId);

        if(status == null)
            throw new IllegalArgumentException("status parameter cannot be null");

        authorizeAndAuthenticate(Engineer.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_update_status",
                email, Password.encrypt(password));

        new ComplaintRepositoryImpl().setStatus(complaintId, status);
    }

    @Override
    public void addUpdateToComplaint(String email, String password, int complaintId, String update)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(update == null)
            throw new IllegalArgumentException("update parameter cannot be null");

        getAssignedComplaint(email, password, complaintId);

        authorizeAndAuthenticate(Engineer.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_create_update",
                email, Password.encrypt(password));

        new ComplaintRepositoryImpl().addUpdate(complaintId, update);
    }
}
