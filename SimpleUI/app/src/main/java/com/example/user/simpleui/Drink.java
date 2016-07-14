package com.example.user.simpleui;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/7/14.
 */
public class Drink {
    String name;
    int mPrice = 0;
    int lPrice = 0;
    int imageId;

    public JSONObject getJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", name);
            jsonObject.put("mPrice", mPrice);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
}
