package com.redcard.report.dao.mybatis;

import com.redcard.report.entity.CallInDay;
import com.redcard.report.entity.CallOutDay;
import com.redcard.report.entity.YeJiDay;
import com.redcard.report.entity.YeJiMonth;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportMapper {

    public List<YeJiDay> yeJiDay(Map<String, Object> param);

    public List<YeJiMonth> yeJiMonth(Map<String, Object> param);

    public List<CallOutDay> callOutDay(Map<String, Object> param);

    public List<CallInDay> callInDay(Map<String, Object> param);

}
