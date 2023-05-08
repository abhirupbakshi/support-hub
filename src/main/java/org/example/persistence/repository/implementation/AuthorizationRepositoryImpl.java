package org.example.persistence.repository.implementation;

import jakarta.persistence.EntityManager;
import org.example.persistence.entity.Authorization;
import org.example.persistence.entity.AuthorizationKey;
import org.example.persistence.repository.AuthorizationRepository;
import org.example.persistence.utilities.EMUtils;

/**
 * <h3>Class AuthorizationRepositoryImpl</h3>
 * This is an implementation class for {@link AuthorizationRepository}.
 */
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

    @Override
    public Boolean getPermission(String employeeType, String permissionName) {

        if(employeeType == null)
                throw new IllegalArgumentException("employeeType parameter cannot be null");
        if(permissionName == null)
                throw new IllegalArgumentException("permissionName parameter cannot be null");

        try (EntityManager em = EMUtils.getEM()) {

            Authorization authorization = em.find(Authorization.class, AuthorizationKey.builder()
                    .employeeType(employeeType)
                    .permissionName(permissionName)
                    .build());

            if (authorization == null)
                return null;

            return authorization.isValue();
        }
    }
}
