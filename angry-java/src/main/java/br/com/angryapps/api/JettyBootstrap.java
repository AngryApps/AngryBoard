package br.com.angryapps.api;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JettyBootstrap implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(JettyBootstrap.class);

    @Override
    public void run() {
        try {
            Integer port = Integer.getInteger("server.port");
            String contextPath = System.getProperty("server.servlet.context-path");

            Server server = new Server(port);

            ServletContextHandler apiContext = new ServletContextHandler(ServletContextHandler.SESSIONS);

            // Add Jersey servlet
            ServletHolder jerseyServlet = new ServletHolder(
                    new ServletContainer(new ApiApplication())
            );

            apiContext.setContextPath(contextPath);
            apiContext.addServlet(jerseyServlet, "/*");

            server.setHandler(apiContext);

            // Start the server
            server.start();
            logger.info("Server started on port {}", port);

            server.join();
        } catch (Exception e) {
            logger.error("Error starting server", e);
        }
    }
}
