package com.alibaba.csp.sentinel.dashboard.config.db;

import com.alibaba.csp.sentinel.dashboard.util.wuga.Board;
import com.alibaba.csp.sentinel.dashboard.util.wuga.Table;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

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
    @ConfigurationProperties(prefix = "hikaricp")
    public DataSource dataSource() {
        HikariDataSource build = DataSourceBuilder.create().type(HikariDataSource.class).build();
//        printDataSourceConfig(build);
        return build;
    }

    /**
     * Print Out the Current HikariCP Configuration.
     *
     * @param build HikariDataSource
     */
    private void printDataSourceConfig(HikariDataSource build) {
        HikariConfigMXBean hikariConfigMXBean = build.getHikariConfigMXBean();

        StringBuilder infoTable = new StringBuilder();
        infoTable.append("\n" + "========== START =============== HikariCP-Config =============== START ==========");

        List<String> headersList = Arrays.asList("Name", "Value");
        List<List<String>> rowsList = Arrays.asList(
                Arrays.asList("PoolName", hikariConfigMXBean.getPoolName() + ""),
                Arrays.asList("ValidationTimeout", hikariConfigMXBean.getValidationTimeout() + ""),
                Arrays.asList("ConnectionTimeout", hikariConfigMXBean.getConnectionTimeout() + ""),
                Arrays.asList("IdleTimeout", hikariConfigMXBean.getIdleTimeout() + ""),
                Arrays.asList("", ""),
                Arrays.asList("LeakDetectionThreshold", hikariConfigMXBean.getLeakDetectionThreshold() + ""),
                Arrays.asList("MaximumPoolSize", hikariConfigMXBean.getMaximumPoolSize() + ""),
                Arrays.asList("MinimumIdle", hikariConfigMXBean.getMinimumIdle() + ""),
                Arrays.asList("MaxLifetime", hikariConfigMXBean.getMaxLifetime() + "")
        );
        Board board = new Board(75);
        infoTable.append("\n" + board.setInitialBlock(new Table(board, 75, headersList, rowsList).tableToBlocks()).build().getPreview());

        infoTable.append("=========== END ================ HikariCP-Config ================ END ===========");

        logger.info(infoTable.toString());
    }
}
