package com.common.core.filter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Allen
 * Date: 12-9-26
 */
public class FilterGroup implements Serializable {
	//在where没有传值的时候，保持程序可用
    private List<FilterRule> rules=new ArrayList<FilterRule>();

    private String op;
    
    private List<FilterGroup> groups=new ArrayList<FilterGroup>();

    public FilterGroup() {
    }

    public List<FilterRule> getRules() {
        return rules;
    }

    public void setRules(List<FilterRule> rules) {
        this.rules = rules;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public List<FilterGroup> getGroups() {
        return groups;
    }

    public void setGroups(List<FilterGroup> groups) {
        this.groups = groups;
    }

	@Override
	public String toString() {
		return "FilterGroup [rules=" + rules + ", op=" + op + ", groups="
				+ groups + "]";
	}
    
    
}
