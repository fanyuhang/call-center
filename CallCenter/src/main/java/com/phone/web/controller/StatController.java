package com.phone.web.controller;

import com.common.core.grid.DataResponse;
import com.common.security.entity.Dept;
import com.common.security.service.DeptManager;
import com.phone.entity.Statrecord;
import com.phone.service.StatrecordManager;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping(value = "/phone/stat")
public class StatController {

    private static Logger logger = LoggerFactory.getLogger(StatController.class);

    @Autowired
    private StatrecordManager statrecordManager;

    @Autowired
    private DeptManager deptManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "phone/stat/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Statrecord> list(String deptName) {
        List<Statrecord> statrecordList = statrecordManager.monitor();

        Dept dept = null;
        if (statrecordList != null && statrecordList.size() > 0) {

            Statrecord statrecord = null;
            Iterator<Statrecord> iter = statrecordList.iterator();
            while (iter.hasNext()) {
                statrecord = iter.next();

                dept = deptManager.findDeptByUser(statrecord.getUid());
                if (dept != null) {
                    statrecord.setDeptName(dept.getDeptName());
                    if (StringUtils.isNotBlank(deptName)&&dept.getDeptName().indexOf(deptName) == -1) {
                        iter.remove();
                    }
                }

            }
        }

        return (new DataResponse<Statrecord>(statrecordList));
    }

}
