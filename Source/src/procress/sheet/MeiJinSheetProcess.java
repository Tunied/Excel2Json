package procress.sheet;

import data.EntityInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import procress.cell.CellProcessBasic;
import procress.cell.CellProcessFactory;

import java.util.HashMap;

public class MeiJinSheetProcess extends SheetProcessBasic {

    @Override
    public void StartProcess(HSSFSheet _sheet, String _fileName) {
        HashMap sheetInfoHashMap = DoProcessEachLie(_sheet, _fileName, 3);
        // 将当前Sheet的信息 存入以Excel文件名为Key的EntityInfo中
        if (!sheetInfoHashMap.isEmpty()) {
            EntityInfo entityInfo = GetOrCreateEntityInfoByName(_fileName);
            entityInfo.entityMap.put(_sheet.getSheetName().substring(1), sheetInfoHashMap);
        }
    }

    protected HashMap DoProcessEachLie(HSSFSheet _sheet, String _fileName, int _valueCellIndex) {

        HashMap sheetInfoHashMap = new HashMap();

        //第一行无意义 仅用于标注
        int currentYIndex = 1;

        // ==============
        // ==读取整个表格
        // ==============
        while (true) {
            if (_sheet.getRow(currentYIndex) != null) {

                //如果每一行的第一个Cell标记了#号,则忽略当前行
                HSSFCell eachLineMarkCell = _sheet.getRow(currentYIndex).getCell(0);
                if (eachLineMarkCell != null && eachLineMarkCell.getStringCellValue().startsWith("#")) {
                    currentYIndex++;
                    continue;
                }

                // ================
                // ==读取一行信息
                //== X = 1 用于定义Key
                //== X = 2  用于定义Arg
                //== X = 3 定义参数
                // ================
                HSSFCell keyCell = _sheet.getRow(currentYIndex).getCell(1);
                if (keyCell != null) {

                    CellProcessBasic cellProcess = CellProcessFactory.GetCellProcessByTopCellValue(keyCell.getStringCellValue());
                    if (cellProcess != null) {
                        HSSFCell argCell = _sheet.getRow(currentYIndex).getCell(2);
                        HSSFCell valueCell = _sheet.getRow(currentYIndex).getCell(_valueCellIndex);
                        cellProcess.StartProcess(keyCell, argCell, valueCell, sheetInfoHashMap);
                    }
                }
                currentYIndex++;
            } else {
                break;
            }
        }

        return sheetInfoHashMap;

    }

}
