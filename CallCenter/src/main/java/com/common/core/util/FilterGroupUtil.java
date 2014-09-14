package com.common.core.util;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.common.core.filter.FilterGroup;
import com.common.core.filter.FilterRule;

public class FilterGroupUtil {
	public static String addRule(String where,String field,String value,String type,String op) {
		FilterGroup filterGroup = null;
		if(!StringUtils.isEmpty(where)) {
			filterGroup = JsonHelper.deserialize(where, FilterGroup.class);
		} else {
			filterGroup = new FilterGroup();
		}
    	List<FilterRule> ruleList = filterGroup.getRules();
    	FilterRule filterRule = new FilterRule();
    	filterRule.setOp(op);
    	filterRule.setField(field);
    	filterRule.setValue(value);
    	filterRule.setType(type);
    	ruleList.add(filterRule);
    	return JsonHelper.serialize(filterGroup);
	}
}
