package com.common.core.util;

import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSetToExcel {

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFFont boldFont;
    private HSSFDataFormat format;
    private ResultSet resultSet;
    private List<FormatType> formatTypes = new ArrayList<FormatType>();
    private List<String> columnNames = new ArrayList<String>();

    public ResultSetToExcel(ResultSet resultSet, List<FormatType> formatTypes, List<String> columnNames, String sheetName) {
        workbook = new HSSFWorkbook();
        this.resultSet = resultSet;
        sheet = workbook.createSheet(sheetName);
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
        this.formatTypes = formatTypes;
        this.columnNames = columnNames;
    }

    public ResultSetToExcel(ResultSet resultSet, String sheetName) {
        this(resultSet, new ArrayList<FormatType>(), new ArrayList<String>(), sheetName);
    }

    public String generateFileName(String fileName) {
        return fileName;
    }

    private FormatType getFormatType(Class _class) {
        if (_class == Integer.class || _class == Long.class) {
            return FormatType.INTEGER;
        } else if (_class == Float.class || _class == Double.class) {
            return FormatType.FLOAT;
        } else if (_class == Timestamp.class || _class == java.sql.Date.class) {
//            return FormatType.DATE;
            return FormatType.TEXT;
        } else if (_class == BigDecimal.class){
            return FormatType.MONEY;
        }
        else {
            return FormatType.TEXT;
        }
    }

    public String generate(String fileName) throws Exception {
        String excelPath = generateFileName(fileName);
        generate(new File(excelPath));
        return excelPath;
    }

    public void generate(OutputStream outputStream) throws Exception {
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

            int currentRow = 0;
            HSSFRow row = sheet.createRow(currentRow);
            int numCols = resultSetMetaData.getColumnCount();

            for (int i = 0; i < numCols; i++) {
                String title = resultSetMetaData.getColumnName(i + 1);
                writeCell(row, i, columnNames.size() < (i + 1) ? title : columnNames.get(i), FormatType.TEXT, boldFont);
                if (formatTypes.size() < (i + 1)) {
                    Class _class = Class.forName(resultSetMetaData.getColumnClassName(i + 1));
                    formatTypes.add(i, getFormatType(_class));
                }
            }

            currentRow++;

            // Write report rows
            while (resultSet.next()) {
                row = sheet.createRow(currentRow++);
                for (int i = 0; i < numCols; i++) {
                    Object value = resultSet.getObject(i + 1);
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
                cell.setCellValue(((Number) value).intValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("#,##0"));
                break;
            case FLOAT:
                cell.setCellValue(((Number) value).doubleValue());
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                break;
            case DATE:
                cell.setCellValue((Timestamp) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("m/d/yy hh:mm:ss"));
                break;
            case MONEY:
                cell.setCellValue(((Number) value).doubleValue());
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