package com.redcard.report.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterRule;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.ListToExcel;
import com.common.mybatis.paginator.domain.PageList;
import com.common.security.util.SecurityUtil;
import com.redcard.report.entity.CallOutDay;
import com.redcard.report.service.mybatis.ReportMybatisManager;
import org.apache.commons.lang3.time.DateUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * .
 * User: Administrator
 * Date: 12-9-22
 * Time: 下午8:36
 */
@Controller
@RequestMapping(value = "/report/callOutDay")
public class CallOutDayController {
    private Logger logger = LoggerFactory.getLogger(CallOutDayController.class);
    @Autowired
    private ReportMybatisManager reportMybatisManager;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "report/callOutDay/list";
    }

    private List<CallOutDay> getList(String where, GridPageRequest pageRequest) {
        FilterTranslator translator = new FilterTranslator(where);
        try {
            Map<String, Object> param = new HashMap<String, Object>();
            for (FilterRule rule : translator.getGroup().getRules()) {
                if (rule.getOp().equalsIgnoreCase(Constant.FILTER_OP_LIKE)) {
                    param.put(rule.getField(), "%" + rule.getValue() + "%");
                } else if (rule.getType().equalsIgnoreCase(Constant.FILTER_TYPE_DATE)) {
                    param.put(rule.getField(), DateUtils.parseDate((String)rule.getValue(), "yyyy-MM-dd hh:MM"));
                }else{
                    param.put(rule.getField(), rule.getValue());
                }
            }
            return reportMybatisManager.callOutDay(param);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
        }
        return new PageList<CallOutDay>();
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CallOutDay> list(String where, GridPageRequest pageRequest) {
        List<CallOutDay> pageList = getList(where, pageRequest);
        DataResponse<CallOutDay> dataResponse = new DataResponse<CallOutDay>(pageList);
        dataResponse.setTotal(pageList.size());
        return dataResponse;
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

            ListToExcel<CallOutDay> listToExcel = new ListToExcel<CallOutDay>(getList(where, null), columnList, propertyList, "sheet1");
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
