package br.com.lamoda.migrations.config;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfig {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource-mssql")
    public DataSource mssqlDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource-oracle")
    public DataSource oracleDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.mssql.liquibase")
    public LiquibaseProperties mssqlLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean("mssqlLiquibase")
    public SpringLiquibase mssqlLiquibase() {
        return springLiquibase(mssqlDatasource(), mssqlLiquibaseProperties());
    }

    @Bean
    @ConfigurationProperties(prefix = "datasource.oracle.liquibase")
    public LiquibaseProperties oracleLiquibaseProperties() {
        return new LiquibaseProperties();
    }

    @Bean("oracleLiquibase")
    public SpringLiquibase oracleLiquibase() {
        return springLiquibase(oracleDatasource(), oracleLiquibaseProperties());
    }

    private static SpringLiquibase springLiquibase(DataSource dataSource, LiquibaseProperties properties) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(properties.getChangeLog());
        liquibase.setContexts(properties.getContexts());
        liquibase.setDefaultSchema(properties.getDefaultSchema());
        liquibase.setDropFirst(properties.isDropFirst());
        liquibase.setShouldRun(properties.isEnabled());
        liquibase.setLabels(properties.getLabels());
        liquibase.setChangeLogParameters(properties.getParameters());
        liquibase.setRollbackFile(properties.getRollbackFile());
        return liquibase;
    }
}