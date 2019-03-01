package com.alibaba.csp.sentinel.dashboard.config.db;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Config DataSource
 *
 * @author Monan
 * created on 2018/11/7 16:48
 */

@Configuration
/*@PropertySource("${properties.location}:config/jdbc.properties")*/
public class DataSourceConfig {

    private Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    /**
     * Create HikariCP DataSource Bean
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource build = DataSourceBuilder.create().type(HikariDataSource.class).build();
        return build;
    }

}
