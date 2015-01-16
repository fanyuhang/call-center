package com.common.core.filter;

import com.common.Constant;
import com.common.core.util.JsonHelper;
import com.common.security.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Allen
 * Date: 9/21/12
 */
public class FilterTranslator {

    private static Logger logger = LoggerFactory.getLogger(FilterTranslator.class);

    //几个前缀/后缀
    private static final String paramPrefixToken = ":";

    private static final String groupLeftToken = "(";

    private static final String groupRightToken = ")";

    private static final String likeToken = "%";

    private static final String RIGHT = " 1=1 ";

    private static Map<String, ContextParamHolder> currentParmMatch = new HashMap<String, ContextParamHolder>();

    private static String timestampPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";  //2012-08-31T16:00:00.000Z

    private int paramCounter = 0;        //参数个数

    private FilterGroup group = new FilterGroup();

    private String condition;

    private Map<String, Object> params = new HashMap<String, Object>();

    static {
        currentParmMatch.put(Constant.FILTER_CONTEXT_USERID, new ContextParamHolder() {
            public Object getValue() {
                return SecurityUtil.getCurrentUserLoginName();
            }

            public Object getName() {
                return SecurityUtil.getCurrentUserLoginName();
            }
        });
        currentParmMatch.put(Constant.FILTER_CONTEXT_ROLEID, new ContextParamHolder() {
            public Object getValue() {
                //TODO deal with role id
                return "";
            }

            public Object getName() {
                return SecurityUtil.getCurrentUserId();
            }
        });
        currentParmMatch.put(Constant.FILTER_CONTEXT_DEPTID, new ContextParamHolder() {
            public Object getValue() {
                return SecurityUtil.getCurrentDept().getDeptCode();
            }

            public Object getName() {
                return "'" + SecurityUtil.getCurrentDept().getDeptCode() + "'";
            }
        });
    }

    public FilterTranslator() {
    }

    public FilterTranslator(String json) {
        if (StringUtils.isNotBlank(json)) {
            setGroup(JsonHelper.deserialize(json, FilterGroup.class));
        }
    }

    /**
     * set the current filter group for filter translator
     *
     * @param json the json string for pending filter group
     * @return filter translator
     */
    public FilterTranslator setFilterGroup(String json) {
        if (StringUtils.isNotBlank(json)) {
            logger.debug("json:{}", json);
            setGroup(JsonHelper.deserialize(json, FilterGroup.class));
            translate();
        }
        return this;
    }

    /**
     * add a set of new filter rules
     *
     * @param op   the operation between old filter group and pending filter group
     * @param json the json string for pending filter group
     */
    public FilterTranslator addFilterGroup(String op, String json) {
        if (StringUtils.isEmpty(json)) {
            return this;
        }
        FilterGroup filterGroup = JsonHelper.deserialize(json, FilterGroup.class);
        return this.addFilterGroup(op, filterGroup);
    }

    /**
     * add a set of new filter rules
     *
     * @param op          the operation between old filter group and pending filter group
     * @param filterGroup the pending filter group
     */
    public FilterTranslator addFilterGroup(String op, FilterGroup filterGroup) {
        FilterGroup mergedGroup = new FilterGroup();
        List<FilterGroup> groups = new ArrayList<FilterGroup>();
        groups.add(filterGroup);
        groups.add(this.group);
        mergedGroup.setGroups(groups);
        mergedGroup.setOp(op);
        this.group = mergedGroup;
        return this;
    }

    /**
     * add a new filter rule to the current filter group
     *
     * @param field parameter name
     * @param value parameter value
     * @param op    the operation for current parameter in query condition
     * @return filter translator
     */
    public FilterTranslator addFilterRule(String field, Object value, String op) {
        FilterRule filterRule = new FilterRule(field, value, op);
        this.getGroup().getRules().add(filterRule);
        return this;
    }

    /**
     * check if the current filter group contains a specific field parameter
     *
     * @param filterGroup a set of filter parameters
     * @param field       parameter name
     * @return true: exist, false : not exist
     */
    private boolean containsFilterRule(FilterGroup filterGroup, String field) {
        List<FilterRule> rules = filterGroup.getRules();
        for (FilterRule rule : rules) {
            if (field.equals(rule.getField())) {
                return true;
            }
        }
        for (FilterGroup childGroup : filterGroup.getGroups()) {
            if (containsFilterRule(childGroup, field))
                return true;
        }
        return false;
    }

