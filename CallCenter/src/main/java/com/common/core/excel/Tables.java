package com.common.core.excel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Tables {

    private static final Logger logger = LoggerFactory.getLogger(Tables.class);

    public Map<Class, Table> tableMap = new HashMap<Class, Table>();

    public Tables() {
    }

    public Tables(Map<Class, Table> tableMap) {
        this.tableMap = tableMap;
    }

    public void addTableBean(Table table) {
        this.tableMap.put(table.getClassName(), table);
    }

    public void setTableMap(Map<Class, Table> tableMap) {
        this.tableMap = tableMap;
    }

    public Map<Class, Table> getTableMap() {
        return tableMap;
    }
}
