package com.github.gmessiasc.devfood.register;
import java.util.HashMap;
import java.util.Map;

import org.testcontainers.containers.MySQLContainer;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

public class RegisterTestLifecycleManager implements QuarkusTestResourceLifecycleManager  {

    public static final MySQLContainer<?> MYSQL = new MySQLContainer<>("mysql:8.0.32");
     
    @Override
    public Map<String, String> start() {
        MYSQL.start();
        Map<String, String> properties = new HashMap<String, String>();

        properties.put("quarkus.datasource.jdbc.url", MYSQL.getJdbcUrl());
        properties.put("quarkus.datasource.username", MYSQL.getUsername());
        properties.put("quarkus.datasource.password", MYSQL.getPassword());

        return properties;
    }

    @Override
    public void stop() {
        if (MYSQL != null && MYSQL.isRunning()){
            MYSQL.stop();
        }
    }
    
}

