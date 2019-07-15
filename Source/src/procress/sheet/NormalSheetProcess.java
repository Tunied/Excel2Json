package procress.sheet;

import data.EntityInfo;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.CellType;
import procress.cell.CellProcessBasic;
import procress.cell.CellProcessFactory;

import java.util.ArrayList;
import java.util.HashMap;

public class NormalSheetProcess extends SheetProcessBasic {

    @Override
    public void StartProcess(HSSFSheet _sheet, String _fileName) {

        ArrayList currentSheetInfoArray = new ArrayList();
        //从第三行开始
        //第一行 Row Index=0 用于定义Key
        //第二行 Row Index=1 用于读取参数
        //第三行 Row Index=2 正式的数据
        int currentYIndex = 2;

        // ==============
        // ==读取整个表格
        // ==============
        while (true) {
            if (_sheet.getRow(currentYIndex) != null) {

                //如果每一行的第一个Cell标记了#号,则忽略当前行
                HSSFCell eachLineMarkCell = _sheet.getRow(currentYIndex).getCell(0);
                if (eachLineMarkCell != null &&
                        eachLineMarkCell.getCellType() == CellType.STRING &&
                        eachLineMarkCell.getStringCellValue().startsWith("#")) {
                    currentYIndex++;
                    continue;
                }

                int currentXIndex = 1;
                HashMap currentLineInfoHashMap = new HashMap();


                // ================
                // ==读取一行信息
                // ================
                while (true) {
                    HSSFCell keyCell = _sheet.getRow(0).getCell(currentXIndex);
                    if (keyCell != null) {

                        CellProcessBasic cellProcess = CellProcessFactory.GetCellProcessByTopCellValue(keyCell.getStringCellValue());
                        if (cellProcess != null) {
                            HSSFCell argCell = _sheet.getRow(1).getCell(currentXIndex);
                            HSSFCell valueCell = _sheet.getRow(currentYIndex).getCell(currentXIndex);
                            cellProcess.StartProcess(keyCell, argCell, valueCell, currentLineInfoHashMap);
                        }
                        currentXIndex++;
                    } else {
                        break;
                    }
                }
                if (!currentLineInfoHashMap.isEmpty()) {
                    currentSheetInfoArray.add(currentLineInfoHashMap);
                }

            } else {
                break;
            }
            currentYIndex++;
        }

        // 将当前Sheet的信息 存入以Excel文件名为Key的EntityInfo中
        if (!currentSheetInfoArray.isEmpty()) {
            EntityInfo entityInfo = GetOrCreateEntityInfoByName(_fileName);
            entityInfo.entityMap.put(_sheet.getSheetName(), currentSheetInfoArray);
        }
    }
}
