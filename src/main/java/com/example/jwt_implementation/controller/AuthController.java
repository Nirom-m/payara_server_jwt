package com.example.jwt_implementation.controller;

import com.example.jwt_implementation.dto.AuthenticationRequest;
import com.example.jwt_implementation.model.Rol;
import com.example.jwt_implementation.security.JwtIssuer;
import com.example.jwt_implementation.service.UserServiceImpl;
import io.jsonwebtoken.JwtBuilder;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/auth")
@RequestScoped
public class AuthController {
    UserServiceImpl userService;

    @Inject
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    public AuthController() {
        this(null);
    }

    public static final long JWT_TOKEN_VALIDITY = 1000 * 60 * 60 * (long) 8; // 8 Horas

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authenticationRequest) {
        final String username = authenticationRequest.username();
        final String password = authenticationRequest.password();

        if (!userService.authUser(username, password)) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // Si las credenciales son válidas, genera un token JWT
        try {
            final List<Rol> roles = userService.listAllUserRoles(username, password);
            //Mandar username
            //Tiempo de vida del token
            String token = JwtIssuer.generateJWTString(username, JWT_TOKEN_VALIDITY, roles);
            return Response.ok(buildTokenResponse(token)).build();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @GET
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserInfo(@HeaderParam("Authorization") String token) {
        // Aquí podrías validar y decodificar el token JWT para obtener información del usuario
        // Retorna información del usuario (por ejemplo, nombre de usuario) en formato JSON
        return Response.ok(buildUserInfoResponse("exampleUser")).build();
    }

    private JsonObject buildTokenResponse(String token) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("token", token);
        return builder.build();
    }

    private JsonObject buildUserInfoResponse(String username) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("username", username);
        return builder.build();
    }
}
