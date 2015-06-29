package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.excel.ExcelExportUtil;
import com.common.core.filter.FilterRule;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.FilterGroupUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.service.TelephoneRecordManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/telephone/record")
public class RecordController {

    private static Logger log = LoggerFactory.getLogger(RecordController.class);

    @Autowired
    private TelephoneRecordManager telephoneRecordManager;

    @Value("#{settingsMap['phoneRecordAddress']}")
    private String phoneRecordAddress;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/record/list";
    }

    @RequestMapping(value = "outAuditInit")
    public String outAuditInit(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/record/outAudit";
    }

    @RequestMapping(value = "inAuditInit")
    public String inAuditInit(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/record/inAudit";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneRecord> list(GridPageRequest pageRequest, String where, String type) {
        if (!StringUtils.isEmpty(type)) {
            if (Constant.TELEPHONE_CALL_TYPE_OUT.toString().equals(type)) {
                where = FilterGroupUtil.addRule(where, "fldCallType", Constant.TELEPHONE_CALL_TYPE_OUT.toString(), "int", "equal");
            } else if (Constant.TELEPHONE_CALL_TYPE_IN.toString().equals(type)) {
                where = FilterGroupUtil.addRule(where, "fldCallType", Constant.TELEPHONE_CALL_TYPE_IN.toString(), "int", "equal");
            }
        }

        FilterTranslator filterTranslator = telephoneRecordManager.createFilter(where);
        try {
            if (filterTranslator != null) {
                for (FilterRule filterRule : filterTranslator.getGroup().getRules()) {
                    if ("fldCallBeginTime".equalsIgnoreCase(filterRule.getField()) && "lessorequal".equalsIgnoreCase(filterRule.getOp())) {
                        filterRule.setValue(DateFormatUtils.format(DateUtils.addDays(DateUtils.parseDate((String) filterRule.getValue(), "yyyy-MM-dd"), 1), "yyyy-MM-dd"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (new DataResponse<TelephoneRecord>(telephoneRecordManager.findAll(filterTranslator, pageRequest)));
    }

    @RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
        TelephoneRecord telephoneRecord = telephoneRecordManager.findById(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("telephoneRecord", telephoneRecord);
        model.addAttribute("phoneRecordAddress", phoneRecordAddress);
        return "telephone/record/view";
    }

    @RequestMapping(value = "auditView")
    public String auditView(String menuNo, String fldId, Model model) {
        TelephoneRecord telephoneRecord = telephoneRecordManager.findById(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("telephoneRecord", telephoneRecord);
        model.addAttribute("phoneRecordAddress", phoneRecordAddress);
        return "telephone/record/auditView";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "export")
    @ResponseBody
    public AsyncResponse export(String where, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出呼叫记录成功");
        try {
            ExcelExportUtil<TelephoneRecord> listToExcel = new ExcelExportUtil<TelephoneRecord>(telephoneRecordManager.findAllTelephoneRecord(null, where).getContent());
            String date = new SimpleDateFormat("yyyyMMddHHmmssS").format(new Date());
            String reportPath = request.getSession().getServletContext().getRealPath("/") + "/export/";
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

    @RequestMapping(value = "saveAudit")
    @ResponseBody
    public AsyncResponse saveAudit(String id, String taskId, String fldAuditFraction, String fldAuditComment) {
        AsyncResponse result = new AsyncResponse(false, "保存审查信息成功");
        telephoneRecordManager.saveAudti(id, taskId, fldAuditFraction, fldAuditComment);
        return result;
    }

    @RequestMapping(value = "saveWithTelephone")
    @ResponseBody
    public AsyncResponse saveWithTelephone(String telephone) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");

        try {
            TelephoneRecord telephoneRecord = telephoneRecordManager.save(telephone);
            result.getData().add(telephoneRecord.getFldId());
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new AsyncResponse(true, "系统错误，请联系管理人员");
        }

        return result;
    }

    @RequestMapping(value = "updateWithTelephone")
    @ResponseBody
    public AsyncResponse updateWithTelephone(String recordId, String callId) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");

        if (StringUtils.isBlank(recordId) || StringUtils.isBlank(callId)) {
            return new AsyncResponse(true, "系统错误，请联系管理人员");
        }

        try {
            telephoneRecordManager.updateWithTelephone(recordId, callId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return new AsyncResponse(true, "系统错误，请联系管理人员");
        }

        return result;
    }
}
