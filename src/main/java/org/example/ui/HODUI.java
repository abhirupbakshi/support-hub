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
        } catch (NotFoundException | AuthenticationException exception) {
            System.out.println("Invalid email or password");
        } catch (AuthorizationException exception) {
            System.out.println("You are not authorized to login");
        } catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        return null;
    }

    private void viewAllEngineers() {

        try {
            Print.engineers(hodService.getAllEngineers(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view engineers");
        }
        catch (NotFoundException ignored) { }
    }

    private void viewAllComplaints() {

        try {
            Print.complaints(hodService.getAllComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view all complaints");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void viewComplaint() {

        System.out.println("Yet to be implemented!!");
    }

    private void createEngineerAccount() {

        try {
            hodService.createEngineerAccount(email, password,
                    prompt.string("Enter engineer email: ", true, "Invalid engineer email"),
                    prompt.string("Enter engineer forename: ", true, "Invalid engineer forename"),
                    prompt.string("Enter engineer surname: ", true, "Invalid engineer surname"),
                    prompt.promptAddress(),
                    prompt.promptPhone(),
                    prompt.password("Enter engineer password: ", "Invalid engineer password")
            );

            System.out.println("Engineer account created successfully");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to create engineer account");
        }
        catch (NotFoundException ignored) { }
        catch (AlreadyExistException exception) {
            System.out.println("Engineer already exists");
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void viewEngineerComplaintAssociation() throws Exception {
        try {
            for(Engineer engineer : hodService.getAllEngineers(email, password)) {

                try {
                    Print.engineerAndAssignedComplaints(engineer,
                            hodService.getComplaintsAssignedToEngineer(email, password,
                                    engineer.getEmployeeEmail()));
                }
                catch (AuthenticationException exception) {
                    throw new AuthenticationException("Invalid email or password");
                }
                catch (AuthorizationException exception) {
                    throw new AuthorizationException("You are not authorized to view all complaints assigned to a engineer");
                }
                catch (NotFoundException ignored) { }
                catch (Exception exception) {
                    throw new Exception("Something went wrong");
                }
            }
        }
        catch (AuthenticationException exception) {
            throw new AuthenticationException("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            throw new AuthorizationException("You are not authorized view all engineers");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            throw new Exception("Something went wrong");
        }
    }

    private void assignComplaintToEngineer() {

        try {
            viewEngineerComplaintAssociation();
        }
        catch (AuthenticationException | AuthorizationException exception) {
            System.out.println(exception.getMessage());
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        viewAllComplaints();

        try {
            hodService.assignExistingComplainToExistingEngineer(email, password,
                    prompt.integer("Complaint ID: ",  "Invalid ID"),
                    prompt.string("Engineer Email: ", true, "Invalid Email"));

            System.out.println("Engineer was successfully assigned");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized assign complaints to engineers");
        }
        catch (NotFoundException | AlreadyExistException exception) {
            System.out.println(exception.getMessage());
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void removeComplaintFromEngineer() {

        try {
            viewEngineerComplaintAssociation();
        }
        catch (AuthenticationException | AuthorizationException exception) {
            System.out.println(exception.getMessage());
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        try {
            hodService.removeExistingComplainFromExistingEngineer(email, password,
                    prompt.integer("Complaint ID: ",  "Invalid ID"),
                    prompt.string("Engineer Email: ", true, "Invalid Email"));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to remove complaints from engineers");
        }

        System.out.println("Engineer-Complaint association successfully removed");
    }

    private void changePassword() {

        try {
            hodService.updatePassword(email,
                    prompt.password("Enter your current password: ", "Invalid password"),
                    prompt.password("Enter your new password: ", "Invalid password")
            );

            System.out.println("Password changed successfully");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to change password");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid current password");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private boolean deleteAccount() {

        try {
            hodService.deleteAccount(email,
                    prompt.password("Enter your password: ",
                            "Invalid password"));

            System.out.println("Account deleted successfully");

            return true;
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to delete account");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        return false;
    }

    public void prompt() {

        System.out.println("Successfully logged in");

        while (true) {

            System.out.println("""
                    1. View all engineers
                    2. View all complaints
                    3. View an individual complaint
                    4. Create engineer account
                    5. Assign a complaint to an engineer
                    6. Remove a complaint from an engineer
                    7. Change password
                    8. Delete account
                    9. Logout""");

            int choice;

            System.out.print("Enter your choice: ");

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
                default -> System.out.println("Invalid choice");
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
