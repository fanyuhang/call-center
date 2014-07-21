package com.redcard.customer.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.excel.ExcelImportUtil;
import com.common.core.grid.AsyncResponse;
import com.common.core.util.DateUtil;
import com.common.core.util.EntityUtil;
import com.common.core.util.FileHelper;
import com.common.security.entity.User;
import com.common.security.service.UserManager;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.ImportEntity;
import com.redcard.customer.service.CustomerManager;

@Controller
@RequestMapping(value = "/customer/common")
public class CommonController {
    private static Logger logger = LoggerFactory.getLogger(CommonController.class);
    
    @Autowired
	private CustomerManager customerManager;
    @Autowired
    private UserManager userManager;
    
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
    
    @RequestMapping(value = "download")
    public void download(String filepath, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("multipart/form-data");
        File file = new File(filepath);
        String name = new String(file.getName().getBytes("GB2312"), "ISO_8859_1");
        response.setHeader("Content-Disposition", "attachment;fileName=" + name);
        InputStream inputStream = null;
        OutputStream os = null;
        try {
            inputStream = new FileInputStream(file);
            os = response.getOutputStream();
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
            if (os != null) {
            	os.close();
            }
        }
    }
    
    @RequestMapping(value = "upload/{type}")
    @ResponseBody
    public AsyncResponse upload(@PathVariable Integer type, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = null;
        String savedFileName = null;
        try {
            savedFileName = FileHelper.uploadFile(request, response);

            if (savedFileName != null && !savedFileName.isEmpty()) {
                switch (type) {
                    case 0: {
                        List<Customer> objects = ExcelImportUtil.excelImport(Customer.class, savedFileName);
                        if(null != objects && objects.size()>0) {
                        	for(Customer customer : objects) {
                        		//校验：姓名+固定电话或者姓名+手机号是否重复
                        		Long count = customerManager.countByPhoneOrMobile(customer.getFldName(),customer.getFldPhone(),customer.getFldMobile());
                        		if(count > 0)
                        			continue;
                        		
                        		customer.setFldId(EntityUtil.getId());
                        		customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
                        		customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                customer.setFldCreateDate(new Date());
                                customer.setFldOperateDate(new Date());
                        	}
                        	customerManager.save(objects);
                        }
                        break;
                    }
                    case 1:{
                    	List<ImportEntity> objects = ExcelImportUtil.excelImport(ImportEntity.class, savedFileName);
                    	if(null != objects && objects.size()>0) {
                    		for(ImportEntity importEntity : objects) {
                    			Customer customer = new Customer();
                    			customer.setFldName(importEntity.getCustName());
                    			if(!StringUtils.isEmpty(importEntity.getPhone())) {
                    				if(importEntity.getPhone().length() == 11) {
                    					customer.setFldMobile(importEntity.getPhone());
                    				} else {
                    					customer.setFldPhone(importEntity.getPhone());
                    				}
                    				Long count = customerManager.countByPhoneOrMobile(customer.getFldName(),customer.getFldPhone(),customer.getFldMobile());
                    				if(count <= 0) {
                    					customer.setFldId(EntityUtil.getId());
                    					customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                    			        customer.setFldCreateDate(new Date());
                    			        customer.setFldOperateDate(new Date());
                    			        customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
                    			        customer.setFldCardTotalMoney((double) 0);
                    			        customer.setFldSource(importEntity.getSource());
                    			        customer.setFldBirthday(DateUtil.getDateByStr(importEntity.getBirthday()));
                    			        customer.setFldIdentityNo(importEntity.getIdentityNo());
                    			        if(!StringUtils.isEmpty(importEntity.getFinancialUserNo())) {
                    			        	List<User> listUser = userManager.findByUserName(importEntity.getFinancialUserNo());
                    			        	if(listUser != null && listUser.size() > 0)
                    			        		customer.setFldFinancialUserNo(listUser.get(0).getLoginName());
                    			        }
                    			        if(!StringUtils.isEmpty(importEntity.getCardMoney())) {
                    			        	customer.setFldCardTotalMoney(Double.valueOf(importEntity.getCardMoney()));
                    			        }
                    			        customer.setFldCardLevel(importEntity.getCardLevel());
                    					customerManager.save(customer);
                    				}
                    			}
                    			
                    		}                    		
                    	}
                    	break;
                    }
                    default:
                        break;
                }
                return new AsyncResponse(false, "导入成功！");
            } else {
                return new AsyncResponse(false, "正在续传...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("导入文件失败, {}", e.getMessage());
            return new AsyncResponse(true, "上传失败！");
        }
    }
}
