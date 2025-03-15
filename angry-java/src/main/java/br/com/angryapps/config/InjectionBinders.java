package br.com.angryapps.config;

import br.com.angryapps.api.mappers.CardMapper;
import br.com.angryapps.api.mappers.ColumnMapper;
import br.com.angryapps.db.dao.CardDAO;
import br.com.angryapps.db.dao.ColumnDAO;
import br.com.angryapps.db.dao.impl.CardDAOImpl;
import br.com.angryapps.db.dao.impl.ColumnDAOImpl;
import jakarta.inject.Singleton;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class InjectionBinders extends AbstractBinder {

    @Override
    protected void configure() {
        // Bind implementations to interfaces
        bind(CardDAOImpl.class).to(CardDAO.class);
        bind(ColumnDAOImpl.class).to(ColumnDAO.class);

        // Bind concrete classes
        bind(CardMapper.class).to(CardMapper.class).in(Singleton.class);
        bind(ColumnMapper.class).to(ColumnMapper.class).in(Singleton.class);
    }
}
