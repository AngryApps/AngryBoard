package br.com.angryapps;

import br.com.angryapps.api.JettyBootstrap;
import br.com.angryapps.configs.PropertiesLoader;
import br.com.angryapps.db.AppDatasource;
import br.com.angryapps.db.JdbiManager;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        PropertiesLoader.loadConfiguration();

        JdbiManager.getInstance();

        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down application...");
            AppDatasource.closeDataSource();
        }));

        JettyBootstrap.start();
    }
}