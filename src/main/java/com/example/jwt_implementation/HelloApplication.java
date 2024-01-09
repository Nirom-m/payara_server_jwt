package com.example.jwt_implementation;

import jakarta.annotation.security.DeclareRoles;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.auth.LoginConfig;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

@LoginConfig(
        authMethod = "MP-JWT")
@DeclareRoles({"ROLE_PROFESOR", "ROLE_ADMIN"})
@SecurityScheme(securitySchemeName = "JWT",type = SecuritySchemeType.HTTP,scheme = "bearer",bearerFormat = "JWT")
@ApplicationPath("/api")
public class HelloApplication extends Application {

}