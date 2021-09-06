package com.epam.esm.config;


import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;


import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

@Configuration
public class DatabaseConfig {

    private static final String DATABASE_PROPERTY_FILE_PATH = "/property/database.properties";
    private static final String DATABASE_DRIVER_CLASS_NAME = "spring.datasource.driver";
    private static final String DATABASE_URL = "spring.datasource.url";
    private static final String DATABASE_USERNAME = "spring.datasource.username";
    private static final String DATABASE_PASSWORD = "spring.datasource.password";
    private static final String DATABASE_POOL_INITIAL_SIZE = "spring.datasource.poolInitialSize";
    private static final String DATABASE_POOL_MAX_SIZE = "spring.datasource.poolMaxSize";
    private static final String CREATE_DATABASE_SCRIPT = "classpath:script/init_h2.sql";
    private static final String FILL_DATABASE_WITH_DATA_SCRIPT = "classpath:script/fill_tables.sql";


    @Profile("prod")
    @Bean
    public DataSource dataSource() throws IOException {
        Properties properties = new Properties();
        properties.load(getClass().getClassLoader().getResourceAsStream(DATABASE_PROPERTY_FILE_PATH));
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(properties.getProperty(DATABASE_DRIVER_CLASS_NAME));
        dataSource.setUrl(properties.getProperty(DATABASE_URL));
        dataSource.setUsername(properties.getProperty(DATABASE_USERNAME));
        dataSource.setPassword(properties.getProperty(DATABASE_PASSWORD));
        dataSource.setInitialSize(Integer.parseInt(properties.getProperty(DATABASE_POOL_INITIAL_SIZE)));
        dataSource.setMaxTotal(Integer.parseInt(properties.getProperty(DATABASE_POOL_MAX_SIZE)));


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
    public DataSource dataHikariSource() throws IOException {
        Properties properties = new Properties();
        properties.load(DatabaseConfig.class.getResourceAsStream(DATABASE_PROPERTY_FILE_PATH));
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(properties.getProperty(DATABASE_DRIVER_CLASS_NAME));
        dataSource.setJdbcUrl(properties.getProperty(DATABASE_URL));
        dataSource.setUsername(properties.getProperty(DATABASE_USERNAME));
        dataSource.setPassword(properties.getProperty(DATABASE_PASSWORD));
        dataSource.setMaximumPoolSize(Integer.parseInt(properties.getProperty(DATABASE_POOL_MAX_SIZE)));
        return dataSource;
    }
}
