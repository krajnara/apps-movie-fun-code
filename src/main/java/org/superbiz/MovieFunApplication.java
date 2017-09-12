package org.superbiz;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.superbiz.moviefun.ActionServlet;

import javax.swing.*;

/**
 * Created by 103209 on 12/09/17.
 */
@SpringBootApplication
public class MovieFunApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieFunApplication.class, args);
    }

    @Bean
    public ServletRegistrationBean mapServlet(ActionServlet servlet) {
        return new ServletRegistrationBean(servlet, "/moviefun");
    }
}