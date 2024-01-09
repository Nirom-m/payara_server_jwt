package com.example.jwt_implementation.dao;

import com.example.jwt_implementation.model.Rol;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

@RequestScoped
public class RolDao {
    @Inject
    EntityManager em;
    public Rol findRoleByNameInContext(String roleName) {
        return em.createQuery("SELECT r FROM Rol r WHERE r.nombre = :roleName", Rol.class)
                .setParameter("roleName", roleName)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }
}
