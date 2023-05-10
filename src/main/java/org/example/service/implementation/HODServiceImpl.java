package org.example.service.implementation;

import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.implementation.AccountRepositoryImpl;
import org.example.persistence.repository.implementation.ComplaintRepositoryImpl;
import org.example.persistence.repository.implementation.EngineerRepositoryImpl;
import org.example.service.HODService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.utilities.Password;
import java.util.List;

/**
 * <h3>Class HODServiceImpl</h3>
 * This is a {@link HODService} implementation which also extends {@link AccountServiceImpl}.
 */
public class HODServiceImpl extends AccountServiceImpl<HOD> implements HODService {

    @Override
    public Account getAccountDetails(String email, String password)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        return super.getAccountDetails(HOD.class, HOD.class, email, password);
    }

    @Override
    public void createAccount(String email, String forename, String surname,
                              List<Address> addresses, List<Phone> phones, String password)
            throws AuthorizationException, AlreadyExistException {

        super.createAccount(HOD.class, HOD.class, email, forename, surname, addresses, phones, password);
    }

    @Override
    public void updatePassword(String email, String currentPassword, String newPassword)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.updatePassword(HOD.class, HOD.class, email, currentPassword, newPassword);
    }

    @Override
    public void deleteAccount(String email, String password)
            throws AuthorizationException, AuthenticationException, NotFoundException {

        super.deleteAccount(HOD.class, HOD.class, email, password);
    }

    @Override
    public void changeForename(String email, byte[] password, String newForename)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.changeForename(HOD.class, HOD.class, email, password, newForename);
    }

    @Override
    public void changeSurname(String email, byte[] password, String newSurname)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.changeSurname(HOD.class, HOD.class, email, password, newSurname);
    }

    @Override
    public void addAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addAddress(HOD.class, HOD.class, email, password, address);
    }

    @Override
    public void updateAddress(String email, byte[] password, Address oldAddress, Address newAddress)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updateAddress(HOD.class, HOD.class, email, password, oldAddress, newAddress);
    }

    @Override
    public void removeAddress(String email, byte[] password, Address address)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removeAddress(HOD.class, HOD.class, email, password, address);
    }

    @Override
    public void addPhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.addPhone(HOD.class, HOD.class, email, password, phone);
    }

    @Override
    public void updatePhone(String email, byte[] password, Phone oldPhone, Phone newPhone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.updatePhone(HOD.class, HOD.class, email, password, oldPhone, newPhone);
    }

    @Override
    public void removePhone(String email, byte[] password, Phone phone)
            throws NotFoundException, AuthenticationException, AuthorizationException {

        super.removePhone(HOD.class, HOD.class, email, password, phone);
    }

    @Override
    public List<Engineer> getAllEngineers(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(HOD.class, Engineer.class,
                Engineer.class.getSimpleName().toLowerCase() + "_read_details",
                email, Password.encrypt(password));

        return new EngineerRepositoryImpl().getAll();
    }

    @Override
    public List<Complaint> getAllComplaints(String email, String password)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");

        authorizeAndAuthenticate(HOD.class, Complaint.class,
                Complaint.class.getSimpleName().toLowerCase() + "_read_details",
                email, Password.encrypt(password));

        return new ComplaintRepositoryImpl().getAll();
    }

    @Override
    public Complaint getComplaint(String email, String password, int complaintId)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        List<Complaint> complaints = getAllComplaints(email, password);

        for(Complaint complaint : complaints) {
            if(complaint.getId() == complaintId)
                return complaint;
        }

        throw new NotFoundException("Complaint not found with id " + complaintId);
    }

    @Override
    public List<Complaint> getComplaintsAssignedToEngineer
            (String email, String password, String engineerEmail)
            throws AuthenticationException, AuthorizationException, NotFoundException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(engineerEmail == null)
            throw new IllegalArgumentException("engineerEmail parameter cannot be null");

        AuthorizationException exception1 = null;
        AuthorizationException exception2 = null;

        try {
            authorizeAndAuthenticate(HOD.class, Complaint.class,
                    Complaint.class.getSimpleName().toLowerCase() + "_read_details",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception1 = exception;
        }

        try {
            authorizeAndAuthenticate(HOD.class, Engineer.class,
                    Engineer.class.getSimpleName().toLowerCase() + "_read_details",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception2 = exception;
        }

        if(exception1 != null || exception2 != null)
            throw exception1 == null ? exception2 : exception1;

        return new EngineerRepositoryImpl().getAssignedComplains(engineerEmail);
    }

    @Override
    public void assignExistingComplainToExistingEngineer(
            String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, AuthorizationException, NotFoundException, AlreadyExistException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(engineerEmail == null)
            throw new IllegalArgumentException("engineerEmail parameter cannot be null");

        AuthorizationException exception1 = null;
        AuthorizationException exception2 = null;
        
        try {
            authorizeAndAuthenticate(HOD.class, Complaint.class,
                    Complaint.class.getSimpleName().toLowerCase() + "_update_assign-engineer",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception1 = exception;
        }

        try {
            authorizeAndAuthenticate(HOD.class, Engineer.class,
                    Engineer.class.getSimpleName().toLowerCase() + "_update_assign-complaint",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception2 = exception;
        }
        
        if(exception1 != null && exception2 != null)
            throw exception1;

        new EngineerRepositoryImpl().assignExistingComplain(engineerEmail, complaintId);
    }

    @Override
    public void removeExistingComplainFromExistingEngineer(String email, String password, int complaintId, String engineerEmail)
            throws AuthenticationException, NotFoundException, AuthorizationException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(engineerEmail == null)
            throw new IllegalArgumentException("engineerEmail parameter cannot be null");

        AuthorizationException exception1 = null;
        AuthorizationException exception2 = null;

        try {
            authorizeAndAuthenticate(HOD.class, Complaint.class,
                    Complaint.class.getSimpleName().toLowerCase() + "_delete_assigned-engineer",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception1 = exception;
        }

        try {
            authorizeAndAuthenticate(HOD.class, Engineer.class,
                    Engineer.class.getSimpleName().toLowerCase() + "_delete_assigned-complaint",
                    email, Password.encrypt(password));
        }
        catch (AuthorizationException exception) {
            exception2 = exception;
        }

        if(exception1 != null && exception2 != null)
            throw exception1;

        new EngineerRepositoryImpl().removeAssignedComplain(engineerEmail, complaintId);
    }

    @Override
    public void createEngineerAccount(
            String email, String password,
            String engineerEmail, String engineerForename, String engineerSurname,
            List<Address> addresses, List<Phone> phones, String engineerPassword)
            throws AuthenticationException, AuthorizationException, NotFoundException, AlreadyExistException {

        if(email == null)
            throw new IllegalArgumentException("email parameter cannot be null");
        if(password == null)
            throw new IllegalArgumentException("password parameter cannot be null");
        if(engineerEmail == null)
            throw new IllegalArgumentException("engineerEmail parameter cannot be null");
        if(engineerForename == null)
            throw new IllegalArgumentException("engineerForename parameter cannot be null");
        if(engineerSurname == null)
            throw new IllegalArgumentException("engineerSurname parameter cannot be null");
        if(addresses == null)
            throw new IllegalArgumentException("addresses parameter cannot be null");
        if(phones == null)
            throw new IllegalArgumentException("phones parameter cannot be null");

        authorizeAndAuthenticate(HOD.class, Engineer.class,
                Engineer.class.getSimpleName().toLowerCase() + "_create_account",
                email, Password.encrypt(password));

        new AccountRepositoryImpl().createAccount(
                Engineer.builder()
                        .employeeEmail(engineerEmail)
                        .forename(engineerForename)
                        .surname(engineerSurname)
                        .addresses(addresses)
                        .phones(phones)
                        .build(),
                Password.encrypt(engineerPassword)
        );
    }
}
