package com.redcard.task;

import com.redcard.customer.service.ContractManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CustomerContractFinishCheckTask extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(CustomerContractFinishCheckTask.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        try {
            logger.info("begin CustomerContractFinishCheckTask");
            ApplicationContext applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");

            ContractManager contractManager = applicationContext.getBean(ContractManager.class);
            contractManager.updateFinishStatus();
        } catch (SchedulerException e) {
            e.printStackTrace();
        } finally {
            logger.info("end CustomerContractFinishCheckTask");
        }
    }
}
