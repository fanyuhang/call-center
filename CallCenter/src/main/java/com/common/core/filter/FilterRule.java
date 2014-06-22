package com.common.core.filter;

import java.io.Serializable;

/**
 * User: Allen
 * Date: 12-9-26
 */
public class FilterRule implements Serializable {

    private String field;      //字段名称

    private Object value;      //字段值

    private String op;         //比较符

    private String type;       //字段类型

    public FilterRule() {
    }

    public FilterRule(String field, Object value) {
        this(field, value, null);
    }

    public FilterRule(String field, Object value, String op) {
        this.field = field;
        this.value = value;
        this.op = op;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getOp() {
        return op;
    }

    public void setOp(String op) {
        this.op = op;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilterRule that = (FilterRule) o;

        if (field != null ? !field.equals(that.field) : that.field != null) return false;
        if (op != null ? !op.equals(that.op) : that.op != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (op != null ? op.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "FilterRule [field=" + field + ", value=" + value + ", op=" + op
                + ", type=" + type + "]";
    }
}
