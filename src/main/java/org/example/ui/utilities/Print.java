package org.example.ui.utilities;

import org.example.persistence.entity.Complaint;
import org.example.persistence.entity.Engineer;

import java.util.List;

public class Print {

    public static void complaint(Complaint complaint) {

        System.out.println(
                complaint.getId() + " " +
                        complaint.getCreatedOn() + " " +
                        complaint.getDescription() + " " +
                        complaint.getCreatedBy().getEmployeeEmail() + " " +
                        complaint.getCategory() + " " +
                        complaint.getStatus().getName());
    }

    public static void complaints(List<Complaint> complaints) {

        complaints.forEach(complaint -> {
            System.out.println(
                    complaint.getId() + " " +
                            complaint.getDescription() + " " +
                            complaint.getStatus().getName());
        });
    }

    public static void engineers(List<Engineer> engineers) {

        engineers.forEach(System.out::println);
    }

    public static void engineer(Engineer engineer) {

        System.out.println(engineer);
    }

    public static void engineerAndAssignedComplaints(Engineer engineer, List<Complaint> complaints) {

        System.out.print(engineer.getEmployeeEmail());
        complaints.forEach(complaint -> {
            System.out.println("\t" + complaint.getId() + " " + complaint.getDescription());
        });
    }
}
