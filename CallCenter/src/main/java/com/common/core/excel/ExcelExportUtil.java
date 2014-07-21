package com.common.core.excel;

import org.apache.commons.digester3.Digester;
import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;
import org.hibernate.cache.ehcache.management.impl.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelExportUtil<T> {
    private static final Logger logger = LoggerFactory.getLogger(ExcelExportUtil.class);

    private static Tables tables;

    public static void init(String fileName) {
        InputStream inputXML = null;
        try {
            if (fileName == null) {
                inputXML = new BufferedInputStream(Tables.class.getResourceAsStream("exportExcel.xml"));
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

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFFont boldFont;
    private HSSFDataFormat format;
    private List<T> data;
    private List<FormatType> formatTypes = new ArrayList<FormatType>();

    private Table table;

    public ExcelExportUtil(List<T> data) {

        if (tables == null) {
            init(null);
        }

        workbook = new HSSFWorkbook();
        this.data = data;
        sheet = workbook.createSheet("data");
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
    }

    public String generateFileName(String fileName) {
        return fileName;
    }

    private FormatType getFormatType(Class _class) {
        if (_class == Integer.class) {
            return FormatType.INTEGER;
        } else if (_class == Float.class || _class == Double.class) {
            return FormatType.FLOAT;
        } else if (_class == Timestamp.class || _class == java.sql.Date.class) {
            return FormatType.DATE;
        } else if (_class == BigDecimal.class) {
            return FormatType.MONEY;
        } else {
            return FormatType.TEXT;
        }
    }

    public String generate(String fileName) throws Exception {

        table = tables.getTableMap().get(this.data.get(0).getClass());
        if (table == null) {
            throw new Exception("请检查导出excel配置文件");
        }

        String excelPath = generateFileName(fileName);
        generate(new File(excelPath));
        return excelPath;
    }

    public void generate(OutputStream outputStream) throws Exception {
        try {
            int currentRow = 0;
            HSSFRow row = sheet.createRow(currentRow);
            List<Column> columnList = table.getColumns();
            Column column = null;
            int numCols = columnList.size();
            for (int i = 0; i < numCols; i++) {
                column = columnList.get(i);
                writeCell(row, i, column.getColumnName(), FormatType.TEXT, boldFont);
                if (column.getKeyToLabelMap().size() == 0) {
                    Class _class = this.data.get(0).getClass().getDeclaredField(column.getPropertyName()).getType();
                    formatTypes.add(i, getFormatType(_class));
                }else{
                    formatTypes.add(i, FormatType.TEXT);
                }
            }

            currentRow++;

            // Write report rows
            for (Object obj : this.data) {
                row = sheet.createRow(currentRow++);
                for (int i = 0; i < numCols; i++) {
                    column = columnList.get(i);
                    Object value = BeanUtils.getBeanProperty(obj, column.getPropertyName());
                    if (column.getKeyToLabelMap().size() != 0) {
                    	value = (null != value) ? column.getKeyToLabelMap().get(Integer.parseInt(value.toString())) : "";
                    }
                    writeCell(row, i, value, formatTypes.get(i));
                }
            }

            // Autosize columns
            for (int i = 0; i < numCols; i++) {
                sheet.autoSizeColumn((short) i);
            }

            workbook.write(outputStream);
        } finally {
            outputStream.close();
        }
    }

    public void generate(File file) throws Exception {
        generate(new FileOutputStream(file));
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType) throws NestableException {
        writeCell(row, col, value, formatType, null, null);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType, HSSFFont font) throws NestableException {
        writeCell(row, col, value, formatType, null, font);
    }

    private void writeCell(HSSFRow row, int col, Object value, FormatType formatType,
                           Short bgColor, HSSFFont font) throws NestableException {
        HSSFCell cell = HSSFCellUtil.createCell(row, col, null);
        if (value == null) {
            return;
        }

        if (font != null) {
            HSSFCellStyle style = workbook.createCellStyle();
            style.setFont(font);
            cell.setCellStyle(style);
        }

        switch (formatType) {
            case TEXT:
                cell.setCellValue(value.toString());
                break;
            case INTEGER:
                if (value == null) {
                    value = 0;
                }
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("#,##0"));
                break;
            case FLOAT:
                if (value == null) {
                    value = 0.0;
                }
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                break;
            case DATE:
                cell.setCellValue((Timestamp) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));
                break;
            case MONEY:
                if (value == null) {
                    value = new BigDecimal(0.0);
                }
                cell.setCellValue(((BigDecimal) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT, format.getFormat("#,##0.00"));
                break;
            case PERCENTAGE:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook,
                        CellUtil.DATA_FORMAT, HSSFDataFormat.getBuiltinFormat("0.00%"));
        }

        if (bgColor != null) {
            HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_FOREGROUND_COLOR, bgColor);
            HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.FILL_PATTERN, HSSFCellStyle.SOLID_FOREGROUND);
        }
    }

    public enum FormatType {
        TEXT(0),
        INTEGER(1),
        FLOAT(2),
        DATE(3),
        MONEY(4),
        PERCENTAGE(5);

        private static final Map<Integer, FormatType> typesByValue = new HashMap<Integer, FormatType>();

        static {
            for (FormatType type : FormatType.values()) {
                typesByValue.put(type.value, type);
            }
        }

        private final int value;

        private FormatType(int value) {
            this.value = value;
        }

        public static FormatType forValue(int value) {
            return typesByValue.get(value);
        }
    }
}