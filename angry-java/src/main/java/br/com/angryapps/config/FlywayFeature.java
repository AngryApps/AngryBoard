package br.com.angryapps.config;

import br.com.angryapps.db.AppDatasource;
import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlywayFeature implements Feature {

    private static final Logger logger = LoggerFactory.getLogger(FlywayFeature.class.getName());

    @Override
    public boolean configure(FeatureContext context) {
        logger.info("Initializing Flyway migration");

        Flyway flyway = Flyway.configure()
                              .dataSource(AppDatasource.getDataSource())
                              .locations("classpath:db/migrations")
                              .baselineOnMigrate(true)
                              .load();

        // Start the migration
        flyway.migrate();

        logger.info("Flyway migration completed");

        return true;
    }
}