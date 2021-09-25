package com.epam.esm.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;


@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    private static final String DATABASE_DRIVER_CLASS_NAME = "spring.datasource.driver";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "spring.datasource.username";
    private static final String DATABASE_PASSWORD = "spring.datasource.password";
    private static final String DATABASE_POOL_INITIAL_SIZE = "spring.datasource.poolInitialSize";
    private static final String DATABASE_POOL_MAX_SIZE = "spring.datasource.poolMaxSize";
    private static final String CREATE_DATABASE_SCRIPT = "classpath:script/init_h2.sql";
    private static final String FILL_DATABASE_WITH_DATA_SCRIPT = "classpath:script/fill_tables_h2.sql";

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }

    @Profile("prod")
    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER_CLASS_NAME));
        dataSource.setUrl(environment.getProperty(DATABASE_URL));
        dataSource.setUsername(environment.getProperty(DATABASE_USERNAME));
        dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD));
        dataSource.setInitialSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_POOL_INITIAL_SIZE))));
        dataSource.setMaxTotal(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_POOL_MAX_SIZE))));

        return dataSource;
    }

    @Profile("dev")
    @Bean
    public DataSource embeddedDataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(CREATE_DATABASE_SCRIPT)
                .addScript(FILL_DATABASE_WITH_DATA_SCRIPT)
                .build();
    }

    @Bean
    @Profile("core")
    public DataSource dataHikariSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(environment.getProperty(DATABASE_DRIVER_CLASS_NAME));
        dataSource.setJdbcUrl(environment.getProperty(DATABASE_URL));
        dataSource.setUsername(environment.getProperty(DATABASE_USERNAME));
        dataSource.setPassword(environment.getProperty(DATABASE_PASSWORD));
        dataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty(DATABASE_POOL_MAX_SIZE))));
        return dataSource;
    }


    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
