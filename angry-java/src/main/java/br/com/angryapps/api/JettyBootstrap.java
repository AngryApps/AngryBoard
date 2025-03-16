package br.com.angryapps.api;

import br.com.angryapps.configs.di.MainInjector;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class JettyBootstrap {

    private static final Logger logger = LoggerFactory.getLogger(JettyBootstrap.class);

    private JettyBootstrap() {
    }

    public static void start() {
        try {
            Integer port = Integer.getInteger("server.port");
            String contextPath = System.getProperty("server.servlet.context-path");

            Server server = new Server(port);

            ServletContextHandler apiContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

            apiContext.setContextPath(contextPath);
            apiContext.setAttribute(ServletProperties.SERVICE_LOCATOR, MainInjector.getLocator());

            // Add Jersey servlet
            ServletHolder jerseyServlet = new ServletHolder(
                    new ServletContainer(new ApiApplication())
            );

            apiContext.addServlet(jerseyServlet, "/*");

            server.setHandler(apiContext);

            // Start the server
            server.start();
            logger.info("Server started on port {}", port);
        } catch (Exception e) {
            logger.error("Error starting server", e);
        }
    }
}
