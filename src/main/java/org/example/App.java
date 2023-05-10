package org.example;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.utilities.EMUtils;
import org.example.service.implementation.HODServiceImpl;
import org.example.ui.MainUI;
import java.util.List;
import java.util.Scanner;

public class App {

    static void add() throws Exception {
        EntityManager em = EMUtils.getEM();
        em.getTransaction().begin();

        // Adding HOD
        new HODServiceImpl().createAccount("hod@email.com", "-", "-",
                List.of(Address.builder().addressType("-").build()),
                List.of(Phone.builder().phoneNumberType("-").number("-").build()),
                "1");

        // Adding Category and Status
        em.persist(Category.builder().name("Software").build());
        em.persist(Category.builder().name("Hardware").build());
        em.persist(Status.builder().name("Open").build());
        em.persist(Status.builder().name("In Progress").build());
        em.persist(Status.builder().name("Resolved").build());

        // Adding HOD permissions
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("hod_create_account")
                        .build())
                .value(false).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("engineer_create_account")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("engineer_read_details")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("complaint_read_details")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("complaint_update_assign-engineer")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("engineer_delete_assigned-complaint")
                        .build())
                .value(true).build()
        );

        // Adding User permissions
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_create_register")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_read_registered")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_update_status-resolved")
                        .build())
                .value(true).build()
        );

        // Adding Engineer permissions
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("engineer_create_account")
                        .build())
                .value(false).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("complaint_read_assigned")
                        .build())
                .value(true).build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("complaint_update_status")
                        .build())
                .value(true)
                .build()
        );
        em.persist(Authorization.builder().key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("complaint_create_update")
                        .build())
                .value(true)
                .build()
        );

        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args) throws Exception {
        EMUtils emUtils = new EMUtils();
        add();

        Scanner scanner = new Scanner(System.in);
        new MainUI(scanner).prompt();
        scanner.close();
    }
}
