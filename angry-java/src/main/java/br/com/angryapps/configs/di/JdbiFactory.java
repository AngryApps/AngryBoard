package br.com.angryapps.configs.di;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.inject.Inject;
import org.glassfish.hk2.api.Factory;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

public class JdbiFactory implements Factory<Jdbi> {
    private static final Logger logger = LoggerFactory.getLogger(JdbiFactory.class);

    private final HikariDataSource hikariDataSource;

    @Inject
    public JdbiFactory(HikariDataSource hikariDataSource) {
        this.hikariDataSource = hikariDataSource;
    }

    @Override
    public Jdbi provide() {
        return Jdbi.create(hikariDataSource)
                   .setSqlLogger(new SqlLogger() {
                       @Override
                       public void logAfterExecution(StatementContext context) {
                           logger.debug("Executed SQL: {} in {}ms",
                                        context.getRenderedSql(),
                                        context.getElapsedTime(ChronoUnit.MILLIS));
                       }

                       @Override
                       public void logException(StatementContext context, SQLException ex) {
                           logger.error("Exception executing SQL: {}", context.getRenderedSql(), ex);
                       }
                   });
    }

    @Override
    public void dispose(Jdbi instance) {
    }
}