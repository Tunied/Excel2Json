package procress.cell;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.CellType;
import utils.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class AtCellProcess extends CellProcessBasic {


    @Override
    public void StartProcess(HSSFCell _topKeyCell, HSSFCell _argCell, HSSFCell _needProcessCell, HashMap _saveToData) {

        ArrayList processResult = null;
        if (_needProcessCell == null) {
            return;
        }
        String valueStr = GetStringValueFromCell(_needProcessCell);
        if (valueStr == null) {
            return;
        }
        String[] valuesArray = valueStr.split(";");

        if (_argCell != null && _argCell.getCellTypeEnum() != CellType.BLANK) {
            String keyStr = GetStringValueFromCell(_argCell);
            if (keyStr == null) {
                return;
            }
            String[] keysArray = keyStr.split(";");

            processResult = DoProcessWithArgCell(keysArray, valuesArray);
        } else {
            processResult = DoProcessWithoutArgCell(valuesArray);
        }

        if (processResult != null) {
            String key = _topKeyCell.getStringCellValue();
            key = key.substring(1);//去除开头符号
            _saveToData.put(key, processResult);
        }
    }

    private ArrayList DoProcessWithArgCell(String[] _keys, String[] _values) {
        ArrayList result = new ArrayList();
        for (int i = 0; i < _values.length; i++) {
            HashMap subData = new HashMap();
            String[] subValues = _values[i].split(",");
            for (int j = 0; j < subValues.length; j++) {
                if (_keys.length > j) {
                    String subKey = _keys[j];
                    String subValueStr = subValues[j];
                    subData.put(subKey, StringUtils.GetValueWithTypeFromString(subValueStr));
                }
            }
            result.add(subData);
        }
        return result;
    }


    private ArrayList DoProcessWithoutArgCell(String[] _values) {
        ArrayList result = new ArrayList();
        for (int i = 0; i < _values.length; i++) {
            result.add(StringUtils.GetValueWithTypeFromString(_values[i]));
        }
        return result;
    }

    private String GetStringValueFromCell(HSSFCell _cell) {
        if (_cell.getCellTypeEnum() == CellType.STRING ||
                (_cell.getCellTypeEnum() == CellType.FORMULA &&
                        _cell.getCachedFormulaResultTypeEnum() == CellType.STRING)) {
            return _cell.getStringCellValue();
        } else {
//            System.out.println("Warning: AtCellProcess->cell type not string or formula with string result  [INFO] : " +
//                    " row index is: " + (_cell.getRowIndex() + 1) +
//                    " value is : " + _cell.toString());
            return null;
        }
    }

}
