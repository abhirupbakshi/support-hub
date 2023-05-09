package org.example.ui;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.Phone;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.exception.NotFoundException;
import org.example.service.UserService;
import org.example.service.exception.AuthenticationException;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.UserServiceImpl;
import org.example.ui.utilities.Print;
import org.example.ui.utilities.Prompt;

import java.util.ArrayList;
import java.util.List;
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
        } catch (NotFoundException | AuthenticationException exception) {
            System.out.println("Invalid email or password");
        } catch (AuthorizationException exception) {
            System.out.println("You are not authorized to login");
        } catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        return null;
    }

    public static void createAccount(Scanner scanner) {

        Prompt prompt = new Prompt(scanner);

        try {
            new UserServiceImpl().createAccount(
                    prompt.string("Enter Email: ", true,
                            "Email cannot be empty"),

                    prompt.string("Enter Forename: ", true,
                            "Forename cannot be empty"),

                    prompt.string("Enter Surname: ", true,
                            "Surname cannot be empty"),

                    new Prompt(scanner).promptAddress(),

                    new Prompt(scanner).promptPhone(),

                    prompt.password("Enter Password: ",
                            "Password cannot be empty")
            );
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to create an account");
        }
        catch (AlreadyExistException exception) {
            System.out.println("User already exists");
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }

        System.out.println("Account created successfully");
    }

    private boolean deleteAccount() {

        try {
            userService.deleteAccount(email,
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

    private void registerComplaint() {

        try {
            userService.registerComplaint(email, password,
                    prompt.string("Enter your complaint description: ",
                            true, "Invalid description"),

                    prompt.string("Enter your complaint category (e.g \"Hardware\" / \"Software\"): ",
                            true, "Invalid category")
            );

            System.out.println("Complaint registered successfully");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to register a complaint");
        }
        catch (NotFoundException exception) {
            System.out.println("Category not was found");
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void changePassword() {

        try {
            userService.updatePassword(email,
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

    private void viewAllCreatedComplaints() {

        try {
            Print.complaints(userService.getAllCreatedComplaints(email, password));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view all created complaints");
        }
        catch (NotFoundException ignored) { }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void viewCreatedComplaint() {

        try {
            viewAllCreatedComplaints();
            Print.complaint(userService.getCreatedComplaint(email, password,
                    prompt.integer("Enter complaint id: ", "Invalid id")));
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to view created complaint");
        }
        catch (NotFoundException ignored) {
            System.out.println("Complaint not was found");
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    private void changeComplaintTOResolved() {

        try {
            viewAllCreatedComplaints();
            userService.changeComplaintStatusToResolved(email, password,
                    prompt.integer("Enter complaint id: ", "Invalid id"));

            System.out.println("Complaint status changed successfully");
        }
        catch (AuthenticationException exception) {
            System.out.println("Invalid email or password");
        }
        catch (AuthorizationException exception) {
            System.out.println("You are not authorized to change complaint status");
        }
        catch (NotFoundException ignored) {
            System.out.println("Complaint not was found");
        }
        catch (Exception exception) {
            System.out.println("Something went wrong");
        }
    }

    public void prompt() {

        System.out.println("Successfully logged in");

        while (true) {

            System.out.println("""
                    1. Register a complaint
                    2. View all created complaints
                    3. View an individual complaint
                    4. Change a complaint status to "Resolved"
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
                case 1 -> registerComplaint();
                case 2 -> viewAllCreatedComplaints();
                case 3 -> viewCreatedComplaint();
                case 4 -> changeComplaintTOResolved();
                case 5 -> changePassword();
                case 6 -> {
                    if(deleteAccount()) return;
                }
                case 7 -> { return; }
                default -> System.out.println("Invalid choice");
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
