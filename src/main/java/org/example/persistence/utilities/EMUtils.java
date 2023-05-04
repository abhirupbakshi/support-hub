package org.example.persistence.utilities;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * This is a utility class that is to make sure that  {@link EntityManagerFactory} is created only once in the app.
 */
public class EMUtils {
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("testPU");

    public static EntityManager getEM() {
        return emf.createEntityManager();
    }
}
