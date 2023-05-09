package org.example.ui;

import org.example.persistence.utilities.EMUtils;
import org.example.ui.utilities.Prompt;

import java.util.Scanner;

public class MainUI {

    private final Scanner scanner;

    public void prompt() {

        Prompt prompt = new Prompt(scanner);

        while (true) {

            System.out.println("""
                    1. Login as Head of Department
                    2. Login as Engineer
                    3. Login as User
                    4. Create User Account
                    5. Exit""");

            int choice;

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException exception) {
                choice = -1;
            }

            switch (choice) {

                case 1 -> {
                    HODUI hodUI = HODUI.isCredentialsCorrect(
                            scanner,
                            prompt.string("Enter your email: ", true, "Invalid email"),
                            prompt.password("Enter your password: ", "Invalid password")
                    );

                    if(hodUI != null)
                        hodUI.prompt();
                }
                case 2 -> {
                    EngineerUI engineerUI = EngineerUI.isCredentialsCorrect(
                            scanner,
                            prompt.string("Enter your email: ", true, "Invalid email"),
                            prompt.password("Enter your password: ", "Invalid password")
                    );

                    if(engineerUI != null)
                        engineerUI.prompt();
                }
                case 3 -> {
                    UserUI userUI = UserUI.isCredentialsCorrect(
                            scanner,
                            prompt.string("Enter your email: ", true, "Invalid email"),
                            prompt.password("Enter your password: ", "Invalid password")
                    );

                    if(userUI != null)
                        userUI.prompt();
                }
                case 4 -> UserUI.createAccount(scanner);
                case 5 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    public MainUI(Scanner scanner) {
        this.scanner = scanner;
    }
}
