package org.example.ui.utilities;

import org.example.persistence.entity.*;
import java.time.LocalDateTime;
import java.util.List;

public class Print {

    private static String getTemporalString(LocalDateTime time) {

        return time.getDayOfMonth() + " " +
                time.getMonth() + " " +
                time.getYear() +
                " @ " +
                time.getHour() + ":" +
                time.getMinute();
    }

    private static String truncatedString(String str, int noOfChars) {

        return str.length() <= noOfChars ?
                str : str.substring(0, noOfChars - 3) + "...";
    }

    public static String redString(String str) {
        return "\033[31m" + str + "\033[39m";
    }

    public static String greenString(String str) {
        return "\033[32m" + str + "\033[39m";
    }

    public static String boldString(String str) {
        return "\033[1m" + str + "\033[22m";
    }

    public static void complaint(Complaint complaint) {

        System.out.print("\033[38;5;35m");

        System.out.println(
                "\033[1mID:\033[22m " + complaint.getId() + "\n" +
                        "\033[1mCreated on:\033[22m " + getTemporalString(complaint.getCreatedOn()) + "\n" +
                        "\033[1mDescription:\033[22m " + complaint.getDescription() + "\n" +
                        "\033[1mCreated by:\033[22m " + (complaint.getCreatedBy() == null ? "" :
                                                    complaint.getCreatedBy().getEmployeeEmail()) + "\n" +
                        "\033[1mCategory:\033[22m " + complaint.getCategory().getName() + "\n" +
                        "\033[1mStatus:\033[22m " + complaint.getStatus().getName()
        );

        if(!complaint.getUpdates().isEmpty()) {

            System.out.println("\033[1mUpdates:\033[22m ");
            System.out.printf("\033[1m\t%-25s\t%s\n\033[22m", "Creation Time", "Message");

            complaint.getUpdates().forEach(update -> {
                System.out.printf("\t%-25s\t%s\n", getTemporalString(complaint.getCreatedOn()), update.getMessage());
            });
        }
        else
            System.out.println("\033[1mUpdates:\033[22m None");

        System.out.print("\033[39m");
    }

    public static void complaints(List<Complaint> complaints) {

        System.out.print("\033[38;5;35m");

        System.out.printf("\033[1m%-5s\t%-33s\t%-10s\033[22m\n", "ID", "Description", "Status");

        complaints.forEach(complaint -> {

            System.out.printf(
                    "%-5s\t%-33s\t%-10s\n",
                    complaint.getId(),
                    truncatedString(complaint.getDescription(), 35),
                    complaint.getStatus().getName());
        });

        System.out.print("\033[39m");
    }

    public static void employees(List<? extends Employee> employees) {

        System.out.print("\033[38;5;35m");

        System.out.printf("\033[1m%-15s\t%-35s\t%s\n\033[22m", "Employee type", "Email", "Name");

        employees.forEach(employee -> {

            String type = "";

            if (employee instanceof HOD)
                type = HOD.class.getSimpleName();
            else if(employee instanceof Engineer)
                type = Engineer.class.getSimpleName();
            else if(employee instanceof User)
                type = User.class.getSimpleName();

            System.out.printf("%-15s\t%-35s\t%s\n",
                    type,
                    truncatedString(employee.getEmployeeEmail(), 35),
                    employee.getForename() + " " + employee.getSurname()
            );
        });

        System.out.print("\033[39m");
    }

    public static void employee(Employee employee) {

        System.out.print("\033[38;5;35m");

        System.out.println(
                "\033[1mEmail:\033[22m " + employee.getEmployeeEmail() + "\n" +
                        "\033[1mName:\033[22m " + employee.getForename() + " " + employee.getSurname()
        );

        System.out.println("\033[1mAddress:\033[22m ");
        System.out.printf("\033[1m\t%-15s\t%-15s\t%-15s\t%-15s\n\033[22m", "Type", "City", "State", "Country");
        employee.getAddresses().forEach(address -> {

            System.out.printf("\t%-15s\t%-15s\t%-15s\t%-15s\n",
                    truncatedString(address.getAddressType(), 15),
                    truncatedString(address.getCity(), 15),
                    truncatedString(address.getState(), 15),
                    truncatedString(address.getCountry(), 15)
            );
        });

        System.out.println("\033[1mPhones:\033[22m ");
        System.out.printf("\033[1m\t%-15s\t%-15s\n\033[22m", "Type", "Number");
        employee.getPhones().forEach(phone -> {

            System.out.printf("\t%-15s\t%-15s\n",
                    truncatedString(phone.getPhoneNumberType(), 15),
                    truncatedString(phone.getNumber(), 15)
            );
        });

        System.out.print("\033[39m");
    }

    public static void engineerComplaintAssociations(Engineer engineer, List<Complaint> complaints) {

        System.out.print("\033[38;5;35m");

        System.out.println("\033[1m" + engineer.getEmployeeEmail() + " " + engineer.getForename() + " " + engineer.getSurname() + "\033[22m");

        System.out.printf("\033[1m\t%-5s\t%-33s\t%-10s\033[22m\n", "ID", "Description", "Status");

        complaints.forEach(complaint -> {

            System.out.printf(
                    "\t%-5s\t%-33s\t%-10s\n",
                    complaint.getId(),
                    truncatedString(complaint.getDescription(), 35),
                    complaint.getStatus().getName());
        });

        System.out.print("\033[39m");
    }
}
