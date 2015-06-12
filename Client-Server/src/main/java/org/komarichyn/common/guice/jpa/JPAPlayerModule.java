package org.komarichyn.common.guice.jpa;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.komarichyn.server.jpa.IPlayerDAO;
import org.komarichyn.server.jpa.dao.PlayerDAO;

import javax.inject.Singleton;


public class JPAPlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new JpaPersistModule("PersistenceUnit"));
        bind(JPAInitializer.class).asEagerSingleton();
        bind(IPlayerDAO.class).to(PlayerDAO.class).in(Singleton.class);
    }
}
