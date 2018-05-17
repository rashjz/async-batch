package com.example.demo.configuration.properties;

import lombok.Data;
 
@Data
public class ApplicationProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;

}
