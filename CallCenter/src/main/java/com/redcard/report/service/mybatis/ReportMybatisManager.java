package com.redcard.report.service.mybatis;

import com.redcard.report.dao.mybatis.ReportMapper;
import com.redcard.report.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReportMybatisManager {

    @Autowired
    private ReportMapper reportMapper;

    public List<YeJiDay> yeJiDay(Map<String, Object> param) {
        return reportMapper.yeJiDay(param);
    }

    public List<YeJiMonth> yeJiMonth(Map<String, Object> param) {
        return reportMapper.yeJiMonth(param);
    }

    public List<CallOutDay> callOutDay(Map<String, Object> param) {
        return reportMapper.callOutDay(param);
    }

    public List<CallInDay> callInDay(Map<String, Object> param) {
        return reportMapper.callInDay(param);
    }

    public List<CallOutDayImport> callOutDayImport(Map<String, Object> param) {
        return reportMapper.callOutDayImport(param);
    }
}
