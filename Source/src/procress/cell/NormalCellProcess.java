package procress.cell;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.HashMap;

public class NormalCellProcess extends CellProcessBasic
{

    @Override
    public void StartProcess(HSSFCell _topKeyCell, HSSFCell _argCell, HSSFCell _needProcessCell, HashMap _saveToData)
    {

        if (_needProcessCell != null)
        {
            String key = _topKeyCell.getStringCellValue();
            CellType cellType = _needProcessCell.getCellType();
            if (cellType != CellType.FORMULA)
            {
                SaveCellData(key, _needProcessCell, cellType, _saveToData);
            } else
            {
                SaveCellData(key, _needProcessCell, _needProcessCell.getCachedFormulaResultTypeEnum(), _saveToData);
            }
        }
    }

    private void SaveCellData(String _key, Cell _needProcessCell, CellType _cellType, HashMap _saveToData)
    {
        switch (_cellType)
        {
            case NUMERIC:
                Double doubleValue = _needProcessCell.getNumericCellValue();
                // 判断是写小数还是整数
                if (doubleValue.intValue() == doubleValue)
                {
                    _saveToData.put(_key, doubleValue.intValue());
                } else
                {
                    _saveToData.put(_key, doubleValue);
                }
                break;
            case STRING:
                _saveToData.put(_key, _needProcessCell.getStringCellValue());
                break;
            case BOOLEAN:
                _saveToData.put(_key, _needProcessCell.getBooleanCellValue());
                break;
            case BLANK:
                //处理合并的区域,取得合并区域的首Cell,如果改Cell不是Blank则递归回去去取值(防止合并了Cell,但是未设置任何值)
                CellRangeAddress rangeAddress = GetMergedRegionForCell(_needProcessCell);
                if (rangeAddress != null)
                {
                    int firstRow = rangeAddress.getFirstRow();
                    int firstCol = rangeAddress.getFirstColumn();
                    Sheet s = _needProcessCell.getRow().getSheet();
                    Cell firstCell = s.getRow(firstRow).getCell(firstCol);
                    if (firstCell.getCellType() != CellType.BLANK)
                    {
                        SaveCellData(_key, firstCell, firstCell.getCellType(), _saveToData);
                    }
                }
                break;
            default:
                System.err.println("NormalCellProcess->unknown cell type   [INFO]: " + "top cell is: " + _key + " row index is: " + (_needProcessCell.getRowIndex() + 1) + " value is : " + _needProcessCell.toString());
                break;
        }
    }

    private CellRangeAddress GetMergedRegionForCell(Cell c)
    {
        Sheet s = c.getRow().getSheet();
        for (CellRangeAddress mergedRegion : s.getMergedRegions())
        {
            if (mergedRegion.isInRange(c.getRowIndex(), c.getColumnIndex()))
            {
                return mergedRegion;
            }
        }
        return null;
    }

}
