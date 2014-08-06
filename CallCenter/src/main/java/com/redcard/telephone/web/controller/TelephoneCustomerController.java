package com.redcard.telephone.web.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.core.excel.ExcelExportUtil;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.service.TelephoneCustomerManager;

@Controller
@RequestMapping(value = "/telephone/customer")
public class TelephoneCustomerController {
	private static Logger log = LoggerFactory.getLogger(TelephoneCustomerController.class);
	
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/customer/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneCustomer> list(GridPageRequest pageRequest, String where) {
        return (new DataResponse<TelephoneCustomer>(telephoneCustomerManager.findAll(where,pageRequest)));
    }
	
	@RequestMapping(value = "ascustomer")
    @ResponseBody
    public AsyncResponse ascustomer(String fldId) {
        AsyncResponse result = new AsyncResponse();
        try {
        	String rtn = telephoneCustomerManager.saveAsCustomer(fldId);
        	if("true".equals(rtn)) {
        		result.setIsError(false);
    			result.setMessage("转为客户成功");
        	} else {
        		result.setIsError(false);
    			result.setMessage(rtn);
        	}
		} catch (Exception e) {
			result.setIsError(true);
			result.setMessage("转为客户失败");
			log.error(e.toString());
		}
        return result;
    }
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "export")
    @ResponseBody
    public AsyncResponse export(String where, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出话单成功");
        try {
            ExcelExportUtil<TelephoneCustomer> listToExcel = new ExcelExportUtil<TelephoneCustomer>(telephoneCustomerManager.findAllTelephoneCustomer(null, where).getContent());
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
}