    /**
     * add a new filter rule to the current filter group
     * if there are already filter rule exist for field in where(json from the frontend), use the the rule in where
     * otherwise, we will use the default filter rule(create by the input parameters)
     *
     * @param field parameter name
     * @param value parameter value
     * @param op    the operation for current parameter in query condition
     * @return filter translator
     */
    public FilterTranslator addDefaultFilterRule(String field, Object value, String op) {
        if (containsFilterRule(this.getGroup(), field)) {
            return this;
        }
        FilterRule filterRule = new FilterRule(field, value, op);
        this.getGroup().getRules().add(filterRule);
        return this;
    }

    public FilterTranslator translate() {
        this.condition = translateGroup(this.group);
        return this;
    }

    public String translateGroup(FilterGroup group) {
        StringBuffer buffer = new StringBuffer();
        if (group == null) return " 1=1 ";
        boolean appended = false;
        buffer.append(groupLeftToken);
        if (group.getRules() != null) {
            for (FilterRule filterRule : group.getRules()) {
                if (appended) {
                    //default and
                    buffer.append(getOperatorQueryText(group.getOp() == null ? Constant.FILTER_OP_AND : group.getOp()));
                }
                buffer.append(translateRule(filterRule));
                appended = true;
            }
        }
        if (group.getGroups() != null) {
            for (FilterGroup filterGroup : group.getGroups()) {
                if (appended) {
                    buffer.append(getOperatorQueryText(group.getOp()));
                }
                buffer.append(translateGroup(filterGroup));
                appended = true;
            }
        }
        buffer.append(groupRightToken);
        if (!appended) return " 1=1 ";
        return buffer.toString();
    }

    public static String getOperatorQueryText(String op) {
        String lowerOp = op.toLowerCase();
        if (Constant.FILTER_OP_ADD.equals(lowerOp)) {
            return " + ";
        } else if (Constant.FILTER_OP_BITWISEAND.equals(lowerOp)) {
            return " & ";
        } else if (Constant.FILTER_OP_BITWISENOT.equals(lowerOp)) {
            return " ~ ";
        } else if (Constant.FILTER_OP_BITWISEOR.equals(lowerOp)) {
            return " | ";
        } else if (Constant.FILTER_OP_BITWISEXOR.equals(lowerOp)) {
            return " ^ ";
        } else if (Constant.FILTER_OP_DIVIDE.equals(lowerOp)) {
            return " / ";
        } else if (Constant.FILTER_OP_EQUAL.equals(lowerOp)) {
            return " = ";
        } else if (Constant.FILTER_OP_GREATER.equals(lowerOp)) {
            return " > ";
        } else if (Constant.FILTER_OP_GREATEROREQUAL.equals(lowerOp)) {
            return " >= ";
        } else if (Constant.FILTER_OP_ISNULL.equals(lowerOp)) {
            return " is null ";
        } else if (Constant.FILTER_OP_ISNOTNULL.equals(lowerOp)) {
            return " is not null ";
        } else if (Constant.FILTER_OP_LESS.equals(lowerOp)) {
            return " < ";
        } else if (Constant.FILTER_OP_LESSOREQUAL.equals(lowerOp)) {
            return " <= ";
        } else if (Constant.FILTER_OP_LIKE.equals(lowerOp)) {
            return " like ";
        } else if (Constant.FILTER_OP_STARTWITH.equals(lowerOp)) {
            return " like ";
        } else if (Constant.FILTER_OP_ENDWITH.equals(lowerOp)) {
            return " like ";
        } else if (Constant.FILTER_OP_MODULO.equals(lowerOp)) {
            return " % ";
        } else if (Constant.FILTER_OP_MULTIPLY.equals(lowerOp)) {
            return " * ";
        } else if (Constant.FILTER_OP_NOTEQUAL.equals(lowerOp)) {
            return " <> ";
        } else if (Constant.FILTER_OP_SUBTRACT.equals(lowerOp)) {
            return " - ";
        } else if (Constant.FILTER_OP_AND.equals(lowerOp)) {
            return " and ";
        } else if (Constant.FILTER_OP_OR.equals(lowerOp)) {
            return " or ";
        } else if (Constant.FILTER_OP_IN.equals(lowerOp)) {
            return " in ";
        } else if (Constant.FILTER_OP_NOTIN.equals(lowerOp)) {
            return " not in ";
        } else {
            return " = ";
        }
    }

    public Object getFilterRuleValue(String op, Object value) {
        Assert.notNull(value);

        Object result = value;
        if (Constant.FILTER_OP_ISNULL.equals(op) || Constant.FILTER_OP_ISNOTNULL.equals(op)) {
            return null;
        }

        if (currentParmMatch.containsKey(value)) {
            //deal with the user variable in the parameter value
            result = currentParmMatch.get(value).getValue().toString();
        }

        if (Constant.FILTER_OP_LIKE.equals(op) || Constant.FILTER_OP_ENDWITH.equals(op)) {
            if (!result.toString().startsWith(likeToken)) {
                result = likeToken + result;
            }
        }
        if (Constant.FILTER_OP_LIKE.equals(op) || Constant.FILTER_OP_STARTWITH.equals(op)) {
            if (!result.toString().endsWith(likeToken)) {
                result = result + likeToken;
            }
        }
        return result;
    }

