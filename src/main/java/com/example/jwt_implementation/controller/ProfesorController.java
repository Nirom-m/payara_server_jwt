package com.example.jwt_implementation.controller;

import com.example.jwt_implementation.dao.ProfesorDao;
import com.example.jwt_implementation.model.Profesor;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.net.URI;
import java.util.List;

@Path("/profesor")
@Produces("application/json")
@Consumes("application/json")
@Transactional
public class ProfesorController {

    ProfesorDao profesorDao;

    @Inject
    public ProfesorController(ProfesorDao profesorDao) {
        this.profesorDao = profesorDao;

    }

    @GET
    @RolesAllowed({"ROLE_PROFESOR","ROLE_ADMIN"})
    public List<Profesor> listAllProfesors(){
        return this.profesorDao.findAll();
    }
    @GET
    @Path("{id}")
    @RolesAllowed({"ROLE_PROFESOR, ROLE_ADMIN"})
    public Response getProfesorById(@PathParam("id") Long id) {
        return Response.ok().entity(this.profesorDao.findById(Profesor.class, id).get()).build();
    }

    @POST
    @RolesAllowed({"ROLE_PROFESOR, ROLE_ADMIN"})
    public Response saveProfesor(Profesor p){
        profesorDao.saveProfesor(p);
        return Response.created(URI.create("/profesor/"+p.getId())).build();
    }

}
