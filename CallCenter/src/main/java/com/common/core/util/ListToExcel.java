package com.common.core.util;

import org.apache.commons.lang.exception.NestableException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.util.CellUtil;
import org.hibernate.cache.ehcache.management.impl.BeanUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListToExcel<T> {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private HSSFFont boldFont;
    private HSSFDataFormat format;
    private List<T> data;
    private List<FormatType> formatTypes = new ArrayList<FormatType>();
    private List<String> columnNames = new ArrayList<String>();
    private List<String> propertyNames = new ArrayList<String>();

    public ListToExcel(List<T> data, List<String> columnNames, List<String> propertyNames, String sheetName) {
        workbook = new HSSFWorkbook();
        this.data = data;
        sheet = workbook.createSheet(sheetName);
        boldFont = workbook.createFont();
        boldFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        format = workbook.createDataFormat();
        this.columnNames = columnNames;
        this.propertyNames = propertyNames;
    }

    public ListToExcel(List<T> data, String sheetName) {
        this(data, new ArrayList<String>(), new ArrayList<String>(), sheetName);
    }

    public String generateFileName(String fileName) {
        return fileName;
    }

    private FormatType getFormatType(Class _class) {
        if (_class == Integer.class) {
            return FormatType.INTEGER;
        } else if (_class == Float.class || _class == Double.class) {
            return FormatType.FLOAT;
        } else if (_class == Timestamp.class || _class == java.sql.Date.class ) {
            return FormatType.DATE;
        } else if (_class == java.util.Date.class) {
            return FormatType.SIMPLE_DATE;
        } else if (_class == BigDecimal.class) {
            return FormatType.MONEY;
        } else {
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
            int currentRow = 0;
            HSSFRow row = sheet.createRow(currentRow);
            int numCols = this.columnNames.size();
            for (int i = 0; i < numCols; i++) {
                writeCell(row, i, columnNames.get(i), FormatType.TEXT, boldFont);
                if (propertyNames.size() > i) {
                    if (this.data.size() > 0) {
                        Field field = null;
                        try{
                            field = this.data.get(0).getClass().getDeclaredField(propertyNames.get(i));
                        }catch (Exception e){
                            field = this.data.get(0).getClass().getSuperclass().getDeclaredField(propertyNames.get(i));
                        }

                        Class _class = field.getType();
                        formatTypes.add(i, getFormatType(_class));
                    }
                }
            }

            currentRow++;

            // Write report rows
            for (Object obj : this.data) {
                row = sheet.createRow(currentRow++);
                for (int i = 0; i < numCols; i++) {
                    if (propertyNames.size() > i) {
                        Object value = BeanUtils.getBeanProperty(obj, propertyNames.get(i));
                        writeCell(row, i, value, formatTypes.get(i));
                    }
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
            case SIMPLE_DATE:
                cell.setCellValue(timeFormat.format(value));
                break;
            case DATE:
                cell.setCellValue((Timestamp) value);
                HSSFCellUtil.setCellStyleProperty(cell, workbook, CellUtil.DATA_FORMAT,
                        HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd hh:mm:ss"));
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
        PERCENTAGE(5),
        SIMPLE_DATE(6);

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