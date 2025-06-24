package utils;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;

public class JsonReader {

    public static String getTestData(String key) throws IOException, ParseException {
        String testDataValue;
        return testDataValue = (String) getJsonData().get(key);
    }

    public static JSONObject getJsonData() throws IOException, ParseException {
        //path of the testData.json file
        String filePath = "./resources/testData/testData.json";
        //pass the path of the file
        File file = new File(filePath);
        //convert json file into String
        String json = FileUtils.readFileToString(file, "UTF-8");
        //parse the String into an object
        Object obj = new JSONParser().parse(json);
        //give json object to the function so that i can return it
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    public static JSONArray getJsonArray(String key) throws IOException, ParseException {
        JSONObject jsonObject = getJsonData();
        JSONArray jsonArray = (JSONArray) jsonObject.get(key);
        return jsonArray;
    }

    public static Object getJsonArrayData(String key, int index) throws IOException, ParseException {
        JSONArray jsonArray = getJsonArray(key);
        return jsonArray.get(index);
    }
}
