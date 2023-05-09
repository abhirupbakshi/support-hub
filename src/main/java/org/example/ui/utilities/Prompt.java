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

            String input = scanner.nextLine();

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
                            prompt.string("Enter Address Type (e.g \"Home\" / \"Office\"): ",
                                    true, "Address Type cannot be empty"))

                    .city(prompt.string("Enter City: ", false, ""))

                    .state(prompt.string("Enter State: ", false, ""))

                    .country(prompt.string("Enter Country: ", false, ""))

                    .build()
            );

            while (true) {
                choice = prompt.string("Add another address (y/n): ",
                        true, "Invalid choice");

                if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"))
                    System.out.println("Invalid choice");
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
                            prompt.string("Enter Phone Type (e.g \"Home\" / \"Office\"): ",
                                    true, "Phone Type cannot be empty"))

                    .number(
                            prompt.string("Enter Phone Number: ",
                                    true, "Phone Number cannot be empty")
                    )

                    .build()
            );

            while (true) {
                choice = prompt.string("Add another phone (y/n): ",
                        true, "Invalid choice");

                if(!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("n"))
                    System.out.println("Invalid choice");
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
