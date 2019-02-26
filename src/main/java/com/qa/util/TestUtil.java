package com.qa.util;

import org.json.JSONObject;
import org.json.JSONArray;

public class TestUtil {
    public JSONObject responseJason;

    public static String getValueByJPath(JSONObject responsejson, String jpath){
            Object obj = responsejson;
            for(String s : jpath.split("/"))
                if(!s.isEmpty())
                    // Checks the Single values
                    if(!(s.contains("[") || s.contains("]")))
                        obj = ((JSONObject) obj).get(s);
                    // Checks the Array values - Data values
                    else if(s.contains("[") || s.contains("]"))
                        obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
            return obj.toString();
        }
    }

