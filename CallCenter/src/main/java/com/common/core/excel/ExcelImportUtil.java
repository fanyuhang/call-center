package com.common.core.excel;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.digester3.Digester;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelImportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelImportUtil.class);

    private static Tables tables;

    public static void init(String fileName) {
        InputStream inputXML = null;
        try {
            if (fileName == null) {
                inputXML = new BufferedInputStream(Tables.class.getResourceAsStream("importExcel.xml"));
            } else {
                inputXML = new BufferedInputStream(new FileInputStream(fileName));
            }

            Digester digester = new Digester();
            digester.setValidating(false);
            digester.addObjectCreate("tables", Tables.class);
            digester.addObjectCreate("tables/table", Table.class);
            digester.addSetProperties("tables/table", "class", "className");
            digester.addObjectCreate("tables/table/sql", Sql.class);
            digester.addSetProperties("tables/table/sql");
            digester.addCallMethod("tables/table/sql", "setValue", 1);
            digester.addCallParam("tables/table/sql", 0);
            digester.addSetNext("tables/table/sql", "setSql");
            digester.addObjectCreate("tables/table/column", Column.class);
            digester.addSetProperties("tables/table/column");
            digester.addObjectCreate("tables/table/column/mapping", Mapping.class);
            digester.addSetProperties("tables/table/column/mapping");
            digester.addSetNext("tables/table/column/mapping", "addMapping");
            digester.addSetNext("tables/table/column", "addColumn");
            digester.addSetNext("tables/table", "addTableBean");
            tables = (Tables) digester.parse(inputXML);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("init excel import util failure, {} ", e.getMessage());
        } finally {
            if (inputXML != null) {
                try {
                    inputXML.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static <T> List<T> excelImport(Class<T> objectClass, String fileName) throws Exception {
        return excelImport(objectClass, fileName, 0, null);
    }

    public static <T> List<T> excelImport(Class<T> objectClass, String fileName, Integer sheetIdex) throws Exception {
        return excelImport(objectClass, fileName, sheetIdex, null);
    }

    public static <T, K> List<T> excelImport(Class<T> objectClass, String fileName, List<K> primaryKeySet) throws Exception {
        return excelImport(objectClass, fileName, 0, primaryKeySet);
    }

    public static <T, K> List<T> excelImport(Class<T> objectClass, String fileName, Integer sheetIdex, List<K> primaryKeySet) throws Exception {
        if (tables == null) {
            init(null);
        }
        List<T> objects = new ArrayList<T>();
        List<K> keySet = new ArrayList<K>();
        if (primaryKeySet != null) {
            keySet.addAll(primaryKeySet);
        }
        Table table = tables.getTableMap().get(objectClass);
        if (table == null) {
            return objects;
        }
        List<Column> columns = table.getColumns();
        if (columns == null || columns.size() == 0) {
            return objects;
        }
        HSSFWorkbook wb = null;
        try {
            wb = new HSSFWorkbook(new FileInputStream(fileName));
            int index = 0;

            if (sheetIdex != null) {
                index = sheetIdex;
            }

            if (table.getSheetIdx() != null) {
                index = table.getSheetIdx();
            }
            HSSFSheet sheet = wb.getSheetAt(index);
            int rows = sheet.getPhysicalNumberOfRows();
            Object object = null;
            HSSFRow row = null;
            HSSFCell cell = null;
            K primaryKey = null;
            for (int r = table.getStartRow(); r < rows; r++) {
                row = sheet.getRow(r);
                object = table.getClassName().newInstance();
                for (Column column : columns) {
                    cell = row.getCell(column.getCol());
                    if (cell != null) {
                        column.populateBean(cell, object);
                    }
                }

                if (table.getPrimaryKey() != null && table.getPrimaryKey().length() > 0) {
                    primaryKey = (K) PropertyUtils.getProperty(object, table.getPrimaryKey());
                    if (primaryKey == null || (primaryKey + "").trim().isEmpty()) {
                        continue;
                    }
                    if (keySet.contains(primaryKey)) {
                        continue;
                    }
                    keySet.add(primaryKey);
                }
                objects.add((T) object);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("import excel failure, {} ", e.getMessage());
            throw e;
        }
        return objects;
    }
}