    public String translateRule(FilterRule rule) {
        StringBuffer buffer = new StringBuffer();
        if (rule == null || !isValidate(rule)) return RIGHT;

        if (currentParmMatch.containsKey(rule.getField())) {
            //deal with the user variable in the parameter names
            buffer.append(currentParmMatch.get(rule.getField()).getName());
        } else {
            buffer.append(rule.getField());
        }

        //操作符
        buffer.append(getOperatorQueryText(rule.getOp()));

        String op = rule.getOp().toLowerCase();

        if (Constant.FILTER_OP_IN.equals(op) || Constant.FILTER_OP_NOTIN.equals(op)) {
            String[] values = rule.getValue().toString().split(",");
            boolean appended = false;
            buffer.append("(");
            int namesuffix = 0;
            for (String value : values) {
                if (appended)
                    buffer.append(",");
                String result = createFilterParam(getFilterRuleValue(rule.getOp(), value), rule.getType(), rule.getField() + "_" + namesuffix++);
                if (StringUtils.isBlank(result))
                    continue;
                buffer.append(paramPrefixToken).append(result);
                appended = true;
            }
            buffer.append(")");
        } else if (!Constant.FILTER_OP_ISNULL.equals(op) && !Constant.FILTER_OP_ISNOTNULL.equals(op)) {
            //is null 和 is not null 不需要值
            String result = createFilterParam(getFilterRuleValue(rule.getOp(), rule.getValue()), rule.getType(), rule.getField());
            if (StringUtils.isNotEmpty(result))
                buffer.append(paramPrefixToken).append(result);
        }
        return buffer.toString();
    }

    /**
     * validate filter rule
     *
     * @param rule .
     * @return true: valid filter rule
     * @deprecated
     */
    public boolean isValidate(FilterRule rule) {
        /*Object value = rule.getValue();
        String type = rule.getType();
        Object val = value;
        if ("number".equals(type)) {
            if (StringUtils.isEmpty(val.toString().trim()))
                return false;
        } else if ("date".equals(type)) {
            try {
                val = dateFormat.parse(val.toString());
            } catch (ParseException e) {
                return false;
            }
        }*/
        return true;
    }

    /**
     * 数字值值为空串，日期类型不符合，或者不为number,date,string三种类型。不处理返回空串
     *
     * @param value field value from frontend
     * @param type  field type from frontend
     * @param name  field name from frontend
     * @return field name
     */
    private String createFilterParam(Object value, String type, String name) {
        Object typedValue = null;

        if (Constant.FILTER_TYPE_FLOAT.equals(type)) {
            //normally, we convert the float field from frontend to BigDecimal in order to match the money field in DB entity
            typedValue = new BigDecimal(value.toString());
        } else if (Constant.FILTER_TYPE_LONG.equals(type)) {
            typedValue = new Long(value.toString());
        }else if (Constant.FILTER_TYPE_INT.equals(type) || Constant.FILTER_TYPE_NUMBER.equals(type)) {
            //there are some difference between int and number, you'd better not use the "number" type in the frontend
            //this might be changed in the future
            typedValue = Integer.parseInt(value.toString());
        } else if (Constant.FILTER_TYPE_DATE.equals(type)) {
            try {
                //trying different parse way
                typedValue = DateUtils.parseDate(value.toString(), timestampPattern, "yyyy-MM-dd");
            } catch (ParseException e) {
                logger.error("parse field [name:{},type:date] error,message is: {}", name, e.getMessage());
            }
        } else if (Constant.FILTER_TYPE_STRING.equals(type)) {
            typedValue = value.toString();
        } else {
            typedValue = value;
        }
        //处理级联属性，表达式a.b作为hql的占位符时，不合法，将其中的 . 替换为 _
        if (name.contains(".")) {
            name = name.replace('.', '_');
        }
        String paramName = getParam(this.getParams(), name);
        this.getParams().put(paramName, typedValue);
        return paramName;
    }

    /**
     * 如果一个参数名被使用多次:如 a>=:arg1 and a<=:arg2
     *
     * @param params
     * @param key
     * @return
     */
    public static String getParam(Map<String, Object> params, String key) {
        if (params.containsKey(key)) {
            key += "_";
            return getParam(params, key);
        }
        return key;
    }

    public FilterGroup getGroup() {
        return group;
    }

    public void setGroup(FilterGroup group) {
        this.group = group;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public int getParamCounter() {
        return paramCounter;
    }
}