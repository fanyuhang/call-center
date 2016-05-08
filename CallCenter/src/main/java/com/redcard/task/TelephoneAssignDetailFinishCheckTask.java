package com.redcard.task;

import com.redcard.customer.service.ContractManager;
import com.redcard.telephone.service.TelephoneTaskManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class TelephoneAssignDetailFinishCheckTask extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(TelephoneAssignDetailFinishCheckTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("begin TelephoneAssignDetailFinishCheckTask");
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");

            TelephoneTaskManager telephoneTaskManager = applicationContext.getBean(TelephoneTaskManager.class);
            telephoneTaskManager.updateTelephoneAssignDetail();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } finally {
            logger.info("end TelephoneAssignDetailFinishCheckTask");
        }
    }
}
