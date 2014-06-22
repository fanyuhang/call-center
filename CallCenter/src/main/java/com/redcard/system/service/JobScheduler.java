package com.redcard.system.service;

import com.common.Constant;
import com.redcard.system.entity.SysJob;
import org.apache.commons.lang3.StringUtils;
import org.quartz.*;
import org.quartz.impl.StdScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Allen
 * Date: 12/3/12
 */
@Component
@Transactional(readOnly = false)
public class JobScheduler {

    private static final Logger logger = LoggerFactory.getLogger(JobScheduler.class);

    @Autowired(required = false)
    private StdScheduler scheduler;

    @Autowired
    private SysJobManager sysJobManager;

    public JobScheduler() {

    }

    public void initAllJob() {
        List<SysJob> jobs = sysJobManager.findAvailableJob();
        for (SysJob job : jobs) {
            try {
                scheduleJob(job);
            } catch (SchedulerException e) {
                logger.error("Can't start job[{}],error:{}", job.getJobName(), e.getMessage());
            }
        }
    }

    public Trigger getTrigger(String name, String group) throws SchedulerException {
        if (scheduler == null) {
            return null;
        }
        return scheduler.getTrigger(TriggerKey.triggerKey(name, group));
    }

    public void saveSysJob(SysJob sysJob) throws SchedulerException {
        sysJobManager.save(sysJob);
        if (sysJob.getStatus() == Constant.JOB_ENABLE) {
            scheduleJob(sysJob);
        }
    }

    public void deleteSysJob(Integer jobId) throws SchedulerException {
        SysJob sysJob = sysJobManager.find(jobId);
        sysJobManager.delete(jobId);
        if (scheduler != null) {
            scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        }
    }

    public void deleteSysJob(SysJob sysJob) throws SchedulerException {
        sysJobManager.delete(sysJob.getId());
        if (scheduler != null) {
            scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        }
    }

    protected Trigger newTrigger(SysJob sysJob) {
        Trigger trigger = null;

        if (sysJob.getTriggerType() == Constant.TriggerType.SIMPLE.ordinal()) {
            //simple trigger
            TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger().withIdentity(TriggerKey.triggerKey(sysJob.getTriggerName(), sysJob.getTriggerGroup()));
            if (sysJob.getRepeatCount() > 0) {
                trigger = triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(sysJob.getRepeatCount(), sysJob.getIntervalInSeconds())).build();
            } else {
                trigger = triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(sysJob.getIntervalInSeconds())).build();
            }
        } else if (sysJob.getTriggerType() == Constant.TriggerType.CRON.ordinal()) {
            //cron trigger
            trigger = TriggerBuilder.newTrigger().withIdentity(TriggerKey.triggerKey(sysJob.getTriggerName(), sysJob.getTriggerGroup())).withSchedule(CronScheduleBuilder.cronSchedule(sysJob.getCronExpression())).build();
        }
        return trigger;
    }

    protected boolean scheduleJob(SysJob sysJob) throws SchedulerException {
        if (scheduler == null) {
            return false;
        }

        if (scheduler.checkExists(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()))) {
            logger.error("Job[name:{},group:{}] is already exist, remove it", sysJob.getJobName(), sysJob.getJobGroup());
            //如果存在，删除，重新添加，防止不生效
            scheduler.deleteJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        }
        if (scheduler.checkExists(TriggerKey.triggerKey(sysJob.getTriggerName(), sysJob.getTriggerGroup()))) {
            logger.error("Trigger[name:{},group:{}] is already exist, remove it", sysJob.getTriggerName(), sysJob.getTriggerGroup());
            //如果存在，删除，重新添加，防止不生效
            scheduler.unscheduleJob(TriggerKey.triggerKey(sysJob.getTriggerName(), sysJob.getTriggerGroup()));
        }
        if (StringUtils.isBlank(sysJob.getJobClass())) {
            logger.error("No job class");
            return false;
        }
        Class jobClass;
        try {
            jobClass = Class.forName(sysJob.getJobClass());
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(sysJob.getJobName(), sysJob.getJobGroup()).build();
            Trigger trigger = newTrigger(sysJob);
            scheduler.scheduleJob(jobDetail, trigger);

            return true;
        } catch (ClassNotFoundException e) {
            logger.error("Can not find job class :{}", sysJob.getJobClass());
        }

        return false;
    }

    public boolean pauseJob(SysJob sysJob) throws SchedulerException {

        if (scheduler == null) {
            return false;
        }

        if (!scheduler.checkExists(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()))) {
            return false;
        }
        scheduler.pauseJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        return true;
    }

    public void pauseAll() throws SchedulerException {
        if (scheduler != null) {
            scheduler.pauseAll();
        }
    }

    public void resumeJob(SysJob sysJob) throws SchedulerException {
        if (scheduler != null) {
            scheduler.resumeJob(JobKey.jobKey(sysJob.getJobName(), sysJob.getJobGroup()));
        }
    }

    public void resumeAll() throws SchedulerException {
        if (scheduler != null) {
            scheduler.resumeAll();
        }
    }

    public void shutdown() throws SchedulerException {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }

    public void start() throws SchedulerException {
        if (scheduler != null) {
            scheduler.start();
        }
    }
}
