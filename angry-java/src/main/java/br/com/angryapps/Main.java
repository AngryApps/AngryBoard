package br.com.angryapps;

import br.com.angryapps.api.JettyBootstrap;
import br.com.angryapps.config.PropertiesLoader;
import br.com.angryapps.db.AppDatasource;
import br.com.angryapps.db.JdbiManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        PropertiesLoader.loadConfiguration();

        AppDatasource.getDataSource();
        JdbiManager.getInstance();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down application...");
            AppDatasource.closeDataSource();
        }));

        executor.submit(new JettyBootstrap());
    }
}