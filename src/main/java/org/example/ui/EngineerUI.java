package org.example.ui;

import org.example.persistence.exception.NotFoundException;
import org.example.service.EngineerService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.EngineerServiceImpl;
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

    private void viewAllAssignedComplaints() {

        try {
            Print.complaints(engineerService.getAllAssignedComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view assigned complaints"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void viewAssignedComplaint() {

        viewAllAssignedComplaints();

        try {
            Print.complaint(engineerService.getAssignedComplaint(email, password,
                    prompt.integer(Print.boldString("Enter complaint ID: "), Print.redString("Invalid ID"))
            ));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view assigned complaints"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void changeComplaintStatus() {

        viewAllAssignedComplaints();

        try {
            engineerService.changeComplaintStatus(email, password,
                    prompt.integer(Print.boldString("Enter complaint ID: "), Print.redString("Invalid ID")),
                    prompt.string(Print.boldString("Enter new status: "), true, Print.redString("Invalid status"))
            );

            System.out.println(Print.greenString("Complaint status changed successfully"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to change complaint status"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void addUpdate() {

        viewAllAssignedComplaints();

        try {
            engineerService.addUpdateToComplaint(email, password,
                    prompt.integer(Print.boldString("Enter complaint ID: "), Print.redString("Invalid ID")),
                    prompt.string(Print.boldString("Enter new update: "), true, Print.redString("Invalid update"))
            );

            System.out.println(Print.greenString("Update added successfully"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to add updates to complaints"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString(exception.getMessage()));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void changePassword() {

        try {
            engineerService.updatePassword(email,
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
            engineerService.deleteAccount(email,
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

            System.out.println(Print.boldString("1. ") + "View all assigned complaints");
            System.out.println(Print.boldString("2. ") + "View an individual assigned complaint");
            System.out.println(Print.boldString("3. ") + "Change a complaint status");
            System.out.println(Print.boldString("4. ") + "Add a update to a complaint");
            System.out.println(Print.boldString("5. ") + "Change password");
            System.out.println(Print.boldString("6. ") + "Delete account");
            System.out.println(Print.boldString("7. ") + "Logout");

            int choice;

            System.out.print(Print.boldString("Enter your choice: "));

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
                default -> System.out.println(Print.redString("Invalid choice"));
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
