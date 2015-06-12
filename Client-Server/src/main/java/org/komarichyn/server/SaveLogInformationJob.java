package org.komarichyn.server;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * Created by mikom on 20.01.14.
 */
public class SaveLogInformationJob implements Job {
    @Inject
    private Service service;
    private static final Logger log = LoggerFactory.getLogger(SaveLogInformationJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.debug("start job...");
        service.persistenceStatistics();
        log.debug("job finished");
    }
}
