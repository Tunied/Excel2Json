package procress.sheet;

import data.EntityInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;

import java.util.HashMap;

public class TanHaoSheetProcess extends MeiJinSheetProcess {

    @Override
    public void StartProcess(HSSFSheet _sheet, String _fileName) {
        int currentValueCellIndex = 3;
        while (true) {
            HSSFCell valueCell = _sheet.getRow(0).getCell(currentValueCellIndex);
            if (valueCell != null &&
                    valueCell.getCellTypeEnum() != CellType.BLANK &&
                    !valueCell.getStringCellValue().startsWith("#")) {
                HashMap sheetInfoHashMap = DoProcessEachLie(_sheet, _fileName, currentValueCellIndex);

                if (!sheetInfoHashMap.isEmpty()) {
                    EntityInfo entityInfo = GetOrCreateEntityInfoByName(_fileName + "_" + valueCell.getStringCellValue());
                    entityInfo.entityMap.put(_fileName, sheetInfoHashMap);
                }
                currentValueCellIndex++;
            } else {
                break;
            }
        }
    }
}