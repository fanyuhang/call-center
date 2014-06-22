package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.SystemEnum;
import com.common.security.entity.Dept;
import com.common.security.service.DeptManager;
import com.common.security.util.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/security/dept")
public class DeptController {

    private static Logger logger = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptManager deptManager;

    @RequestMapping(value = "init")
    public String init(String MenuNo, Model model) {
        model.addAttribute("MenuNo", MenuNo);
        return "security/dept/list";
    }

    @RequestMapping(value = "getNextChild")
    @ResponseBody
    public Dept getNextChild(String parent) {
        Dept dept = new Dept();
        String maxChildCode = deptManager.findMaxChildCode(parent);
        dept.setDeptCode(TreeUtil.getDeptChildCode(parent, maxChildCode));
        return dept;
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Dept> list() {
        //构造部门列表中的tree grid数据
        List<Dept> depts = deptManager.findAllDept();
        DataResponse dataResponse = new DataResponse<Dept>(TreeUtil.buildDeptTree(depts));
        dataResponse.setTotal(depts.size());
        return dataResponse;
    }

    @RequestMapping(value = "listDept")
    @ResponseBody
    public DataResponse<Dept> listDept(String where, GridPageRequest pageRequest) {
        Page<Dept> depts = deptManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<Dept>(depts);
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Dept dept) {
        AsyncResponse result = new AsyncResponse(false, "保存部门成功");
        dept.setSystem(SystemEnum.SYSTEM_SELF.toString());
        deptManager.saveDept(dept);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Dept dept) {
        AsyncResponse result = new AsyncResponse(false, "保存部门成功");
        deptManager.saveDept(dept);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String code) {
        AsyncResponse result;
        try {
            //TODO 统一异常处理
            deptManager.deleteDept(code);
            result = new AsyncResponse(false, "删除部门成功");
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error("delete:"+e.getMessage());
            result = new AsyncResponse(true, "发生异常");
        }
        return result;
    }
}
