package procress.cell;

public class CellProcessFactory {

    private static AtCellProcess atCellProcess = new AtCellProcess();
    private static NormalCellProcess normalCellProcess = new NormalCellProcess();

    public static CellProcessBasic GetCellProcessByTopCellValue(String _topCellValue) {
        if (_topCellValue.startsWith("@")) {
            return atCellProcess;
        } else if (_topCellValue.startsWith("#")) {
            return null;
        } else {
            return normalCellProcess;
        }
    }

}
