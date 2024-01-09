package com.example.jwt_implementation.dao;

import com.example.jwt_implementation.dao.generic.GenericDao;
import com.example.jwt_implementation.model.Profesor;
import com.example.jwt_implementation.model.Rol;
import com.example.jwt_implementation.model.Usuario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class UsuarioDao extends GenericDao<Usuario> {

    @Inject
    EntityManager em;

    public UsuarioDao() {
        super(Usuario.class);
    }

    @Transactional
    public Usuario findUsuarioByUsernameAndPassword(String username, String password) {
        return em.createQuery("select u from Usuario u where u.username = :username and u.password = :password", Usuario.class)
                .setParameter("username", username)
                .setParameter("password", password)
                .getSingleResult();

    }
    @Transactional
    public List<Rol> listAllUserRoles(String username, String password) {
        Usuario u = findUsuarioByUsernameAndPassword(username, password);
        return u.getRoles();
    }

    @Transactional
    public boolean isProfesor(String username, String password) {
        Usuario usuario = findUsuarioByUsernameAndPassword(username, password);
        return usuario instanceof Profesor;
    }
}
