package org.example.ui;

import org.example.persistence.exception.NotFoundException;
import org.example.service.EngineerService;
import org.example.service.HODService;
import org.example.service.UserService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.EngineerServiceImpl;
import org.example.service.implementation.HODServiceImpl;
import org.example.service.implementation.UserServiceImpl;
import org.example.ui.utilities.Print;
import org.example.ui.utilities.Prompt;

import java.util.Scanner;

public class EngineerUI {

    private final String password;
    private final String email;
    private final Scanner scanner;
    private final EngineerService engineerService = new EngineerServiceImpl();
    private final Prompt prompt;

    public static EngineerUI isCredentialsCorrect(Scanner scanner, String email, String password) {

        EngineerService engineerService = new EngineerServiceImpl();

        try {
            engineerService.getAccountDetails(email, password);
            return new EngineerUI(password, email, scanner);
        } catch (NotFoundException | AuthenticationException exception) {
            System.out.println("Invalid email or password");
        } catch (AuthorizationException exception) {
            System.out.println("You are not authorized to login");
        } catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        return null;
    }

    private void viewAllAssignedComplaints() {

        try {
            Print.complaints(engineerService.getAllAssignedComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view assigned complaints");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void viewAssignedComplaint() {

        viewAllAssignedComplaints();

        try {
            Print.complaint(engineerService.getAssignedComplaint(email, password,
                    prompt.integer("Enter Complaint ID: ", "Invalid ID")
            ));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view assigned complaints");
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void changeComplaintStatus() {

        viewAllAssignedComplaints();

        try {
            engineerService.changeComplaintStatus(email, password,
                    prompt.integer("Enter Complaint ID: ", "Invalid ID"),
                    prompt.string("Enter new status: ", true, "Invalid status")
            );

            System.out.println("Complaint status changed successfully");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to change complaint status");
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void addUpdate() {

        viewAllAssignedComplaints();

        try {
            engineerService.addUpdateToComplaint(email, password,
                    prompt.integer("Enter complaint ID: ", "Invalid ID"),
                    prompt.string("Enter new update: ", true, "Invalid update")
            );

            System.out.println("Update added successfully");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to add updates to complaints");
        }
        catch (NotFoundException exception) {
            System.out.println(exception.getMessage());
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void changePassword() {

        try {
            engineerService.updatePassword(email,
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
            engineerService.deleteAccount(email,
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
                    1. View all assigned complaints
                    2. View an individual assigned complaint
                    3. Change a complaint status
                    4. Add a update to a complaint
                    5. Change password
                    6. Delete account
                    7. Logout""");

            int choice;

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException exception) {
                choice = -1;
            }

            switch (choice) {
                case 1 -> viewAllAssignedComplaints();
                case 2 -> viewAssignedComplaint();
                case 3 -> changeComplaintStatus();
                case 4 -> addUpdate();
                case 5 -> changePassword();
                case 6 -> {
                    if(deleteAccount()) return;
                }
                case 7 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public EngineerUI(String password, String email, Scanner scanner) {
        this.password = password;
        this.email = email;
        this.scanner = scanner;
        this.prompt = new Prompt(scanner);
    }
}
