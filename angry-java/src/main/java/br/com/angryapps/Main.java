package br.com.angryapps;

import br.com.angryapps.api.JettyBootstrap;
import br.com.angryapps.configs.PropertiesLoader;
import br.com.angryapps.configs.di.MainInjector;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        PropertiesLoader.loadConfiguration();

        executeMigrations();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down application...");
        }));

        JettyBootstrap.start();
    }

    private static void executeMigrations() {
        HikariDataSource hikariDataSource = MainInjector.getService(HikariDataSource.class);
        Flyway flyway = Flyway.configure()
                              .dataSource(hikariDataSource)
                              .locations("classpath:db/migrations")
                              .baselineOnMigrate(true)
                              .load();

        flyway.migrate();
    }
}