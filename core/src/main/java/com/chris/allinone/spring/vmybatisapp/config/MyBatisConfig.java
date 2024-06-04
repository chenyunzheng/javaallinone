package com.chris.allinone.spring.vmybatisapp.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author chrischen
 */
@Configuration
public class MyBatisConfig {

//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws FileNotFoundException {
//        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
//        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("mybatis/mybatis-config.xml");
//        return builder.build(resourceAsStream);
//    }

    /* formal usage of mybatis **/
    @Bean
    public PlatformTransactionManager transactionManager() {
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/allinone?useSSL=true&useUnicode=true&characterEncoding=UTF-8");
        dataSource.setUsername("root");
        dataSource.setPassword("1qaz2wsx");
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        //sqlSessionFactoryBean.setMapperLocations();
        return sqlSessionFactoryBean.getObject();
    }

    //add @MapperScan or @Bean MapperScannerConfigurer
}
