package com.redcard.telephone.web.controller;

import com.common.AppContext;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.ListToExcel;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneTask;
import com.redcard.telephone.service.TelephoneAssignDetailManager;
import com.redcard.telephone.service.TelephoneTaskManager;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 数据管理
 * User: Allen
 * Date: 10/28/12
 */
@Controller
@RequestMapping(value = "/telephone/task")
public class TelephoneTaskController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTaskController.class);

    @Autowired
    private TelephoneTaskManager telephoneTaskManager;

    @Autowired
    private TelephoneAssignDetailManager telephoneAssignDetailManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/task/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneTask> list(String where, GridPageRequest pageRequest) {
        Page<TelephoneTask> dictionaries = telephoneTaskManager.findAll(where, pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneTask>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "invalid")
    @ResponseBody
    public AsyncResponse invalid(Long id) {
        AsyncResponse result = new AsyncResponse(false, "处理成功");
        try {

            String detailId = telephoneTaskManager.invalid(id);
            if (StringUtils.isNotBlank(detailId)) {
                telephoneAssignDetailManager.updateFinishStatusByDetailId(detailId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "exchange")
    @ResponseBody
    public AsyncResponse exchange(String ids, String date, String callUserNo) {
        AsyncResponse result = new AsyncResponse(false, "交接成功");
        try {
            telephoneTaskManager.exchange(ids, date, callUserNo);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "recover")
    @ResponseBody
    public AsyncResponse recover(String ids) {
        AsyncResponse result = new AsyncResponse(false, "回收成功");
        try {
            telephoneTaskManager.recover(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "export")
    @ResponseBody
    public AsyncResponse export(String where, String columnNames, String propertyNames, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "生成导出列表成功");
        ObjectMapper mapper = new ObjectMapper();
        List<String> columnList = null;
        List<String> propertyList = null;
        try {
            columnList = mapper.readValue(columnNames, new TypeReference<List<String>>() {
            });
            propertyList = mapper.readValue(propertyNames, new TypeReference<List<String>>() {
            });
            Page<TelephoneTask> page = telephoneTaskManager.listTask(null, where);

            if (page != null && page.getContent() != null) {
                for (TelephoneTask telephoneTask : page.getContent()) {
                    telephoneTask.setFldTaskTypeLabel(AppContext.getInstance().getDictName(33, telephoneTask.getFldTaskType() + ""));
                    telephoneTask.setFldTaskStatusLabel(AppContext.getInstance().getDictName(30, telephoneTask.getFldTaskStatus() + ""));
                    telephoneTask.setFldCallStatusLabel(AppContext.getInstance().getDictName(23, telephoneTask.getFldCallStatus() + ""));
                    telephoneTask.setFldResultTypeLabel(AppContext.getInstance().getDictName(27, telephoneTask.getFldResultType() + ""));
                }
            }
            ListToExcel<TelephoneTask> listToExcel = new ListToExcel<TelephoneTask>(page.getContent(), columnList, propertyList, "sheet1");
            String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
            String reportPath = request.getSession().getServletContext().getRealPath("/") + "/trade/";
            String fileName = SecurityUtil.getCurrentUserId() + "_" + date + ".xls";
            File pathFile = new File(reportPath);
            if (!pathFile.exists())
                pathFile.mkdirs();
            result.getData().add(listToExcel.generate(reportPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }
}
