package com.epam.esm.config;

import javax.sql.DataSource;

public class DataSourceConfig {

    public static final DataSource dataSource = new DatabaseConfig().embeddedDataSource();
}
