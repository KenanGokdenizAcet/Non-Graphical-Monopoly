import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;


public class PropertyJsonReader {

    public static ArrayList<String[]> landList = new ArrayList<>(); //[id, name, cost]
    public static ArrayList<String[]> railroadList = new ArrayList<>(); //[id, name, cost]
    public static ArrayList<String[]> companyList = new ArrayList<>(); //[id, name, cost]

    public PropertyJsonReader(){
        JSONParser processor = new JSONParser();
        try (Reader file = new FileReader("property.json")){
            JSONObject jsonfile = (JSONObject) processor.parse(file);
            JSONArray Land = (JSONArray) jsonfile.get("1");
            for(Object i:Land){
                String[] arr = new String[3];

                arr[0] = ((String)((JSONObject)i).get("id"));
                arr[1] = (String)((JSONObject)i).get("name");
                arr[2] = ((String)((JSONObject)i).get("cost"));

                landList.add(arr);


            }
            JSONArray RailRoad = (JSONArray) jsonfile.get("2");
            for(Object i:RailRoad){

                String[] arr = new String[3];

                arr[0] = ((String)((JSONObject)i).get("id"));
                arr[1] = (String)((JSONObject)i).get("name");
                arr[2] = ((String)((JSONObject)i).get("cost"));

                railroadList.add(arr);
            }

            JSONArray Company = (JSONArray) jsonfile.get("3");
            for(Object i:Company){

                String[] arr = new String[3];

                arr[0] = ((String)((JSONObject)i).get("id"));
                arr[1] = (String)((JSONObject)i).get("name");
                arr[2] = ((String)((JSONObject)i).get("cost"));

                companyList.add(arr);
            }

        } catch (IOException e){
            e.printStackTrace();
        } catch (ParseException e){
            e.printStackTrace();
        }
    }


}