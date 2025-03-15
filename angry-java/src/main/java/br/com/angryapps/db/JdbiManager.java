package br.com.angryapps.db;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.statement.SqlLogger;
import org.jdbi.v3.core.statement.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;

public class JdbiManager {
    private static final Logger logger = LoggerFactory.getLogger(JdbiManager.class);
    private static Jdbi jdbi = getJdbi();

    private static Jdbi getJdbi() {
        return Jdbi.create(AppDatasource.getDataSource())
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

    public static Jdbi getInstance() {
        return jdbi;
    }
}