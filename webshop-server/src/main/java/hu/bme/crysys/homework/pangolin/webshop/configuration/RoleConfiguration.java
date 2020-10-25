package hu.bme.crysys.homework.pangolin.webshop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "pangolin.roles")
public class RoleConfiguration {
    private String admin;
    private String user;
}
