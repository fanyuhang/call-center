package com.common.core.excel;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellReference;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Leonid Vysochyn
 */
public class Column {
    protected final Log log = LogFactory.getLog(getClass());

    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    int row;
    int col;
    String propertyName;
    String cell;
    String type = null;
    String columnName;
    boolean nullAllowed = false;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    private Map<String, Integer> mapping = new HashMap<String, Integer>();

    private Map<Object, String> keyToLabelMap = new HashMap<Object, String>();

    static {
        ReaderConfig.getInstance();
    }

    public Column(int rowNum, short cellNum, String propertyName) {
        this.row = rowNum;
        this.col = cellNum;
        this.propertyName = propertyName;
    }

    public Column(String cell, String propertyName) {
        setCell(cell);
        this.propertyName = propertyName;
    }

    public Column() {
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }


    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
        CellReference cellRef = new CellReference(cell);
        row = cellRef.getRow();
        col = cellRef.getCol();
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isNullAllowed() {
        return nullAllowed;
    }

    public void setNullAllowed(boolean nullAllowed) {
        this.nullAllowed = nullAllowed;
    }

    public void addMapping(Mapping mapping) {
        this.mapping.put(mapping.getText(), mapping.getValue());
        this.keyToLabelMap.put(mapping.getValue(), mapping.getText());
    }

    public Map<String, Integer> getMapping() {
        return mapping;
    }

    public void setMapping(Map<String, Integer> mapping) {
        this.mapping = mapping;
    }

    public Map<Object, String> getKeyToLabelMap() {
        return keyToLabelMap;
    }

    public void setKeyToLabelMap(Map<Object, String> keyToLabelMap) {
        this.keyToLabelMap = keyToLabelMap;
    }

    public void populateBean(Cell cell, Object bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ClassNotFoundException, ParseException {
        Class dataType = getPropertyType(bean);
        String dataString = getCellString(cell);
        Object value = null;
        if ((dataString != null && !dataString.equals("null")) || !nullAllowed) { // set only if null is not allowed!
            if (dataType.equals(java.util.Date.class)) {
                value = df.parse(dataString);
            } else {
                value = ConvertUtils.convert(dataString, dataType);
            }
        }

        PropertyUtils.setProperty(bean, propertyName, value);
    }

    // Added feature to set type by type-attribute in XML
    public Class getPropertyType(Object bean) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
        if (bean == null) {
            return Object.class;
        }
        if (type == null) {
            return PropertyUtils.getPropertyType(bean, propertyName);
        } else {
            return Class.forName(type);
        }
    }

    public String getCellName() {
        CellReference cellRef = new CellReference(row, col, false, false);
        return cellRef.formatAsString();
    }

    public String toString() {
        return propertyName;
    }

    private String getCellString(Cell cell) {
        String dataString = null;

        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    dataString = cell.getRichStringCellValue().getString();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        return df.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
                    }
                    dataString = readNumericCell(cell);
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    dataString = Boolean.toString(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    break;
                case Cell.CELL_TYPE_ERROR:
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    // attempt to read formula cell as numeric cell
                    try {
                        dataString = readNumericCell(cell);
                    } catch (Exception e1) {
                        log.info("Failed to read formula cell as numeric. Next to try as string. Cell=" + cell.toString());
                        try {
                            dataString = cell.getRichStringCellValue().getString();
                            log.info("Successfully read formula cell as string. Value=" + dataString);
                        } catch (Exception e2) {
                            log.warn("Failed to read formula cell as numeric or string. Cell=" + cell.toString());
                        }
                    }

                    break;
                default:
                    break;
            }
        }

        if (this.mapping != null && this.mapping.size() != 0) {
            return mapping.get(dataString.trim()) + "";
        }
        return dataString == null ? null : dataString.trim();
    }

    private String readNumericCell(Cell cell) {
        String dataString = null;
        BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
        dataString = bigDecimal.toString();
        return dataString;
    }
}
