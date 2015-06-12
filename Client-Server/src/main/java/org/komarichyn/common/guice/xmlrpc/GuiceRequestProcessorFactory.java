package org.komarichyn.common.guice.xmlrpc;

import com.google.inject.Injector;
import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.XmlRpcRequest;
import org.apache.xmlrpc.server.RequestProcessorFactoryFactory;

import javax.inject.Inject;


public class GuiceRequestProcessorFactory {

    private Injector injector;
    @Inject
    public GuiceRequestProcessorFactory(Injector injector){
        this.injector = injector;
    }

    @SuppressWarnings("unchecked")
    protected RequestProcessorFactoryFactory getRequestProcessorFactoryFactory(final Injector injector) {
        return new RequestProcessorFactoryFactory() {
            @Override
            public RequestProcessorFactory getRequestProcessorFactory(final Class pClass) throws XmlRpcException {
                return new RequestProcessorFactory() {
                    @Override
                    public Object getRequestProcessor(XmlRpcRequest pRequest) throws XmlRpcException {
                        return injector.getInstance(pClass);
                    }
                };
            }
        };
    }

    public RequestProcessorFactoryFactory getFactory(){
        return getRequestProcessorFactoryFactory(injector);
    }
}
