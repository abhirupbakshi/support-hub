//package org.example;
//
//import jakarta.persistence.EntityManager;
//import org.example.persistence.entity.*;
//import org.example.persistence.repository.*;
//import org.example.persistence.repository.implementation.*;
//import org.example.persistence.utilities.EMUtils;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//public class App {
//    public static void add(EntityManager em) {
//
//        User user = User.builder()
//                .email("a@a.com")
//                .forename("user")
//                .surname("user")
//                .addresses(new ArrayList<>(List.of(
//                        Address.builder()
//                                .addressType("home")
//                                .state("state1")
//                                .city("city1")
//                                .country("country1")
//                                .build(),
//
//                        Address.builder()
//                                .addressType("office")
//                                .state("state2")
//                                .city("city2")
//                                .country("country2")
//                                .build()
//                )))
//                .phones(new ArrayList<>(List.of(
//                        Phone.builder()
//                                .phoneNumberType("home")
//                                .number("123456789")
//                                .build()
//                )))
//                .build();
//
//        Engineer engineer = Engineer.builder()
//                .email("b@b.com")
//                .forename("engineer")
//                .surname("engineer")
//                .addresses(new ArrayList<>(List.of(
//                        Address.builder()
//                                .addressType("home")
//                                .state("state1")
//                                .city("city1")
//                                .country("country1")
//                                .build(),
//
//                        Address.builder()
//                                .addressType("office")
//                                .state("state2")
//                                .city("city2")
//                                .country("country2")
//                                .build()
//                )))
//                .phones(new ArrayList<>(List.of(
//                        Phone.builder()
//                                .phoneNumberType("home")
//                                .number("126696789")
//                                .build()
//                )))
//                .build();
//
//        HOD hod = HOD.builder()
//                .email("c@c.com")
//                .forename("hod")
//                .surname("hod")
//                .addresses(new ArrayList<>(List.of(
//                        Address.builder()
//                                .addressType("home")
//                                .state("state1")
//                                .city("city1")
//                                .country("country1")
//                                .build(),
//
//                        Address.builder()
//                                .addressType("office")
//                                .state("state2")
//                                .city("city2")
//                                .country("country2")
//                                .build()
//                )))
//                .phones(new ArrayList<>(List.of(
//                        Phone.builder()
//                                .phoneNumberType("home")
//                                .number("126616789")
//                                .build()
//                )))
//                .build();
//
//        Category software = Category.builder()
//                .name("Software")
//                .build();
//
//        Status open = Status.builder()
//                .name("Open")
//                .build();
//
//        Complaint complaint = Complaint.builder()
//                .createdBy(user)
//                .description("description")
//                .category(software)
//                .status(open)
//                .createdOn(LocalDateTime.now())
//                .build();
//
//        Account engineerAccount = Account.builder()
//                .employee(engineer)
//                .password(new byte[] {1, 2, 3})
//                .build();
//        Account userAccount = Account.builder()
//                .employee(user)
//                .password(new byte[] {1, 2, 6})
//                .build();
//        Account hodAccount = Account.builder()
//                .employee(hod)
//                .password(new byte[] {1, 80, 3})
//                .build();
//
//        user.setAccount(userAccount);
//        engineer.setAccount(engineerAccount);
//        hod.setAccount(hodAccount);
//
//        user.getCreatedComplaints().add(complaint);
//
//        Authorization userAuth = Authorization.builder().employeeType(User.class.getName()).level(1).build();
//
//        em.getTransaction().begin();
//        em.persist(software);
//        em.persist(open);
//        em.persist(userAuth);
//
//        em.persist(userAccount);
//        em.persist(engineerAccount);
//        em.persist(hodAccount);
//
//        complaint.getAssignedEngineers().add(engineer);
//
//        complaint.getUpdates().add(Update.builder()
//                .message("message")
//                .build());
//
//        em.getTransaction().commit();
//        em.close();
//    }
//
//    public static void main(String[] args) throws Exception {
//        EMUtils emUtils = new EMUtils();
//        add(EMUtils.getEM());
//
//        UserRepository ur = new UserRepositoryImpl();
//        EngineerRepository er = new EngineerRepositoryImpl();
//        HODRepository hr = new HODRepositoryImpl();
//        AccountRepository ar = new AccountRepositoryImpl();
//        ComplaintRepository cr = new ComplaintRepositoryImpl();
//
//        System.out.println(ur.get("a@a.com"));
//        er.addPhone("b@b.com", Phone.builder().number("123456789").phoneNumberType("unknown").build());
//        hr.addAddress("c@c.com", Address.builder().addressType("unknown").build());
//        hr.removeAddress("c@c.com", Address.builder().addressType("unknown").build());
//        System.out.println(ur.getCreatedComplains("a@a.com"));
//        ur.setSurname("a@a.com", "bob");
//
//        ar.createAccount(User.builder()
//                .email("new.com")
//                .forename("usernew")
//                .surname("usernew")
//                .addresses(new ArrayList<>(List.of(
//                        Address.builder()
//                                .addressType("home")
//                                .state("state1new")
//                                .city("city1new")
//                                .country("country1new")
//                                .build(),
//
//                        Address.builder()
//                                .addressType("office")
//                                .state("state2new")
//                                .city("city2new")
//                                .country("country2new")
//                                .build()
//                )))
//                .phones(new ArrayList<>(List.of(
//                        Phone.builder()
//                                .phoneNumberType("office")
//                                .number("3333333333")
//                                .build()
//                )))
//                .build(), new byte[] {1, 2, 3});
//
//        ar.setPassword("new.com", new byte[] {1, 2, 3}, new byte[] {1});
//        System.out.println(ar.get("new.com").getEmployee());
//        ar.deleteAccount("new.com");
//        System.out.println(ar.getAll());
//
//        System.out.println(cr.get(1).getAssignedEngineers());
//        System.out.println(cr.getAll());
//        cr.createComplain("desc1new", ur.get("a@a.com"), "Software", "Open");
//        cr.setDescription(1, "desc2new");
//        cr.setCategory(1, "software");
//        cr.setStatus(2, "Open");
//        cr.assignExistingEngineer("b@b.com", 2);
//        System.out.println(cr.getAssignedEngineers(1));
//        cr.addUpdate(1, "update1 for complaint2");
//        cr.addUpdate(1, "update1 for complaint2");
//        cr.addUpdate(1, "update1 for complaint2");
//        cr.addUpdate(1, "update1 for complaint2");
//        cr.removeAssignedEngineer("b@b.com", 1);
//    }
//}
