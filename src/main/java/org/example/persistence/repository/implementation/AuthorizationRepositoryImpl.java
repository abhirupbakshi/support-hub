package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.Authorization;
import org.example.persistence.exception.NotFoundException;
import org.example.persistence.repository.AuthorizationRepository;
import org.example.persistence.utilities.EMUtils;
import java.util.List;

/**
 * <h3>Class AuthorizationRepositoryImpl</h3>
 * This is an implementation class for {@link AuthorizationRepository}.
 */
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @Override
    public int get(String employeeType) throws NotFoundException {

        if(employeeType == null) {
            throw new IllegalArgumentException("employeeType parameter cannot be null");
        }

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();
            Authorization authorization = em.find(Authorization.class, employeeType);
            em.getTransaction().commit();

            if(authorization == null) {
                throw new NotFoundException("Authorization not found for employee type: " + employeeType);
            }

            return authorization.getLevel();
        }
    }

    @Override
    public List<String> get(int level) {

        try (EntityManager em = EMUtils.getEM()) {

            em.getTransaction().begin();

            List<Authorization> authorizations = em.createQuery("""
                    SELECT a
                    FROM Authorization a
                    WHERE a.level = :level
                    """, Authorization.class)
                            .setParameter("level", level)
                            .getResultList();

            em.getTransaction().commit();

            return authorizations.stream().map(Authorization::getEmployeeType).toList();
        }
    }
}
