package com.redcard.report.dao.mybatis;

import com.redcard.report.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportMapper {

    public List<YeJiDay> yeJiDay(Map<String, Object> param);

    public List<YeJiMonth> yeJiMonth(Map<String, Object> param);

    public List<CallOutDay> callOutDay(Map<String, Object> param);

    public List<CallInDay> callInDay(Map<String, Object> param);

    public List<CallOutDayImport> callOutDayImport(Map<String, Object> param);

}
