package com.redcard.telephone.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.excel.ExcelExportUtil;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneRecord;
import com.redcard.telephone.service.TelephoneRecordManager;

@Controller
@RequestMapping(value = "/telephone/record")
public class RecordController {
	@Autowired
	private TelephoneRecordManager telephoneRecordManager;
	
	@Value("#{settingsMap['phoneRecordAddress']}")
	private String phoneRecordAddress;

	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/record/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneRecord> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<TelephoneRecord>(telephoneRecordManager.findAllTelephoneRecord(pageRequest, where)));
    }
	
	@RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
		TelephoneRecord telephoneRecord = telephoneRecordManager.findById(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("telephoneRecord", telephoneRecord);
        model.addAttribute("phoneRecordAddress",phoneRecordAddress);
        return "telephone/record/view";
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
    public AsyncResponse saveAudit(String id,String taskId,String fldAuditFraction,String fldAuditComment) {
        AsyncResponse result = new AsyncResponse(false,"保存审查信息成功");
        telephoneRecordManager.saveAudti(id, taskId, fldAuditFraction, fldAuditComment);
        return result;
    }
}
