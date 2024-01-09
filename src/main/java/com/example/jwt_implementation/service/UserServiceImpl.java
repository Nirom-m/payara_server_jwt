package com.example.jwt_implementation.service;

import com.example.jwt_implementation.dao.ProfesorDao;
import com.example.jwt_implementation.dao.UsuarioDao;
import com.example.jwt_implementation.model.Profesor;
import com.example.jwt_implementation.model.Rol;
import com.example.jwt_implementation.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;

import java.util.List;

@ApplicationScoped
public class UserServiceImpl {
    //private final ProfesorDao profesorDao;
    private final UsuarioDao usuarioDao;

    @Inject
    public UserServiceImpl(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public UserServiceImpl() {
        this(null);
    }

    public boolean authUser(String username, String password){
        Usuario usuario= usuarioDao.findUsuarioByUsernameAndPassword(username, password);
        return usuario != null;
    }

    //Quitar password
    public Usuario loadProfesor(String username, String password) {
        return usuarioDao.findUsuarioByUsernameAndPassword(username, password);
    }

    public List<Rol> listAllUserRoles(String username, String password) {
        return usuarioDao.listAllUserRoles(username, password);
    }



}
