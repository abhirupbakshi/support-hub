package org.example.ui;

import org.example.persistence.entity.Engineer;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.service.HODService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.HODServiceImpl;
import org.example.ui.utilities.Print;
import org.example.ui.utilities.Prompt;
import java.util.List;
import java.util.Scanner;

public class HODUI {

    private final String password;
    private final String email;
    private final Scanner scanner;
    private final HODService hodService = new HODServiceImpl();
    private final Prompt prompt;

    public static HODUI isCredentialsCorrect(Scanner scanner, String email, String password) {

        HODService hodService = new HODServiceImpl();

        try {
            hodService.getAccountDetails(email, password);
            return new HODUI(password, email, scanner);
        }
        catch (NotFoundException | AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to login"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }

        return null;
    }

    private void viewAllEngineers() {

        try {
            Print.employees(hodService.getAllEngineers(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view employees"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void viewAllComplaints() {

        try {
            Print.complaints(hodService.getAllComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view all complaints"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void viewComplaint() {

        viewAllComplaints();

        try {
            Print.complaint(hodService.getComplaint(email, password,
                    prompt.integer(Print.boldString("Enter complaint ID: "), Print.redString("Invalid ID"))
            ));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view complaints"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void createEngineerAccount() {

        try {
            hodService.createEngineerAccount(email, password,
                    prompt.string(Print.boldString("Enter engineer email: "), true, Print.redString("Invalid engineer email")),
                    prompt.string(Print.boldString("Enter engineer forename: "), true, Print.redString("Invalid engineer forename")),
                    prompt.string(Print.boldString("Enter engineer surname: "), true, Print.redString("Invalid engineer surname")),
                    prompt.promptAddress(),
                    prompt.promptPhone(),
                    prompt.password(Print.boldString("Enter engineer password: "), Print.redString("Invalid engineer password"))
            );

            System.out.println(Print.greenString("Engineer account created successfully"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to create engineer account"));
        }
        catch (NotFoundException ignored) { }
        catch (AlreadyExistException exception) {
            System.out.println(Print.redString("Engineer already exists"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void viewEngineerComplaintAssociation() throws Exception {
        try {
            List<Engineer> engineers = hodService.getAllEngineers(email, password);

            if(engineers.isEmpty()) {

                System.out.println(Print.redString("No engineers found"));
                return;
            }

            for(Engineer engineer : engineers) {

                try {
                    Print.engineerComplaintAssociations(engineer,
                            hodService.getComplaintsAssignedToEngineer(email, password,
                                    engineer.getEmployeeEmail()));
                }
                catch (AuthenticationException exception) {
                    throw new AuthenticationException(Print.redString("Invalid email or password"));
                }
                catch (AuthorizationException exception) {
                    throw new AuthorizationException(Print.redString("You are not authorized to view all complaints assigned to a employee"));
                }
                catch (NotFoundException ignored) { }
                catch (Exception exception) {
                    throw new Exception(Print.redString("Something went wrong"));
                }
            }
        }
        catch (AuthenticationException exception) {
            throw new AuthenticationException(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            throw new AuthorizationException(Print.redString("You are not authorized view all employees"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            throw new Exception(Print.redString("Something went wrong"));
        }
    }

    private void assignComplaintToEngineer() {

        try {
            viewEngineerComplaintAssociation();
        }
        catch (AuthenticationException | AuthorizationException | NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }

        viewAllComplaints();

        try {
            hodService.assignExistingComplainToExistingEngineer(email, password,
                    prompt.integer(Print.boldString("Complaint ID: "),  Print.redString("Invalid ID")),
                    prompt.string(Print.boldString("Engineer Email: "), true, Print.redString("Invalid Email")));

            System.out.println(Print.greenString("Engineer was successfully assigned"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized assign complaints to employees"));
        }
        catch (NotFoundException | AlreadyExistException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void removeComplaintFromEngineer() {

        try {
            viewEngineerComplaintAssociation();
        }
        catch (AuthenticationException | AuthorizationException | NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }

        try {
            hodService.removeExistingComplainFromExistingEngineer(email, password,
                    prompt.integer(Print.boldString("Complaint ID: "),  Print.redString("Invalid ID")),
                    prompt.string(Print.boldString("Engineer Email: "), true, Print.redString("Invalid Email")));

            System.out.println(Print.greenString("Engineer-Complaint association successfully removed"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to remove complaints from employees"));
        }
    }

    private void changePassword() {

        try {
            hodService.updatePassword(email,
                    prompt.password(Print.boldString("Enter your current password: "), Print.redString("Invalid password")),
                    prompt.password(Print.boldString("Enter your new password: "), Print.redString("Invalid password"))
            );

            System.out.println(Print.greenString("Password changed successfully"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to change password"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid current password"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private boolean deleteAccount() {

        try {
            hodService.deleteAccount(email,
                    prompt.password(Print.boldString("Enter your password: "),
                            Print.redString("Invalid password")));

            System.out.println(Print.greenString("Account deleted successfully"));

            return true;
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to delete account"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }

        return false;
    }

    public void prompt() {

        System.out.println(Print.greenString("Successfully logged in"));

        while (true) {

            System.out.println(Print.boldString("1. ") + "View all engineers");
            System.out.println(Print.boldString("2. ") + "View all complaints");
            System.out.println(Print.boldString("3. ") + "View an individual complaint");
            System.out.println(Print.boldString("4. ") + "Create an engineer account");
            System.out.println(Print.boldString("5. ") + "Assign a complaint to an engineer");
            System.out.println(Print.boldString("6. ") + "Remove a complaint from an engineer");
            System.out.println(Print.boldString("7. ") + "Change password");
            System.out.println(Print.boldString("8. ") + "Delete account");
            System.out.println(Print.boldString("9. ") + "Logout");

            int choice;

            System.out.print(Print.boldString("Enter your choice: "));

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> viewAllEngineers();
                case 2 -> viewAllComplaints();
                case 3 -> viewComplaint();
                case 4 -> createEngineerAccount();
                case 5 -> assignComplaintToEngineer();
                case 6 -> removeComplaintFromEngineer();
                case 7 -> changePassword();
                case 8 -> { if(deleteAccount()) return; }
                case 9 -> { return; }
                default -> System.out.println(Print.redString("Invalid choice"));
            }
        }
    }

    public HODUI(String password, String email, Scanner scanner) {
        this.password = password;
        this.email = email;
        this.scanner = scanner;
        this.prompt = new Prompt(scanner);
    }
}
