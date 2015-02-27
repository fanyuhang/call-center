package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.excel.ExcelExportUtil;
import com.common.core.excel.ExcelImportUtil;
import com.common.core.filter.FilterRule;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.core.util.FileHelper;
import com.common.core.util.JsonHelper;
import com.common.security.util.SecurityUtil;
import com.redcard.telephone.entity.TelephoneCustomer;
import com.redcard.telephone.entity.TelephoneImport;
import com.redcard.telephone.entity.TelephoneImportDetail;
import com.redcard.telephone.entity.TelephoneImportEntity;
import com.redcard.telephone.service.TelephoneCustomerManager;
import com.redcard.telephone.service.TelephoneImportDetailManager;
import com.redcard.telephone.service.TelephoneImportManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @RequestMapping(value = "listForAssign")
    @ResponseBody
    public DataResponse<TelephoneImport> listForAssign(GridPageRequest pageRequest, String where) {
        FilterTranslator filterTranslator = telephoneImportManager.createFilter(where);

        String fldName = null;
        if(filterTranslator!=null){
            for(FilterRule filterRule: filterTranslator.getGroup().getRules()){
                if("fldName".equalsIgnoreCase(filterRule.getField())){
                    fldName = (String)filterRule.getValue();
                }
            }
        }

        return (new DataResponse<TelephoneImport>(telephoneImportManager.findTelephoneImportByNameAndLoginName(fldName, SecurityUtil.getCurrentUserLoginName(), pageRequest)));
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
            log.error(e.toString());
            e.printStackTrace();
        } catch (IOException e) {
            log.error(e.toString());
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
            e.printStackTrace();
            return new AsyncResponse(true, "上传失败！");
        }
    }

    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public String fileUpload(String importName, Integer fldDuplicateStatus, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导入话单成功!");
        try {
            String fileName = FileHelper.uploadFile(request, response);

            List<TelephoneImportEntity> objects = ExcelImportUtil.excelImport(TelephoneImportEntity.class, fileName);
            if (null != objects && objects.size() > 0) {
                String uuid = EntityUtil.getId();
                //存放原始话单
                ExcelExportUtil<TelephoneImportEntity> listToExcel = new ExcelExportUtil<TelephoneImportEntity>(objects);
                String localPath = Constant.TELEPHONE_IMPORTT_PATH + File.separator + uuid + File.separator;
                String reportPath = request.getSession().getServletContext().getRealPath("/") + File.separator + localPath;
                File pathFile = new File(reportPath);
                if (!pathFile.exists())
                    pathFile.mkdirs();
                fileName = StringUtils.substringAfterLast(fileName, File.separator);
                String origFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_ORIG + "_" + fileName;
                String path = reportPath + origFileName;
                listToExcel.generate(path);

                List<TelephoneImportEntity> dupList = new ArrayList<TelephoneImportEntity>();//重复话单
                List<TelephoneImportEntity> list = new ArrayList<TelephoneImportEntity>();//非重复话单
                for (TelephoneImportEntity importEntity : objects) {

                    if(StringUtils.isNotBlank(importEntity.getMobile())&&importEntity.getMobile().length()!=11){
                        continue;
                    }

                    if(StringUtils.isNotBlank(importEntity.getTelephone())&&importEntity.getTelephone().length()<7){
                        continue;
                    }

                    if(StringUtils.isBlank(importEntity.getMobile())&&StringUtils.isBlank(importEntity.getTelephone())){
                        continue;
                    }

                    if (Constant.DUPLICATE_STATUS_Y == Integer.valueOf(fldDuplicateStatus)) {//去重:姓名+手机或姓名+固定电话
                        if (list.size() == 0) {
                            Long count = telephoneCustomerManager.countByPhoneOrMobile(importEntity.getCustName(), importEntity.getTelephone(), importEntity.getMobile());
                            if (count > 0) {
                                dupList.add(importEntity);
                            } else {
                                list.add(importEntity);
                            }
                            continue;
                        }
                        boolean flag = true;
                        for (TelephoneImportEntity telephoneImportEntity : list) {
                            if (StringUtils.isNotBlank(telephoneImportEntity.getMobile())
                                    && StringUtils.isNotBlank(importEntity.getMobile())
                                    && telephoneImportEntity.getMobile().equals(importEntity.getMobile())) {
                                dupList.add(importEntity);
                                flag = false;
                                break;
                            } else if (StringUtils.isNotBlank(telephoneImportEntity.getTelephone())
                                    && StringUtils.isNotBlank(importEntity.getTelephone())
                                    && telephoneImportEntity.getCustName().equals(importEntity.getCustName())
                                    && telephoneImportEntity.getTelephone().equals(importEntity.getTelephone())) {
                                dupList.add(importEntity);
                                flag = false;
                                break;
                            }
                        }
                        if (flag) {
                            Long count = telephoneCustomerManager.countByPhoneOrMobile(importEntity.getCustName(), importEntity.getTelephone(), importEntity.getMobile());
                            if (count > 0) {
                                dupList.add(importEntity);
                            } else {
                                list.add(importEntity);
                            }
                        }
                    } else {
                        Long count = telephoneCustomerManager.countByPhoneOrMobile(importEntity.getCustName(), importEntity.getTelephone(), importEntity.getMobile());
                        if (count > 0) {
                            dupList.add(importEntity);
                        } else {
                            if (list.size() == 0) {
                                list.add(importEntity);
                            } else {
                                boolean flag = true;
                                for (TelephoneImportEntity telephoneImportEntity : list) {
                                    if (StringUtils.isNotBlank(telephoneImportEntity.getMobile())
                                            && StringUtils.isNotBlank(importEntity.getMobile())
                                            && telephoneImportEntity.getMobile().equals(importEntity.getMobile())) {
                                        dupList.add(importEntity);
                                        flag = false;
                                        break;
                                    } else if (StringUtils.isNotBlank(telephoneImportEntity.getTelephone())
                                            && StringUtils.isNotBlank(importEntity.getTelephone())
                                            && telephoneImportEntity.getCustName().equals(importEntity.getCustName())
                                            && telephoneImportEntity.getTelephone().equals(importEntity.getTelephone())) {
                                        dupList.add(importEntity);
                                        flag = false;
                                        break;
                                    }
                                }
                                if (flag) {
                                    list.add(importEntity);
                                }
                            }
                        }
                    }
                }

                //存放重复话单
                String dupFileName = null;
                if (null != dupList && dupList.size() > 0) {
                    dupFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_DUP + "_" + fileName;
                    ExcelExportUtil<TelephoneImportEntity> dupListToExcel = new ExcelExportUtil<TelephoneImportEntity>(dupList);
                    dupListToExcel.generate(reportPath + dupFileName);
                }

                //存放非重复话单
                String noDupFileName = null;
                if (null != list && list.size() > 0) {
                    noDupFileName = Constant.TELEPHONE_IMPORT_SAVE_TYPE_NO_DUP + "_" + fileName;
                    ExcelExportUtil<TelephoneImportEntity> noDupListToExcel = new ExcelExportUtil<TelephoneImportEntity>(list);
                    noDupListToExcel.generate(reportPath + noDupFileName);
                }

                //记录话务导入表
                TelephoneImport telephoneImport = new TelephoneImport();
                telephoneImport.setFldName(importName);
                telephoneImport.setFldId(EntityUtil.getId());
                telephoneImport.setFldDuplicateStatus(Integer.valueOf(fldDuplicateStatus));
                telephoneImport.setFldUploadFilePath(localPath + origFileName);//原始话单相对路径
                telephoneImport.setFldTotalNumber(objects.size());//原始话单记录总数
                telephoneImport.setFldDuplicateTotalNumber(dupList.size());//重复记录数
                if (!StringUtils.isEmpty(dupFileName)) {
                    telephoneImport.setFldDuplicateFilePath(localPath + dupFileName);//重复记录文件地址
                }
                telephoneImport.setFldImportTotalNumber(list.size());
                if (!StringUtils.isEmpty(noDupFileName)) {
                    telephoneImport.setFldImportFilePath(localPath + noDupFileName);
                }
                telephoneImport.setFldAssignTotalNumber(0);
                telephoneImport.setFldOperateDate(new Date());
                telephoneImport.setFldCreateDate(new Date());
                telephoneImport.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                telephoneImportManager.save(telephoneImport);

                //记录话单原始表
                List<TelephoneCustomer> telephoneCustomerList = new ArrayList<TelephoneCustomer>();
                int index = 0;
                for (TelephoneImportEntity telephoneImportEntity : list) {
                    TelephoneCustomer telephoneCustomer = new TelephoneCustomer();
                    telephoneCustomer.setFldId(EntityUtil.getId());
                    telephoneCustomer.setFldCustomerName(telephoneImportEntity.getCustName());
                    telephoneCustomer.setFldGender(telephoneImportEntity.getGender());
                    telephoneCustomer.setFldMobile(telephoneImportEntity.getMobile());
                    telephoneCustomer.setFldPhone(telephoneImportEntity.getTelephone());
                    telephoneCustomer.setFldComment(telephoneImportEntity.getFldComment());
                    telephoneCustomer.setFldAddress(telephoneImportEntity.getAddress());
                    telephoneCustomer.setFldOperateDate(new Date());
                    telephoneCustomer.setFldCreateDate(new Date());
                    telephoneCustomer.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                    telephoneCustomer.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_UNASSIGN);//分配状态：未分配
                    telephoneCustomer.setFldSource(Constant.TELEPHONE_CUSTOMER_SOURCE_IMPORT);//客户来源,话务导入

                    telephoneCustomerList.add(telephoneCustomer);

                    telephoneImportEntity.setFldTelephoneId(telephoneCustomer.getFldId());
                    list.set(index, telephoneImportEntity);

                    index++;
                }
                telephoneCustomerManager.save(telephoneCustomerList);

                //记录话务导入明细表
                List<TelephoneImportDetail> telephoneImportDetailList = new ArrayList<TelephoneImportDetail>();
                for (TelephoneImportEntity telephoneImportEntity : list) {
                    TelephoneImportDetail telephoneImportDetail = new TelephoneImportDetail();
                    telephoneImportDetail.setFldId(EntityUtil.getId());
                    telephoneImportDetail.setFldTelephoneId(telephoneImportEntity.getFldTelephoneId());
                    telephoneImportDetail.setFldImportId(telephoneImport.getFldId());
                    telephoneImportDetail.setFldCustomerName(telephoneImportEntity.getCustName());
                    telephoneImportDetail.setFldGender(telephoneImportEntity.getGender());
                    telephoneImportDetail.setFldMobile(telephoneImportEntity.getMobile());
                    telephoneImportDetail.setFldPhone(telephoneImportEntity.getTelephone());
                    telephoneImportDetail.setFldComment(telephoneImportEntity.getFldComment());
                    telephoneImportDetail.setFldAddress(telephoneImportEntity.getAddress());
                    telephoneImportDetail.setFldOperateDate(new Date());
                    telephoneImportDetail.setFldCreateDate(new Date());
                    telephoneImportDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                    telephoneImportDetail.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_UNASSIGN);//分配状态：未分配
                    telephoneImportDetail.setFldAssignNumber(0);//分配次数

                    telephoneImportDetailList.add(telephoneImportDetail);
                }
                if (Constant.DUPLICATE_STATUS_N == Integer.valueOf(fldDuplicateStatus)) {
                    for (TelephoneImportEntity telephoneImportEntity : dupList) {
                        TelephoneImportDetail telephoneImportDetail = new TelephoneImportDetail();
                        telephoneImportDetail.setFldId(EntityUtil.getId());
                        telephoneImportDetail.setFldImportId(telephoneImport.getFldId());
                        telephoneImportDetail.setFldCustomerName(telephoneImportEntity.getCustName());
                        telephoneImportDetail.setFldGender(telephoneImportEntity.getGender());
                        telephoneImportDetail.setFldMobile(telephoneImportEntity.getMobile());
                        telephoneImportDetail.setFldPhone(telephoneImportEntity.getTelephone());
                        telephoneImportDetail.setFldComment(telephoneImportEntity.getFldComment());
                        telephoneImportDetail.setFldAddress(telephoneImportEntity.getAddress());
                        telephoneImportDetail.setFldOperateDate(new Date());
                        telephoneImportDetail.setFldCreateDate(new Date());
                        telephoneImportDetail.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
                        telephoneImportDetail.setFldAssignStatus(Constant.TELEPHONE_ASSIGN_STATUS_UNASSIGN);//分配状态：未分配
                        telephoneImportDetail.setFldAssignNumber(0);//分配次数

                        telephoneImportDetailList.add(telephoneImportDetail);
                    }
                }
                telephoneImportDetailManager.save(telephoneImportDetailList);
            }
        } catch (Exception e) {
            log.error("导入文件失败, {}", e.getMessage());
            e.printStackTrace();
            return JsonHelper.serialize(new AsyncResponse(true, "导入话单失败！"));
        }
        return JsonHelper.serialize(result);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "origexport")
    @ResponseBody
    public AsyncResponse origexport(String id, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出原始话单列表成功");
        try {
            TelephoneImport telephoneImport = this.telephoneImportManager.findById(id);
            if (null != telephoneImport && !StringUtils.isEmpty(telephoneImport.getFldUploadFilePath())) {
                result.getData().add(request.getSession().getServletContext().getRealPath("/") + File.separator + telephoneImport.getFldUploadFilePath());
            } else {
                return new AsyncResponse(false, "未找到原始话单");
            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "nodupexport")
    @ResponseBody
    public AsyncResponse nodupexport(String id, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出非重复话单列表成功");
        try {
            TelephoneImport telephoneImport = this.telephoneImportManager.findById(id);
            if (null != telephoneImport && !StringUtils.isEmpty(telephoneImport.getFldImportFilePath())) {
                result.getData().add(request.getSession().getServletContext().getRealPath("/") + File.separator + telephoneImport.getFldImportFilePath());
            } else {
                return new AsyncResponse(false, "未找到非重复话单");
            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "dupexport")
    @ResponseBody
    public AsyncResponse dupexport(String id, HttpServletRequest request, HttpServletResponse response) {
        AsyncResponse result = new AsyncResponse(false, "导出重复话单列表成功");
        try {
            TelephoneImport telephoneImport = this.telephoneImportManager.findById(id);
            if (null != telephoneImport && !StringUtils.isEmpty(telephoneImport.getFldDuplicateFilePath())) {
                result.getData().add(request.getSession().getServletContext().getRealPath("/") + File.separator + telephoneImport.getFldDuplicateFilePath());
            } else {
                return new AsyncResponse(false, "未找到重复话单");
            }
        } catch (Exception e) {
            log.error(e.toString());
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误");
        }
        return result;
    }

    @RequestMapping(value = "listAllUnAssignTelephone")
    @ResponseBody
    public List<TelephoneImport> listAllUnAssignTelephone() {
        return telephoneImportManager.listAllUnAssignTelephone();
    }

    @RequestMapping(value = "viewDtl")
    public String viewDtl(String menuNo, Model model, String fldId) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("importId", fldId);
        return "telephone/import/importDtl";
    }

    @RequestMapping(value = "showDtl")
    @ResponseBody
    public DataResponse<TelephoneImportDetail> showDtl(GridPageRequest pageRequest, String fldId) {
        FilterTranslator filterTranslator = telephoneImportDetailManager.createFilter(null);
        filterTranslator.addFilterRule("fldImportId", fldId, Constant.FILTER_OP_EQUAL);
        return new DataResponse<TelephoneImportDetail>(telephoneImportDetailManager.findAll(filterTranslator, pageRequest));
    }
}
