package com.redcard.telephone.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.FileHelper;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneImport;
import com.redcard.telephone.entity.TelephoneImportDetail;
import com.redcard.telephone.entity.TelephoneImportEntity;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneImportDetailManager;
import com.redcard.telephone.service.TelephoneImportManager;

@Controller
@RequestMapping(value = "/telephone/import")
public class TelephoneImportController {
	private static Logger log = LoggerFactory.getLogger(TelephoneImportController.class);
	
	@Autowired
	private TelephoneCustomerManager telephoneCustomerManager;
	@Autowired
	private TelephoneImportManager telephoneImportManager;
	@Autowired
	private TelephoneImportDetailManager telephoneImportDetailManager;
	
	@RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/import/list";
    }
	
	@RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneImport> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<TelephoneImport>(telephoneImportManager.findAllTelephoneImport(pageRequest, where)));
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
            	String localPath = Constant.TELEPHONE_IMPORTT_PATH+File.separator+uuid+File.separator;
            	String reportPath = request.getSession().getServletContext().getRealPath("/") + File.separator+localPath;
            	File pathFile = new File(reportPath);
                if (!pathFile.exists())
                    pathFile.mkdirs();
                fileName = StringUtils.substringAfterLast(fileName, File.separator);
                String origFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_ORIG + "_" + fileName;
                String path = reportPath + origFileName;
            	listToExcel.generate(path);
            	
            	List<TelephoneImportEntity> dupList = new ArrayList<TelephoneImportEntity>();//重复话单
            	List<TelephoneImportEntity> list = new ArrayList<TelephoneImportEntity>();//非重复话单
            	for(TelephoneImportEntity importEntity : objects) {
            		if(Constant.DUPLICATE_STATUS_Y == Integer.valueOf(fldDuplicateStatus)) {//去重:姓名+手机或姓名+固定电话
            			Long count = telephoneCustomerManager.countByPhoneOrMobile(importEntity.getCustName(),importEntity.getTelephone(),importEntity.getMobile());
            			if(count > 0) {
            				dupList.add(importEntity);
            			} else {
            				if(!dupList.contains(importEntity)) {
            					list.add(importEntity);
            				} else {
            					dupList.add(importEntity);
            				}
            			}
            		}
            	}
            	
            	String dupFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_DUP + "_" + fileName;
            	if(null != dupList && dupList.size() > 0) {//存放重复话单
            		ExcelExportUtil<TelephoneImportEntity> dupListToExcel = new ExcelExportUtil<TelephoneImportEntity>(dupList);
            		dupListToExcel.generate(reportPath + dupFileName);
            	}
            	
            	String noDupFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_NO_DUP + "_" + fileName;
            	if(null != list && list.size() > 0) {//存放非重复话单
            		ExcelExportUtil<TelephoneImportEntity> dupListToExcel = new ExcelExportUtil<TelephoneImportEntity>(list);
            		dupListToExcel.generate(reportPath + noDupFileName);
            	}
            	
            	//记录话务导入表
            	TelephoneImport telephoneImport = new TelephoneImport();
            	telephoneImport.setFldId(EntityUtil.getId());
            	telephoneImport.setFldDuplicateStatus(Integer.valueOf(fldDuplicateStatus));
            	telephoneImport.setFldUploadFilePath(localPath+origFileName);//原始话单相对路径
            	telephoneImport.setFldTotalNumber(objects.size());//原始话单记录总数
            	telephoneImport.setFldDuplicateTotalNumber(dupList.size());//重复记录数
            	telephoneImport.setFldDuplicateFilePath(localPath+dupFileName);//重复记录文件地址
            	telephoneImport.setFldImportTotalNumber(list.size());
            	telephoneImport.setFldImportFilePath(localPath+noDupFileName);
            	telephoneImport.setFldAssignTotalNumber(0);
            	telephoneImport.setFldOperateDate(new Date());
            	telephoneImport.setFldCreateDate(new Date());
            	telephoneImport.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            	telephoneImportManager.save(telephoneImport);
            	
            	//记录话务导入明细表
            	List<TelephoneImportDetail> telephoneImportDetailList = new ArrayList<TelephoneImportDetail>();
            	for(TelephoneImportEntity telephoneImportEntity : objects) {
            		TelephoneImportDetail telephoneImportDetail = new TelephoneImportDetail();
            		telephoneImportDetail.setFldId(EntityUtil.getId());
            		telephoneImportDetail.setFldImportId(telephoneImport.getFldId());
            		telephoneImportDetail.setFldCustomerName(telephoneImportEntity.getCustName());
            		telephoneImportDetail.setFldGender(telephoneImportEntity.getGender());
            		telephoneImportDetail.setFldMobile(telephoneImportEntity.getMobile());
            		telephoneImportDetail.setFldPhone(telephoneImportEntity.getTelephone());
            		telephoneImportDetail.setFldOperateDate(new Date());
            		telephoneImportDetail.setFldCreateDate(new Date());
            		telephoneImportDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            		
            		telephoneImportDetailList.add(telephoneImportDetail);
            	}
            	telephoneImportDetailManager.save(telephoneImportDetailList);
            	
            	//记录话单原始表
            	List<TelephoneCustomer> telephoneCustomerList = new ArrayList<TelephoneCustomer>();
            	for(TelephoneImportEntity telephoneImportEntity : list) {
            		TelephoneCustomer telephoneCustomer = new TelephoneCustomer();
            		telephoneCustomer.setFldId(EntityUtil.getId());
            		telephoneCustomer.setFldCustomerName(telephoneImportEntity.getCustName());
            		telephoneCustomer.setFldGender(telephoneImportEntity.getGender());
            		telephoneCustomer.setFldMobile(telephoneImportEntity.getMobile());
            		telephoneCustomer.setFldPhone(telephoneImportEntity.getTelephone());
            		telephoneCustomer.setFldOperateDate(new Date());
            		telephoneCustomer.setFldCreateDate(new Date());
            		telephoneCustomer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            		
            		telephoneCustomerList.add(telephoneCustomer);
            	}
            	telephoneCustomerManager.save(telephoneCustomerList);
            }
            return result;
        } catch (Exception e) {
            log.error("导入文件失败, {}", e.getMessage());
            return new AsyncResponse(true, "导入话单失败！");
        }
    }
}
