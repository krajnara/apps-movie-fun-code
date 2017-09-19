package org.superbiz.moviefun;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/**
 * Created by 103209 on 19/09/17.
 */
@Profile("development")
@Configuration
public class DevelopmentSecurityConfig {
    @Bean
    public RestOperations restOperations() {
        return new RestTemplate();
    }
}
