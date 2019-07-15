import data.EntityInfo;
import export.JsonExport;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import procress.ProcessSharedData;
import procress.sheet.SheetProcessBasic;
import procress.sheet.SheetProcessFactory;
import utils.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ExcelToJson {


    public void execute(String _excelFilePath, String _outputJsonPath) {

        try {
            //==============
            //==Step1 读取文件
            //==============
            File file = new File(_excelFilePath);
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
            HSSFWorkbook excelFile = new HSSFWorkbook(fs);
            String fileName = FileUtils.GetFileNameWithoutPrefix(file.getName());

            System.out.println("Start process file :" + fileName + "\n");

            //==============
            //==Step2 分析Excel
            //==============
            ProcessSharedData sharedData = new ProcessSharedData();
            int sheetNum = excelFile.getNumberOfSheets();
            for (int i = 0; i < sheetNum; i++) {
                HSSFSheet sheet = excelFile.getSheetAt(i);
                SheetProcessBasic sheetProcess = SheetProcessFactory.GetSheetProcessBySheetValue(sheet.getSheetName());
                if (sheetProcess != null) {
                    sheetProcess.SetSharedData(sharedData);
                    sheetProcess.StartProcess(sheet, fileName);
                } else {
                    System.out.println("Ignore sheet :" + sheet.getSheetName() + "\n");
                }
            }

            //==============
            //==Step3 导出JSON
            //==============
            if (!sharedData.allEntityInfoList.isEmpty()) {
                for (EntityInfo entityInfo : sharedData.allEntityInfoList) {
                    JsonExport.Export(entityInfo, _outputJsonPath);
                }
                System.out.println("Done , Enjoy \n\nPowered by Eran");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
