package com.epam.esm.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EnableJpaRepositories(basePackages = "com.epam.esm.repository")
@EntityScan("com.epam.esm.entity")
public class EntityManagerFactoryConfig  {

}
