package com.example.jwt_implementation.security;

import com.example.jwt_implementation.model.Rol;
import com.example.jwt_implementation.model.Usuario;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.SignedJWT;
import io.jsonwebtoken.Jwts;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.eclipse.microprofile.jwt.Claims;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.List;

import static com.nimbusds.jose.JOSEObjectType.JWT;
import static com.nimbusds.jose.JWSAlgorithm.RS256;
import static com.nimbusds.jwt.JWTClaimsSet.parse;
import static java.lang.Thread.currentThread;

public class JwtIssuer {

    public static String generateJWTString(String username, long expirationTime, List<Rol> roles) throws Exception {

        long currentTimeInSecs = (System.currentTimeMillis() / 1000);

         return Jwts.builder().header()
                 .type("JWT")
                 .and()
                 .claim(Claims.iat.name(), currentTimeInSecs)
                 .claim(Claims.auth_time.name(), currentTimeInSecs)
                 .claim(Claims.exp.name(), expirationTime)
                 .claim("username", username)
                 .claim(Claims.groups.name(), obtenerNombresRoles(roles))
                 .claim(Claims.iss.name(), "nikor")
                 .signWith(readPrivateKey("META-INF/private-key.pem"))
                 .compact();
        //Meter la llave con el byteCode de la llave

        //Ingresar los datos del usuario

//        signedJWT.sign(new RSASSASigner(readPrivateKey("META-INF/private-key.pem")));
    }

    public static PrivateKey readPrivateKey(String resourceName) throws Exception {

        byte[] byteBuffer = new byte[16384];
        int length = currentThread().getContextClassLoader()
                .getResource(resourceName)
                .openStream()
                .read(byteBuffer);

        String key = new String(byteBuffer, 0, length).replaceAll("-----BEGIN (.*)-----", "")
                .replaceAll("-----END (.*)----", "")
                .replaceAll("\r\n", "")
                .replaceAll("\n", "")
                .trim();

        return KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(key)));
    }
    private static List<String> obtenerNombresRoles(List<Rol> roles) {
        // Implementa la lógica para obtener los nombres de los roles de tu lista de objetos Rol
        // Devuelve una lista de nombres de roles
        // Por ejemplo, puedes usar un stream y map para obtener los nombres de los roles
        return roles.stream().map(Rol::getNombre).toList();
    }
}
