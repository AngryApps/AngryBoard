package br.com.angryapps.configs.di;

import com.zaxxer.hikari.HikariDataSource;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.jdbi.v3.core.Jdbi;

public class AppBinder extends AbstractBinder {

    @Override
    protected void configure() {
        bindFactory(JdbiFactory.class).to(Jdbi.class).in(Singleton.class);
        bindFactory(HikariFactory.class).to(HikariDataSource.class).in(Singleton.class);
    }
}
