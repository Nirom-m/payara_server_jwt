package com.example.jwt_implementation.dao;

import com.example.jwt_implementation.dao.generic.GenericDao;
import com.example.jwt_implementation.model.Profesor;
import com.example.jwt_implementation.model.Rol;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequestScoped
public class ProfesorDao extends GenericDao<Profesor> {
    @Inject
    EntityManager em;

    @Inject
    RolDao rolDao;

    public ProfesorDao() {
        super(Profesor.class);
    }

    @Transactional
    public Profesor saveProfesor(Profesor p) {
        Rol rolProfesor = rolDao.findRoleByNameInContext("ROLE_PROFESOR");
        System.out.println("rolProfesor: " + rolProfesor.getNombre());
        List<Rol> roles = new ArrayList<>();
        roles.add(rolProfesor);
        p.setRoles(roles);
        em.persist(p);

        return p;
    }

    @Transactional
    public Profesor findProfesorByUsernameAndPassword(String username, String password) {
        return em.createQuery("select p from Profesor p where p.username = :username and p.password = :password", Profesor.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();

    }

    @Transactional
    public List<Rol> listAllRolesProfesor(String username, String password) {
        Profesor p = findProfesorByUsernameAndPassword(username, password);
        return p.getRoles();
    }
}
