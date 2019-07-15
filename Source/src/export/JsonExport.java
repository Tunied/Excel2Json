package export;

import com.google.gson.Gson;
import data.EntityInfo;
import utils.FileUtils;

import java.io.File;

public class JsonExport {

    public static void Export(EntityInfo _entityInfo, String _exportPath) {
        Gson gson = new Gson();
        String json = gson.toJson(_entityInfo.entityMap);
        FileUtils.writeStringToFile(json, _exportPath + File.separator + _entityInfo.fileName + ".json");
        System.out.println("Save file to : " + _exportPath + File.separator + _entityInfo.fileName + ".json" + "\n");
    }

}
