package com.example.jwt_implementation.config;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.ejb.Startup;
import jakarta.inject.Singleton;

@DataSourceDefinition(

        name="java:app/mundoPc/db",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        url = "${ENV=url}",//
        user = "${ENV=user}", //
        password = "${ENV=password}", //
        initialPoolSize = 2,
        minPoolSize = 2,
        maxPoolSize = 10,
        properties = {
                "useSSL=false",
                "useInformationSchema=true",
                "nullCatalogMeansCurrent=true",
                "nullNamePatternMatchesAll=false"
        }
)
@Singleton
@Startup
public class Persistencia {
}
