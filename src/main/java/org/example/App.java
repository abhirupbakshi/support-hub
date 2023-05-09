package org.example;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.exception.AlreadyExistException;
import org.example.persistence.repository.*;
import org.example.persistence.repository.implementation.*;
import org.example.persistence.utilities.EMUtils;
import org.example.service.EngineerService;
import org.example.service.HODService;
import org.example.service.UserService;
import org.example.service.exception.AuthorizationException;
import org.example.service.implementation.EngineerServiceImpl;
import org.example.service.implementation.HODServiceImpl;
import org.example.service.implementation.UserServiceImpl;
import org.example.ui.MainUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void test() throws Exception {

        EntityManager em = EMUtils.getEM();
        em.getTransaction().begin();
        em.persist(Category.builder()
                .name("Software")
                .build()
        );
        em.persist(Category.builder()
                .name("Hardware")
                .build()
        );
        em.persist(Status.builder()
                .name("Open")
                .build()
        );
        em.persist(Status.builder()
                .name("In Progress")
                .build()
        );
        em.persist(Status.builder()
                .name("Resolved")
                .build()
        );
        em.getTransaction().commit();
        em.close();


        AccountRepository ar = new AccountRepositoryImpl();
        UserRepository ur = new UserRepositoryImpl();
        EngineerRepository er = new EngineerRepositoryImpl();
        HODRepository hr = new HODRepositoryImpl();

        ar.createAccount(User.builder()
                .employeeEmail("user1@example.com")
                .forename("u1f")
                .surname("u1s")
                .addresses(new ArrayList<>(
                        List.of(
                        Address.builder()
                                .addressType("home")
                                .state("state1")
                                .city("city1")
                                .country("country1")
                                .build(),

                        Address.builder()
                                .addressType("office")
                                .state("state2")
                                .city("city2")
                                .country("country2")
                                .build()
                    )
                ))
                .phones(new ArrayList<>(List.of(
                        Phone.builder()
                                .phoneNumberType("home")
                                .number("1111111111")
                                .build()
                )))
                .build(),
                new byte[] {1, 2, 3}
        );

        ar.createAccount(Engineer.builder()
                .employeeEmail("engineer1@example.com")
                .forename("e1f")
                .surname("e2s")
                .addresses(new ArrayList<>(List.of(
                        Address.builder()
                                .addressType("home")
                                .state("state3")
                                .city("city3")
                                .country("country3")
                                .build(),

                        Address.builder()
                                .addressType("office")
                                .state("state4")
                                .city("city4")
                                .country("country4")
                                .build()
                )))
                .phones(new ArrayList<>(List.of(
                        Phone.builder()
                                .phoneNumberType("home")
                                .number("2222222222")
                                .build(),
                        Phone.builder()
                                .phoneNumberType("office")
                                .number("3333333333")
                                .build()
                )))
                .build(),
                new byte[] {1, 2, 4}
        );

        ar.createAccount(HOD.builder()
                .employeeEmail("hod1@example.com")
                .forename("h1f")
                .surname("h2s")
                .addresses(new ArrayList<>(List.of(
                        Address.builder()
                                .addressType("home")
                                .state("state5")
                                .city("city5")
                                .country("country5")
                                .build(),

                        Address.builder()
                                .addressType("office")
                                .state("state6")
                                .city("city6")
                                .country("country6")
                                .build()
                )))
                .phones(new ArrayList<>(List.of(
                        Phone.builder()
                                .phoneNumberType("office")
                                .number("4444444444")
                                .build()
                )))
                .build(),
                new byte[] {1, 2, 5}
        );

        ar.createAccount(User.builder()
                        .employeeEmail("user2@example.com")
                        .forename("u2f")
                        .surname("u2s")
                        .addresses(new ArrayList<>(List.of(
                                Address.builder()
                                        .addressType("home")
                                        .state("state7")
                                        .city("city7")
                                        .country("country7")
                                        .build(),

                                Address.builder()
                                        .addressType("office")
                                        .state("state8")
                                        .city("city8")
                                        .country("country8")
                                        .build()
                        )))
                        .phones(new ArrayList<>(List.of(
                                Phone.builder()
                                        .phoneNumberType("home")
                                        .number("4444444444")
                                        .build()
                        )))
                        .build(),
                new byte[] {1, 2, 6}
        );

        ar.createAccount(Engineer.builder()
                        .employeeEmail("engineer2@example.com")
                        .forename("e2f")
                        .surname("e2s")
                        .addresses(new ArrayList<>(List.of(
                                Address.builder()
                                        .addressType("home")
                                        .state("state9")
                                        .city("city9")
                                        .country("country9")
                                        .build(),

                                Address.builder()
                                        .addressType("office")
                                        .state("state10")
                                        .city("city10")
                                        .country("country10")
                                        .build()
                        )))
                        .phones(new ArrayList<>(List.of(
                                Phone.builder()
                                        .phoneNumberType("home")
                                        .number("5555555555")
                                        .build()
                        )))
                        .phone(Phone.builder()
                                .phoneNumberType("home")
                                .number("5555555555")
                                .build())
                        .build(),
                new byte[] {1, 2, 7}
        );

        ComplaintRepository cr = new ComplaintRepositoryImpl();

        cr.createComplain(
                "complaint1",
                "user1@example.com",
                "Hardware",
                "Open");

        cr.createComplain(
                "complaint2",
                "user2@example.com",
                "Software",
                "Open");

        cr.createComplain(
                "complaint3",
                "user1@example.com",
                "Software",
                "Open");

        er.removeAddress("engineer2@example.com",
                Address.builder()
                .addressType("home")
                .state("state9")
                .city("city9")
                .country("country9")
                .build());

        er.updateAddress("engineer2@example.com",
                Address.builder()
                .addressType("office")
                .state("state10")
                .city("city10")
                .country("country10")
                .build(),

                Address.builder()
                        .addressType("office")
                        .state("state10")
                        .city("city10")
                        .country("country101")
                        .build()
                );

        cr.assignExistingEngineer("engineer1@example.com", 1);
        cr.assignExistingEngineer("engineer2@example.com", 2);
        cr.assignExistingEngineer("engineer1@example.com", 3);
        cr.removeAssignedEngineer("engineer1@example.com", 3);
        System.out.println(er.getAssignedComplains("engineer1@example.com"));
        System.out.println(ur.getCreatedComplains("user1@example.com"));

        System.out.println(cr.get(1).getAssignedEngineers());
        System.out.println(cr.getAll());
        System.out.println(cr.getAssignedEngineers(1));
        cr.setCategory(1, "Software");
        cr.setStatus(1, "In Progress");
        cr.setDescription(2, "complaint2 new description");
        cr.removeAssignedEngineer("engineer1@example.com", 1);
        cr.assignExistingEngineer("engineer1@example.com", 1);
        cr.addUpdate(1, "complaint1 new description");
        cr.addUpdate(1, "complaint1 new description2");

        System.out.println(ar.get("engineer1@example.com"));
        System.out.println(ar.getAll());
        ar.setPassword("engineer1@example.com", new byte[] {1, 2, 4}, new byte[] {0});
        Account account = ar.get("engineer1@example.com");
        System.out.println(account.isPasswordEqual(new byte[] {0}));
        System.out.println("===================================");
        ar.deleteAccount("engineer1@example.com");
        System.out.println("===================================");
        ar.deleteAccount("user1@example.com");

        AuthorizationRepository authr = new AuthorizationRepositoryImpl();
        System.out.println(authr.getPermission("UserDTO", "user_account_create"));
        System.out.println(authr.getPermission("UserDTO", "user_acc"));
        System.out.println(authr.getPermission("Engineer", "hod_account_create"));
        System.out.println(authr.getPermission("Engineer", "hod_account_cre"));



        //***************************************************************************************************************


        EngineerService es = new EngineerServiceImpl();

        es.changeForename("engineer2@example.com", new byte[] {1, 2, 7}, "e1f");
        es.changeSurname("engineer2@example.com", new byte[] {1, 2, 7}, "esurnamef");
        es.addAddress("engineer2@example.com", new byte[] {1, 2, 7}, Address.builder()
                .addressType("home")
                .state("state11")
                .city("city11")
                .country("country11")
                .build());
        es.updateAddress("engineer2@example.com", new byte[] {1, 2, 7},
                Address.builder()
                .addressType("home")
                .state("state11")
                .city("city11")
                .country("country11")
                .build(),

                Address.builder()
                        .addressType("office")
                        .state("state11")
                        .city("city11")
                        .country("country11")
                        .build()
        );
        es.removeAddress("engineer2@example.com", new byte[] {1, 2, 7},
                Address.builder()
                        .addressType("office")
                        .state("state11")
                        .city("city11")
                        .country("country11")
                        .build()
        );


        es.addPhone("engineer2@example.com", new byte[] {1, 2, 7}, Phone.builder()
                .phoneNumberType("home")
                .number("6666666666")
                .build());
        es.updatePhone("engineer2@example.com", new byte[] {1, 2, 7},
                Phone.builder()
                .phoneNumberType("home")
                .number("6666666666")
                .build(),

                Phone.builder()
                        .phoneNumberType("office")
                        .number("6666666666")
                        .build()
        );
        es.removePhone("engineer2@example.com", new byte[] {1, 2, 7}, Phone.builder()
                .phoneNumberType("office")
                .number("6666666666")
                .build());

        es.createAccount("engineer3@example.com",
                "engineer3",
                "engineer3surname",
                new ArrayList<>(List.of(Address.builder()
                        .addressType("office")
                        .state("state11")
                        .city("city11")
                        .country("country11")
                        .build())),
                new ArrayList<>(List.of(Phone.builder()
                        .phoneNumberType("home")
                        .number("7777777777")
                        .build())),
                "1234");
        es.updatePassword("engineer3@example.com", "1234", "engineer3password");
        es.updatePassword("engineer3@example.com", "engineer3password", "1234");
        System.out.println(
                ((Engineer) es.getAccountDetails("engineer3@example.com", "1234")
                .getEmployee()).getAssignedComplaints()
        );
        System.out.println(
                ((Engineer) es.getAccountDetails("engineer3@example.com", "1234")
                        .getEmployee()).getPhones()
        );
        cr.assignExistingEngineer("engineer3@example.com", 1);
        System.out.println(es.getAssignedComplaint("engineer3@example.com", "1234", 1)
                .getStatus());
        System.out.println(es.getAllAssignedComplaints("engineer3@example.com", "1234"));
        es.changeComplaintStatus("engineer3@example.com", "1234", 1, "Resolved");
        es.addUpdateToComplaint("engineer3@example.com", "1234", 1, "update1: done something");

        UserService us = new UserServiceImpl();

        us.createAccount("user3@example.com",
                "user3",
                "user3surname",
                new ArrayList<>(List.of(Address.builder()
                        .addressType("office")
                        .state("state11")
                        .city("city11")
                        .country("country11")
                        .build())),
                new ArrayList<>(List.of(Phone.builder()
                        .phoneNumberType("home")
                        .number("7777777777")
                        .build())),
                "1234");
        us.registerComplaint("user3@example.com", "1234", "complaint desc1", "Software");
        System.out.println(us.getAllCreatedComplaints("user3@example.com", "1234"));
        System.out.println(us.getCreatedComplaint("user3@example.com", "1234", 4));
        us.changeComplaintStatusToResolved("user3@example.com", "1234", 4);

        es.deleteAccount("engineer3@example.com", "1234");

        HODService hs = new HODServiceImpl();

        hs.createAccount("hod2@example.com",
                "hod",
                "hod surname",
                new ArrayList<>(List.of(Address.builder()
                        .addressType("office")
                        .state("state11")
                        .city("city11")
                        .country("country11")
                        .build())),
                new ArrayList<>(List.of(Phone.builder()
                        .phoneNumberType("home")
                        .number("7777777777")
                        .build())),
                "1234");

        hs.getAllEngineers("hod2@example.com", "1234").forEach(System.out::println);
        hs.getAllComplaints("hod2@example.com", "1234").forEach(System.out::println);
        hs.assignExistingComplainToExistingEngineer("hod2@example.com", "1234",
                1, "engineer2@example.com");
        hs.removeExistingComplainFromExistingEngineer("hod2@example.com", "1234",
                1, "engineer2@example.com");
    }

    static void addTestAuth() {
        EntityManager em = EMUtils.getEM();
        em.getTransaction().begin();

        try {
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(Engineer.class.getSimpleName())
                            .permissionName("complaint_read_assigned")
                            .build()
                    )
                    .value(true)
                    .build()
            );
        }
        catch (Exception ignored) {}
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("complaint_update_status")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(Engineer.class.getSimpleName())
                        .permissionName("complaint_create_update")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_read_created")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_create_register")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(User.class.getSimpleName())
                        .permissionName("complaint_update_status-resolved")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("engineer_read_details")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("complaint_read_details")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("complaint_update_assign-engineer")
                        .build()
                )
                .value(true)
                .build()
        );
        em.persist(Authorization.builder()
                .key(AuthorizationKey.builder()
                        .employeeType(HOD.class.getSimpleName())
                        .permissionName("engineer_delete_assigned-complaint")
                        .build()
                )
                .value(true)
                .build()
        );

        em.getTransaction().commit();
        em.close();
    }

    static void addAuth() {
        EntityManager em = EMUtils.getEM();

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(Engineer.class.getSimpleName())
                            .permissionName("complaint_create_update")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(Engineer.class.getSimpleName())
                            .permissionName("complaint_update_status")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(Engineer.class.getSimpleName())
                            .permissionName("complaint_read_assigned")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("complaint_delete_assigned-engineer")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("complaint_read_details")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("engineer_read_details")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(User.class.getSimpleName())
                            .permissionName("complaint_create_register")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(User.class.getSimpleName())
                            .permissionName("complaint_read_registered")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(User.class.getSimpleName())
                            .permissionName("complaint_update_status-resolved")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("engineer_read_details")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("complaint_read_details")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("complaint_update_assign-engineer")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        try {
            em.getTransaction().begin();
            em.persist(Authorization.builder()
                    .key(AuthorizationKey.builder()
                            .employeeType(HOD.class.getSimpleName())
                            .permissionName("engineer_create_account")
                            .build()
                    )
                    .value(true)
                    .build()
            );
            em.getTransaction().commit();
        }
        catch (Exception ignored) {}

        em.close();
    }

    static void addStuff() {
        EntityManager em = EMUtils.getEM();

        try {
            em.getTransaction().begin();
            new HODServiceImpl().createAccount("hod", "HOD", "HOD",
                    List.of(Address.builder().addressType("Home").build()),
                    List.of(Phone.builder().phoneNumberType("home").number("888888888").build()),
                    "1234");
            em.getTransaction().commit();
        } catch (Exception e) {}



        em.close();
        em = EMUtils.getEM();
        em.getTransaction().begin();
        em.persist(Category.builder()
                .name("Software")
                .build()
        );
        em.persist(Category.builder()
                .name("Hardware")
                .build()
        );
        em.persist(Status.builder()
                .name("Open")
                .build()
        );
        em.persist(Status.builder()
                .name("In Progress")
                .build()
        );
        em.persist(Status.builder()
                .name("Resolved")
                .build()
        );
        em.getTransaction().commit();
        em.close();
    }

    public static void main(String[] args) throws Exception {
        EMUtils emUtils = new EMUtils();
        Scanner scanner = new Scanner(System.in);

//        addTestAuth();
//        test();

        try {
            addAuth();
        }
        catch (Exception ignored) {}
        try {
            addStuff();
        }
        catch (Exception ignored) {}


        new MainUI(scanner).prompt();
        scanner.close();
    }
}
