package hu.bme.crysys.homework.pangolin.webshop.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "pangolin.token")
public class TokenConfiguration {
    private Integer expireLength;
}
