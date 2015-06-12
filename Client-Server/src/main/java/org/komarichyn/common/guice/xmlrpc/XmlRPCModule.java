package org.komarichyn.common.guice.xmlrpc;

import com.google.inject.AbstractModule;


public class XmlRPCModule extends AbstractModule{
    @Override
    protected void configure() {
        bind(GuiceRequestProcessorFactory.class);
    }
}
