package br.com.angryapps.configs.di;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;

public class MainInjector {

    private static final ServiceLocator locator = initializeLocator();

    private static ServiceLocator initializeLocator() {
        ServiceLocator serviceLocator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        ServiceLocatorUtilities.bind(serviceLocator, new AppBinder());

        return serviceLocator;
    }

    public static ServiceLocator getLocator() {
        return locator;
    }

    public static <T> T getService(Class<T> service) {
        return locator.getService(service);
    }
}
