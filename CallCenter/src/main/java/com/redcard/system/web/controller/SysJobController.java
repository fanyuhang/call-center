package com.redcard.system.web.controller;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.util.SecurityUtil;
import com.redcard.system.entity.SysJob;
import com.redcard.system.service.JobScheduler;
import com.redcard.system.service.SysJobManager;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
@RequestMapping(value = "/system/sysJob")
public class SysJobController {
    private static Logger logger = LoggerFactory.getLogger(SysJobController.class);

    @Autowired
    private SysJobManager sysJobManager;

    @Autowired
    private JobScheduler jobScheduler;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "system/sysJob/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<SysJob> list(String where, GridPageRequest pageRequest) throws SchedulerException {
        Page<SysJob> list = sysJobManager.find(where, pageRequest);

        for (SysJob job : list.getContent()) {
            Trigger trigger = jobScheduler.getTrigger(job.getTriggerName(), job.getTriggerGroup());
            if (trigger != null) {
                job.setLastSuccessDate(trigger.getPreviousFireTime());
                job.setNextFireTime(trigger.getNextFireTime());
                job.setStartTime(trigger.getStartTime());
                job.setEndTime(trigger.getEndTime());

                if (job.getTriggerType() == Constant.TriggerType.SIMPLE.ordinal()) {
                    job.setExecuteCount(((SimpleTrigger) trigger).getTimesTriggered());
                }
            }
        }

        return (new DataResponse<SysJob>(list));
    }

    @RequestMapping(value = "edit")
    public String edit(String menuNo, Integer id, Model model) {
        SysJob sysjob = sysJobManager.find(id);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("sysjob", sysjob);
        return "system/sysJob/edit";
    }

    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("sysjob", new SysJob());
        return "system/sysJob/add";
    }

    @RequestMapping(value = "find")
    public String find(String menuNo, Integer id, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("sysjob", sysJobManager.find(id));
        return "system/sysJob/view";
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) throws SchedulerException {
        AsyncResponse result = new AsyncResponse(false, "删除任务成功");
        jobScheduler.deleteSysJob(id);
        return result;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(SysJob sysjob) throws SchedulerException {
        AsyncResponse result = new AsyncResponse(false, "保存任务成功");
        sysjob.setOperateId(SecurityUtil.getCurrentUserId());
        sysjob.setOperateDate(new Date());
        jobScheduler.saveSysJob(sysjob);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(SysJob sysjob) throws SchedulerException {
        AsyncResponse result = new AsyncResponse(false, "保存任务成功");
        SysJob oldJob = this.sysJobManager.find(sysjob.getId());
        sysjob.setLastSuccessDate(oldJob.getLastSuccessDate());
        sysjob.setExecuteCount(oldJob.getExecuteCount());
        sysjob.setOperateId(SecurityUtil.getCurrentUserId());
        sysjob.setOperateDate(new Date());
        jobScheduler.saveSysJob(sysjob);
        return result;
    }


}
