package com.alibaba.csp.sentinel.dashboard.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * mybatis引入配置类
 *
 * @quthor zrhong
 * @date 2017/11/30 16:26
 **/
@Configuration
@EnableTransactionManagement
@MapperScan(value = {"com.alibaba.csp.sentinel.dashboard.dao"})
public class MyBatisConfig implements TransactionManagementConfigurer {
    @Autowired
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlsession = new SqlSessionFactoryBean();
//        sqlsession.setConfigLocation(new ClassPathResource("config/mybatis-config.xml"));
        sqlsession.setDataSource(dataSource);
//        sqlsession.setTypeAliasesPackage("com.fengchao.model");//扫描entity包 使用别名
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setUseGeneratedKeys(true);//使用jdbc的getGeneratedKeys获取数据库自增主键值
        configuration.setUseColumnLabel(true);//使用列别名替换列名 select auth as User
        configuration.setMapUnderscoreToCamelCase(true);//-自动使用驼峰命名属性映射字段   userId    user_id
        sqlsession.setConfiguration(configuration);
        sqlsession.setFailFast(true);

        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            sqlsession.setMapperLocations(resolver.getResources("classpath*:mappers/**/*.xml"));
            return sqlsession.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
