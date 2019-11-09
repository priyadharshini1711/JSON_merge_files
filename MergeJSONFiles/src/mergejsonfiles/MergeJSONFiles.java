/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/*
Output: 

Enter the starting name of the files to be read = 
data
Value from the files = [[{"name":"Alexis Sanchez","club":"Manchester United"},{"name":"Robin van Persie","club":"Feyenoord"}],[{"name":"Nicolas Pepe","club":"Arsenal"}],[{"name":"Gonzalo Higuain","club":"Napoli"},{"name":"Sunil Chettri","club":"Bengaluru FC"}]]
Enter the file size in integer = 
10
Actual Output File Size = 238 bytes
File written successfully
*/

package mergejsonfiles;

import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMap;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringWriter;
import java.lang.reflect.Array;
import static java.nio.file.Files.list;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author admin
 */
public class MergeJSONFiles {

    public static void main(String[] args) throws IOException, ParseException {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the starting name of the files to be read = ");
        String filename = sc.next();

        //reads the files with the common prefix from the current working directory                
        File dir = new File(System.getProperty("user.dir"));
        File[] foundFiles = dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(filename.trim());
            }
        });

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        JSONObject jSONObject = new JSONObject();

        //read json objects from each file
        for (File file : foundFiles) {
            Object obj = parser.parse(new FileReader(file));
            HashMap<String, Object> result;
            result = new ObjectMapper().readValue(file, HashMap.class);
            jsonArray.add(result.get("strikers"));
        }

        //System.out.println(jsonArray.size());
        //System.out.println(jsonArray);

        //converting the JSON array to write to the file
        String objectMapper = new ObjectMapper().writeValueAsString(jsonArray);
        String jsonValue = JSONValue.toJSONString(jsonArray);

        System.out.println("Value from the files = " + jsonValue +"\n");
        
        //reading file size
        System.out.println("Enter the file size in integer = ");
        int size = sc.nextInt();
        
        //writing into a file
        RandomAccessFile f = new RandomAccessFile("output", "rw");
        f.setLength((byte) size);
        f.writeBytes(jsonValue);
        System.out.println("Actual Output File Size = " + f.length() + " bytes");
        System.out.println("File written successfully");
    }
}
