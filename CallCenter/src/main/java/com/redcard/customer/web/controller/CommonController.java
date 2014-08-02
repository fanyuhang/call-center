package com.redcard.customer.web.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
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
import com.redcard.customer.entity.CustomerContract;
import com.redcard.customer.entity.CustomerProduct;
import com.redcard.customer.entity.CustomerProductDetail;
import com.redcard.customer.entity.ImportEntity;
import com.redcard.customer.service.ContractManager;
import com.redcard.customer.service.CustomerManager;
import com.redcard.customer.service.ProductDetailManager;
import com.redcard.customer.service.ProductManager;

@Controller
@RequestMapping(value = "/customer/common")
public class CommonController {
    private static Logger logger = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CustomerManager customerManager;
    @Autowired
    private UserManager userManager;
    @Autowired
    private ProductManager productManager;
    @Autowired
    private ProductDetailManager productDetailManager;
    @Autowired
    private ContractManager contractManager;

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

    @SuppressWarnings("unused")
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
                        if (null != objects && objects.size() > 0) {
                            for (Customer customer : objects) {
                                //校验：姓名+固定电话或者姓名+手机号是否重复
                                Long count = customerManager.countByPhoneOrMobile(customer.getFldName(), customer.getFldPhone(), customer.getFldMobile());
                                if (count > 0)
                                    continue;

                                customer.setFldId(EntityUtil.getId());
                                customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
                                customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                customer.setFldCreateDate(new Date());
                                customer.setFldOperateDate(new Date());
                                customer.setFldCardTotalMoney((double) 0);
                            }
                            customerManager.save(objects);
                        }
                        break;
                    }
                    case 1: {
                        List<ImportEntity> objects = ExcelImportUtil.excelImport(ImportEntity.class, savedFileName);
                        if (null != objects && objects.size() > 0) {
                            for (ImportEntity importEntity : objects) {
                                //产品信息
                                List<CustomerProduct> list = productManager.findByName(importEntity.getProductName());
                                CustomerProduct product = new CustomerProduct();
                                CustomerProductDetail productDetail = new CustomerProductDetail();
                                if (!(null != list && list.size() > 0)) {
                                    product.setFldId(EntityUtil.getId());
                                    product.setFldFullName(importEntity.getProductName());
                                    product.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                    product.setFldCreateDate(new Date());
                                    product.setFldOperateDate(new Date());
                                    product.setFldStatus(Constant.PRODUCT_STATUS_NORMAL);
                                    product.setFldEstablishDate(DateUtil.getDateByStr(importEntity.getEstablishDate()));
                                    product.setFldValueDate(DateUtil.getDateByStr(importEntity.getValueDate()));
                                    productManager.saveProductInfo(product);
                                } else {
                                    product = list.get(0);
                                }

                                //productDetail = productDetailManager.findByProductIdAndClearDays(product.getFldId(), Integer.valueOf(importEntity.getClearDays()));
                                BigDecimal bg = new BigDecimal(importEntity.getAnnualizedRate() * 100);
                                Double annualizedRate = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                                Long countProductDetail = productDetailManager.countByCondition(Constant.DAY_UNIT_DAY, Integer.valueOf(importEntity.getClearDays()), annualizedRate, product.getFldId());
                                if (countProductDetail <= 0) {
                                    productDetail = new CustomerProductDetail();
                                    productDetail.setFldId(EntityUtil.getId());
                                    productDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                    productDetail.setFldCreateDate(new Date());
                                    productDetail.setFldOperateDate(new Date());
                                    productDetail.setFldStatus(Constant.PRODUCT_DETAIL_STATUS_NORMAL);
                                    productDetail.setFldProductId(product.getFldId());
                                    productDetail.setFldDueDate(DateUtil.getDateByStr(importEntity.getDueDate()));
                                    productDetail.setFldClearDays(Integer.valueOf(importEntity.getClearDays()));
                                    productDetail.setFldPerformanceRadio(Double.valueOf(importEntity.getPerformanceRadio()));
                                    productDetail.setFldAnnualizedRate(Double.valueOf(importEntity.getAnnualizedRate()) * 100);
                                    productDetail.setFldDepositRate(Double.valueOf(importEntity.getDepositRate()) * 100);
                                    productDetail.setFldDayUnit(Constant.DAY_UNIT_DAY);
                                    productDetail.setFldMinPurchaseMoney(Double.valueOf(importEntity.getMinPurchaseMoney()));
                                    productDetail.setFldMaxPurchaseMoney(Double.valueOf(importEntity.getMaxPurchaseMoney()));
                                    productDetailManager.save(productDetail);
                                } else {
                                    productDetail = productDetailManager.findByCondition(Constant.DAY_UNIT_DAY, Integer.valueOf(importEntity.getClearDays()), annualizedRate, product.getFldId());
                                }

                                //客户信息
                                Customer customer = new Customer();
                                customer.setFldName(importEntity.getCustName());
                                //if(!StringUtils.isEmpty(importEntity.getPhone())) {
                                customer.setFldMobile(importEntity.getMobile());
                                customer.setFldPhone(importEntity.getPhone());
                                Long count = customerManager.countByPhoneOrMobile(customer.getFldName(), customer.getFldPhone(), customer.getFldMobile());
                                if (count <= 0) {
                                    customer.setFldId(EntityUtil.getId());
                                    customer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                    customer.setFldCreateDate(new Date());
                                    customer.setFldOperateDate(new Date());
                                    customer.setFldStatus(Constant.CUSTOMER_STATUS_NORMAL);
                                    customer.setFldCardTotalMoney((double) 0);
                                    customer.setFldSource(importEntity.getSource());
                                    customer.setFldBirthday(DateUtil.getDateByStr(importEntity.getBirthday()));
                                    customer.setFldIdentityNo(importEntity.getIdentityNo());
                                    if (!StringUtils.isEmpty(importEntity.getFinancialUserNo())) {
                                        List<User> listUser = userManager.findByUserName(importEntity.getFinancialUserNo());
                                        if (listUser != null && listUser.size() > 0)
                                            customer.setFldFinancialUserNo(listUser.get(0).getLoginName());
                                    }
                                    customer.setFldCardLevel(importEntity.getCardLevel());
                                    customer.setFldCardNo(importEntity.getCardNo());
                                    customer.setFldComment(importEntity.getComment());
                                } else {
                                    if (!StringUtils.isEmpty(customer.getFldMobile())) {
                                        customer = customerManager.findByCustNameAndMobile(customer.getFldName(), customer.getFldMobile());
                                    } else {
                                        customer = customerManager.findByCustNameAndPhone(customer.getFldName(), customer.getFldPhone());
                                    }
                                }

                                //客户的瑞得卡金额是一个累加的金额
                                if (!StringUtils.isEmpty(importEntity.getCardMoney())) {
                                    if(customer.getFldCardTotalMoney()!=null){
                                        customer.setFldCardTotalMoney(Double.valueOf(importEntity.getCardMoney())+customer.getFldCardTotalMoney());
                                    }else{
                                        customer.setFldCardTotalMoney(Double.valueOf(importEntity.getCardMoney()));
                                    }
                                }

                                customerManager.save(customer);
                                //}

                                //合同信息
                                CustomerContract contract = new CustomerContract();
                                contract.setFldId(importEntity.getContractNo());
                                contract.setFldCustomerId(customer.getFldId());
                                contract.setFldProductId(product.getFldId());
                                contract.setFldProductDetailId(productDetail.getFldId());
                                contract.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                                contract.setFldCreateDate(new Date());
                                contract.setFldOperateDate(new Date());
                                contract.setFldStatus(Constant.CONTRACT_STATUS_NORMAL);
                                contract.setFldSignDate(DateUtil.getDateByStr(importEntity.getSignDate()));
                                contract.setFldPurchaseMoney(Double.valueOf(importEntity.getPurchaseMoney()));
                                contract.setFldPerformanceMoney(Double.valueOf(importEntity.getPerformanceMoney()));
                                contract.setFldAnnualizedMoney(Double.valueOf(importEntity.getAnnualizedMoney()));
                                contract.setFldMoneyDate(DateUtil.getDateByStr(importEntity.getMoneyDate()));
                                contract.setFldCollectDays(Integer.valueOf(importEntity.getCollectDays()));
                                contract.setFldCollectMoney(Double.valueOf(importEntity.getCollectMoney()));
                                contract.setFldBankNo(importEntity.getBankNo());
                                contract.setFldBankName(importEntity.getBankName());
                                contract.setFldCardMoney(Double.valueOf(importEntity.getCardMoney()));
                                contract.setFldCardLevel(importEntity.getCardLevel());

                                if(DateUtils.truncatedCompareTo(productDetail.getFldDueDate(), new Date(), Calendar.DATE)>0){
                                    contract.setFldFinishStatus(Constant.CONTRACT_FINISH_STATUS_NO);
                                }else{
                                    contract.setFldFinishStatus(Constant.CONTRACT_FINISH_STATUS_YES);
                                }

                                if (!StringUtils.isEmpty(importEntity.getFinancialUserNo())) {
                                    List<User> listUser = userManager.findByUserName(importEntity.getFinancialUserNo());
                                    if (listUser != null && listUser.size() > 0)
                                        contract.setFldFinancialUserNo(listUser.get(0).getLoginName());
                                }
                                contract.setFldDepositRate(Double.valueOf(importEntity.getDepositRate()) * 100);
                                contract.setFldAnnualizedRate(Double.valueOf(importEntity.getAnnualizedRate()) * 100);
                                contract.setFldPerformanceRadio(Double.valueOf(importEntity.getPerformanceRadio()));
                                contract.setFldCardNo(importEntity.getCardNo());
                                contractManager.saveOnly(contract);
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
