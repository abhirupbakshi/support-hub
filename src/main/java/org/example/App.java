package org.example;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.*;
import org.example.persistence.repository.*;
import org.example.persistence.repository.implementation.*;
import org.example.persistence.utilities.EMUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
        em.persist(Authorization.builder()
                .employeeType(User.class.getName())
                .level(1)
                .build());
        em.persist(Authorization.builder()
                .employeeType(Engineer.class.getName())
                .level(2)
                .build());
        em.persist(Authorization.builder()
                .employeeType(HOD.class.getName())
                .level(3)
                .build());
        em.getTransaction().commit();


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
        System.out.println(authr.get(2));
        System.out.println(authr.get(Engineer.class.getName()));
    }

    public static void main(String[] args) throws Exception {
        EMUtils emUtils = new EMUtils();
        test();
    }
}
