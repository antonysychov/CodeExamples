package org.komarichyn.server;


import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;
import org.komarichyn.common.guice.quartz.Quartz;
import org.komarichyn.common.guice.xmlrpc.GuiceRequestProcessorFactory;
import org.komarichyn.server.handlers.WalletHandler;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;


public class Server {

    private static final Logger log = LoggerFactory.getLogger(Server.class);
    private static final int SERVER_PORT = 5555;

    private WebServer server;


    @Inject
    private GuiceRequestProcessorFactory factory;


    @Inject
    private Quartz quartz;

    public Server() {
        server = new WebServer(SERVER_PORT);
    }

    private void initializeXmlRpcServer() throws XmlRpcException {
        XmlRpcServer xmlRpcServer = server.getXmlRpcServer();
        PropertyHandlerMapping phm = new PropertyHandlerMapping();
        phm.setRequestProcessorFactoryFactory(factory.getFactory());
        phm.addHandler("wallet", WalletHandler.class);
        xmlRpcServer.setHandlerMapping(phm);


        XmlRpcServerConfigImpl serverConfig =
                (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
        serverConfig.setEnabledForExtensions(true);
        serverConfig.setContentLengthOptional(false);
    }

    private void initializeLoggingJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SaveLogInformationJob.class)
                .withIdentity("SaveLongInformationJob", "group1") // name "myJob", group "group1"
                .build();


        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("20SecTrigger", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(20)
                        .repeatForever())
                .build();

        if(quartz.getScheduler().checkExists(job.getKey())){
            quartz.getScheduler().resumeAll();
        }else {
            quartz.getScheduler().scheduleJob(job, trigger);
        }
    }


    public void start() throws IOException, SchedulerException, XmlRpcException {
        log.info("starting server...");

        server.start();
        initializeXmlRpcServer();
        quartz.start();
        initializeLoggingJob();

        log.info("Server started on port:{}", SERVER_PORT);
    }

    public void stop() throws SchedulerException {
        log.info("stopping server...");
        server.shutdown();
        quartz.shutdown();
        log.debug("Server stopped");
    }

}
