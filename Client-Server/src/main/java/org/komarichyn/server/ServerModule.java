package org.komarichyn.server;

import com.google.inject.AbstractModule;
import org.komarichyn.common.guice.jpa.JPAPlayerModule;
import org.komarichyn.common.guice.quartz.QuartzModule;
import org.komarichyn.common.guice.xmlrpc.XmlRPCModule;
import org.komarichyn.server.handlers.WalletHandler;

import javax.inject.Singleton;

public class ServerModule  extends AbstractModule {
    @Override
    protected void configure() {

        install(new QuartzModule());
        install(new JPAPlayerModule());
        install(new XmlRPCModule());

        bind(Server.class).in(Singleton.class);
        bind(WalletHandler.class).in(Singleton.class);

        bind(SaveLogInformationJob.class).in(Singleton.class);
    }
}
