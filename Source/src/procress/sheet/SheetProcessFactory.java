package procress.sheet;

public class SheetProcessFactory {

    public static SheetProcessBasic GetSheetProcessBySheetValue(String _sheetValue) {
        if (_sheetValue.startsWith("#")) {
            return null;
        } else if (_sheetValue.startsWith("!")) {
            return new TanHaoSheetProcess();
        } else if (_sheetValue.startsWith("$")) {
            return new MeiJinSheetProcess();
        } else {
            return new NormalSheetProcess();
        }
    }
}
