package com.common.core.excel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-3-22
 * Time: 下午11:10
 * To change this template use File | Settings | File Templates.
 */
public class Table {

    private Class className;

    private String primaryKey;

    private Integer startRow;

    private Integer sheetIdx;

    private Sql sql;

    private List<Column> columns = new ArrayList<Column>();

    public Table() {

    }

    public Table(String className) throws ClassNotFoundException {
        this.className = Class.forName(className);
    }

    public Table(String className, List<Column> columns) throws ClassNotFoundException {
        this(className);
        this.columns = columns;
    }

    public void addColumn(Column column) {
        this.columns.add(column);
    }

    public List<Column> getColumns() {
        return this.columns;
    }

    public Class getClassName() {
        return className;
    }

    public void setClassName(Class className) {
        this.className = className;
    }

    public Sql getSql() {
        return sql;
    }

    public void setSql(Sql sql) {
        this.sql = sql;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public String getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public Integer getSheetIdx() {
        return sheetIdx;
    }

    public void setSheetIdx(Integer sheetIdx) {
        this.sheetIdx = sheetIdx;
    }
}
