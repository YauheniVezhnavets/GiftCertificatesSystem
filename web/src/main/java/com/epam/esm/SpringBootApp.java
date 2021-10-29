package com.epam.esm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.hateoas.config.EnableHypermediaSupport;

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@ComponentScan("com.epam.esm")
public class SpringBootApp {


    public static void main(String[] args) {
        SpringApplication.run(SpringBootApp.class,args);
    }
}
