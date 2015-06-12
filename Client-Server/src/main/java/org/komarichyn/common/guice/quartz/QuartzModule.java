package org.komarichyn.common.guice.quartz;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;


public class QuartzModule extends AbstractModule {
    @Override
    protected void configure()
    {
        bind(SchedulerFactory.class).to(StdSchedulerFactory.class);
        bind(GuiceJobFactory.class);
        bind(Quartz.class).in(Scopes.SINGLETON);
    }
}
