package org.komarichyn.client;

import com.google.inject.AbstractModule;

import javax.inject.Singleton;


public class ClientModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Client.class).in(Singleton.class);
        bind(Service.class).in(Singleton.class);
    }
}
