package br.com.angryapps.configs.di;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.glassfish.hk2.api.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HikariFactory implements Factory<HikariDataSource> {

    private static final Logger logger = LoggerFactory.getLogger(HikariFactory.class);

    @Override
    public HikariDataSource provide() {
        try {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(System.getProperty("db.url"));
            config.setUsername(System.getProperty("db.username"));
            config.setPassword(System.getProperty("db.password"));
            config.setDriverClassName("org.postgresql.Driver");

            // Connection pool settings
            config.setMaximumPoolSize(Integer.getInteger("db.pool.maxSize", 10));
            config.setMinimumIdle(Integer.getInteger("db.pool.minIdle", 2));
            config.setIdleTimeout(Long.getLong("db.pool.idleTimeout", 30000));
            config.setConnectionTimeout(Long.getLong("db.pool.connectionTimeout", 30000));
            config.setMaxLifetime(Long.getLong("db.pool.maxLifetime", 1800000));

            // Connection test query
            config.setConnectionTestQuery("SELECT 1");

            config.setPoolName("AngryJavaConnectionPool");

            logger.info("HikariCP connection pool initialized successfully");

            return new HikariDataSource(config);
        } catch (Exception e) {
            logger.error("Failed to initialize HikariCP connection pool", e);
            throw new RuntimeException("Failed to initialize database connection pool", e);
        }
    }

    @Override
    public void dispose(HikariDataSource instance) {
        instance.close();
    }
}
