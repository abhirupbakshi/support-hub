package org.example.ui.utilities;

import org.example.persistence.entity.Address;
import org.example.persistence.entity.Phone;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Prompt {

    private final Scanner scanner;

    public String string(String message, boolean required, String invalidMessage) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine();

            if (required && input.isEmpty())
                System.out.println(invalidMessage);
            else
                return input;
        }
    }

    public int integer(String message, String invalidMessage) {

        while (true) {

            System.out.print(message);

            try {
                return Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException exception) {
                System.out.println(invalidMessage);
            }
        }
    }

    public String password(String message, String invalidMessage) {

        while (true) {

            System.out.print(message);

            System.out.print("\033[8m");
            String input = scanner.nextLine();
            System.out.print("\033[28m");

            if (input.isEmpty())
                System.out.println(invalidMessage);
            else
                return input;
        }
    }

    public List<Address> promptAddress() {

        Prompt prompt = new Prompt(scanner);
        List<Address> addresses = new ArrayList<>();
        String choice;

        do {
            addresses.add(Address.builder()
                    .addressType(
                            prompt.string(Print.boldString("Enter address Type (e.g \"Home\" / \"Office\"): "),
                                    true, Print.redString("Address type cannot be empty")))

                    .city(prompt.string(Print.boldString("Enter city: "), false, ""))

                    .state(prompt.string(Print.boldString("Enter state: "), false, ""))

                    .country(prompt.string(Print.boldString("Enter country: "), false, ""))

                    .build()
            );

            while (true) {
                choice = prompt.string(Print.boldString("Add another address (y/n): "),
                        true, Print.redString("Invalid choice"));

                if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"))
                    System.out.println(Print.redString("Invalid choice"));
                else
                    break;
            }
        }
        while (choice.equalsIgnoreCase("y"));

        return addresses;
    }

    public List<Phone> promptPhone() {

        Prompt prompt = new Prompt(scanner);
        List<Phone> phones = new ArrayList<>();
        String choice;

        do {
            phones.add(Phone.builder()

                    .phoneNumberType(
                            prompt.string(Print.boldString("Enter Phone Type (e.g \"Home\" / \"Office\"): "),
                                    true, Print.redString("Phone Type cannot be empty")))

                    .number(
                            prompt.string(Print.boldString("Enter Phone Number: "),
                                    true, Print.redString("Phone Number cannot be empty"))
                    )

                    .build()
            );

            while (true) {
                choice = prompt.string(Print.boldString("Add another phone (y/n): "),
                        true, Print.redString("Invalid choice"));

                if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"))
                    System.out.println(Print.redString("Invalid choice"));
                else
                    break;
            }
        }
        while (choice.equalsIgnoreCase("y"));

        return phones;
    }

    public Prompt(Scanner scanner) {
        this.scanner = scanner;
    }
}
