package com.redcard.telephone.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.excel.ExcelExportUtil;
import com.common.core.excel.ExcelImportUtil;
import com.common.core.grid.AsyncResponse;
import com.common.core.util.EntityUtil;
import com.common.core.util.FileHelper;
import com.redcard.telephone.entity.TelephoneImportEntity;
import com.redcard.telephone.service.TelephoneCustomerManager;

@Controller
@RequestMapping(value = "/telephone/import")
public class TelephoneImportController {
	private static Logger log = LoggerFactory.getLogger(TelephoneImportController.class);
	
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/import/list";
    }
	
	@RequestMapping(value = "template/{fileName}")
    public void template(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator + "template" + File.separator + fileName + ".xls";
        File file = new File(path);
        String name = new String(file.getName().getBytes("GB2312"), "ISO_8859_1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            OutputStream os = response.getOutputStream();
            byte[] b = new byte[1024];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }
            os.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
	
	@RequestMapping(value = "upload")
    @ResponseBody
    public AsyncResponse upload(HttpServletRequest request, HttpServletResponse response) {
		AsyncResponse result = new AsyncResponse();
        String savedFileName = null;
        try {
            savedFileName = FileHelper.uploadFile(request, response);

            if (savedFileName != null && !savedFileName.isEmpty()) {
            	result.setIsError(false);
            	result.setMessage(savedFileName);
                return result;
            } else {
            	return new AsyncResponse(true, "上传失败！");
            }
        } catch (Exception e) {
            log.error("上传文件失败, {}", e.getMessage());
            return new AsyncResponse(true, "上传失败！");
        }
    }
	
	@RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse upload(String fileName,String fldDuplicateStatus,HttpServletRequest request,HttpServletResponse response) {
		AsyncResponse result = new AsyncResponse(false,"导入话单成功!");
        try {
        	List<TelephoneImportEntity> objects = ExcelImportUtil.excelImport(TelephoneImportEntity.class, fileName);
            if(null != objects && objects.size()>0) {
            	String uuid = EntityUtil.getId();
            	//存放原始话单
            	ExcelExportUtil<TelephoneImportEntity> listToExcel = new ExcelExportUtil<TelephoneImportEntity>(objects);
            	String reportPath = request.getSession().getServletContext().getRealPath("/") + File.separator+Constant.TELEPHONE_IMPORTT_PATH+File.separator+uuid;
            	File pathFile = new File(reportPath);
                if (!pathFile.exists())
                    pathFile.mkdirs();
                fileName = StringUtils.substringAfterLast(fileName, File.separator);
                String path = reportPath + fileName;
            	listToExcel.generate(path);
            	
            	for(TelephoneImportEntity importEntity : objects) {
            		if(Constant.DUPLICATE_STATUS_Y == Integer.valueOf(fldDuplicateStatus)) {//去重:姓名+手机或姓名+固定电话
            			//Long count = telephoneCustomerManager.countByPhoneOrMobile(importEntity.getCustName(),importEntity.getTelephone(),importEntity.getMobile());
            		} else {
            			
            		}
            	}
            }
            return result;
        } catch (Exception e) {
            log.error("导入文件失败, {}", e.getMessage());
            return new AsyncResponse(true, "导入话单失败！");
        }
    }
}
