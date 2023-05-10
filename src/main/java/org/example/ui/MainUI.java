package org.example.ui;

import org.example.ui.utilities.Print;
import org.example.ui.utilities.Prompt;
import java.util.Scanner;

public class MainUI {

    private final Scanner scanner;

    public void prompt() {

        Prompt prompt = new Prompt(scanner);

        while (true) {

            System.out.println(Print.boldString("1. ") + "Login as Head of Department");
            System.out.println(Print.boldString("2. ") + "Login as Engineer");
            System.out.println(Print.boldString("3. ") + "Login as User");
            System.out.println(Print.boldString("4. ") + "Create User Account");
            System.out.println(Print.boldString("5. ") + "Exit");

            int choice;

            System.out.print(Print.boldString("Enter your choice: "));

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
                            prompt.string(Print.boldString("Enter your email: "), true, Print.redString("Invalid email")),
                            prompt.password(Print.boldString("Enter your password: "), Print.redString("Invalid password"))
                    );

                    if(hodUI != null)
                        hodUI.prompt();
                }
                case 2 -> {
                    EngineerUI engineerUI = EngineerUI.isCredentialsCorrect(
                            scanner,
                            prompt.string(Print.boldString("Enter your email: "), true, Print.redString("Invalid email")),
                            prompt.password(Print.boldString("Enter your password: "), Print.redString("Invalid password"))
                    );

                    if(engineerUI != null)
                        engineerUI.prompt();
                }
                case 3 -> {
                    UserUI userUI = UserUI.isCredentialsCorrect(
                            scanner,
                            prompt.string(Print.boldString("Enter your email: "), true, Print.redString("Invalid email")),
                            prompt.password(Print.boldString("Enter your password: "), Print.redString("Invalid password"))
                    );

                    if(userUI != null)
                        userUI.prompt();
                }
                case 4 -> UserUI.createAccount(scanner);
                case 5 -> { return; }
                default -> System.out.println(Print.redString("Invalid choice"));
            }
        }
    }

    public MainUI(Scanner scanner) {
        this.scanner = scanner;
    }
}
