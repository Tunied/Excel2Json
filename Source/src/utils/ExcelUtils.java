package utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;

import java.util.HashMap;

public final class ExcelUtils {

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void setHashMapValue(String _key, HSSFCell valueCell, HashMap map) {
        if (valueCell != null) {
            if (valueCell.getCellTypeEnum() == CellType.NUMERIC) {
                Double doubleValue = valueCell.getNumericCellValue();
                // 判断是写小数还是整数
                if (doubleValue.intValue() == doubleValue) {
                    map.put(_key, doubleValue.intValue());
                } else {
                    map.put(_key, doubleValue);
                }
            } else if (valueCell.getCellTypeEnum() == CellType.STRING) {
                map.put(_key, valueCell.getStringCellValue());
            } else if (valueCell.getCellTypeEnum() == CellType.BOOLEAN) {
                map.put(_key, valueCell.getBooleanCellValue());
            } else if (valueCell.getCellTypeEnum() == CellType.BLANK) {
            } else {
                System.err.println("ExcelUtils->unknown cell type " + "top cell is: " + _key + " row is: " + valueCell.getRowIndex() + " value is : " + valueCell.toString());
            }
        }
    }

    public static String getStringValueFromCell(HSSFCell _cell) {
        String key = null;

        if (_cell.getCellTypeEnum() == CellType.NUMERIC) {
            key = String.valueOf(_cell.getNumericCellValue());
        } else if (_cell.getCellTypeEnum() == CellType.STRING) {
            key = _cell.getStringCellValue();
        } else if (_cell.getCellTypeEnum() == CellType.BOOLEAN) {
            key = String.valueOf(_cell.getBooleanCellValue());
        } else {
            System.err.println("ExcelUtils->getStringValueFromCell() unknown cell type, row is: " + _cell.getRowIndex() + " value is : " + _cell.toString());
        }
        return key;
    }


}
