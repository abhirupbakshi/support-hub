package org.example.ui;

import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.service.UserService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.UserServiceImpl;
import org.example.ui.utilities.Print;
import org.example.ui.utilities.Prompt;
import java.util.Scanner;

public class UserUI {

    private final String password;
    private final String email;
    private final Scanner scanner;
    private final UserService userService = new UserServiceImpl();
    private final Prompt prompt;

    public static UserUI isCredentialsCorrect(Scanner scanner, String email, String password) {

        UserService userService = new UserServiceImpl();

        try {
            userService.getAccountDetails(email, password);
            return new UserUI(password, email, scanner);
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

    public static void createAccount(Scanner scanner) {

        Prompt prompt = new Prompt(scanner);

        try {
            new UserServiceImpl().createAccount(
                    prompt.string(Print.boldString("Enter Email: "), true,
                            Print.redString("Email cannot be empty")),

                    prompt.string(Print.boldString("Enter Forename: "), true,
                            Print.redString("Forename cannot be empty")),

                    prompt.string(Print.boldString("Enter Surname: "), true,
                            Print.redString("Surname cannot be empty")),

                    new Prompt(scanner).promptAddress(),

                    new Prompt(scanner).promptPhone(),

                    prompt.password(Print.boldString("Enter Password: "),
                            Print.redString("Password cannot be empty"))
            );
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to create an account"));
        }
        catch (AlreadyExistException exception) {
            System.out.println(Print.redString("User already exists"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }

        System.out.println(Print.greenString("Account created successfully"));
    }

    private boolean deleteAccount() {

        try {
            userService.deleteAccount(email,
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

    private void registerComplaint() {

        try {
            userService.registerComplaint(email, password,
                    prompt.string(Print.boldString("Enter your complaint description: "),
                            true, Print.redString("Invalid description")),

                    prompt.string(Print.boldString("Enter your complaint category (e.g \"Hardware\" / \"Software\"): "),
                            true, Print.redString("Invalid category"))
            );

            System.out.println(Print.greenString("Complaint registered successfully"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to register a complaint"));
        }
        catch (NotFoundException exception) {
            System.out.println(Print.redString("Category not was found"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void changePassword() {

        try {
            userService.updatePassword(email,
                    prompt.password(Print.boldString("Enter your current password: "),
                            Print.redString("Invalid password")),
                    prompt.password(Print.boldString("Enter your new password: "),
                            Print.redString("Invalid password"))
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

    private void viewAllCreatedComplaints() {

        try {
            Print.complaints(userService.getAllCreatedComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view all created complaints"));
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void viewCreatedComplaint() {

        try {
            viewAllCreatedComplaints();
            Print.complaint(userService.getCreatedComplaint(email, password,
                    prompt.integer(Print.boldString("Enter complaint id: "), Print.redString("Invalid id"))));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to view created complaint"));
        }
        catch (NotFoundException ignored) {
            System.out.println(Print.redString("Complaint was not found"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    private void changeComplaintTOResolved() {

        try {
            viewAllCreatedComplaints();
            userService.changeComplaintStatusToResolved(email, password,
                    prompt.integer(Print.boldString("Enter complaint id: "), Print.redString("Invalid id")));

            System.out.println(Print.greenString("Complaint status changed successfully"));
        }
        catch (AuthenticationException exception) {
            System.out.println(Print.redString("Invalid email or password"));
        }
        catch (AuthorizationException exception) {
            System.out.println(Print.redString("You are not authorized to change complaint status"));
        }
        catch (NotFoundException ignored) {
            System.out.println(Print.redString("Complaint not was found"));
        }
        catch (Exception exception) {
            System.out.println(Print.redString("Something went wrong"));
        }
    }

    public void prompt() {

        System.out.println(Print.greenString("Successfully logged in"));

        while (true) {

            System.out.println(Print.boldString("1. ") + "Register a complaint");
            System.out.println(Print.boldString("2. ") + "View all created complaints");
            System.out.println(Print.boldString("3. ") + "View an individual complaint");
            System.out.println(Print.boldString("4. ") + "Change a complaint status to Resolved");
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
                case 1 -> registerComplaint();
                case 2 -> viewAllCreatedComplaints();
                case 3 -> viewCreatedComplaint();
                case 4 -> changeComplaintTOResolved();
                case 5 -> changePassword();
                case 6 -> {
                    if(deleteAccount()) return;
                }
                case 7 -> { return; }
                default -> System.out.println(Print.redString("Invalid choice"));
            }
        }
    }

    public UserUI(String password, String email, Scanner scanner) {
        this.password = password;
        this.email = email;
        this.scanner = scanner;
        this.prompt = new Prompt(scanner);
    }
}
