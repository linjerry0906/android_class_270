package com.example.user.simpleui;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by user on 2016/7/13.
 */

@ParseClassName("Order")
public class Order extends ParseObject{

    public String getNote()
    {
        String note =  getString("note");
        if(note == null)
        {
            return "";
        }
        return note;
    }
    public void setNote(String note)
    {
        put("note", note);
    }

    public void setMenuResults(String menuResults) {
        put("menuResults", menuResults);
    }

    public String getMenuResults() {
        String menuResults =  getString("menuResults");
        if(menuResults == null)
        {
            return "";
        }
        return menuResults;
    }

    public void setStoreInfo(String storeInfo) {
        put("storeInfo", storeInfo);
    }

    public String getStoreInfo() {
        String storeInfo = getString("storeInfo");
        if(storeInfo == null)
        {
            return "";
        }
        return storeInfo;
    }

    public String toData()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("storeInfo", getStoreInfo());
            jsonObject.put("note", getNote());
            jsonObject.put("menuResults", getMenuResults());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
    public static Order newInstanceWithData(String data)
    {
        try {
            JSONObject jsonObject = new JSONObject(data);
            Order order = new Order();
            order.setNote(jsonObject.getString("note"));
            order.setMenuResults(jsonObject.getString("menuResults"));
            order.setStoreInfo(jsonObject.getString("storeInfo"));
            return order;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int totalNumber ()
    {
        if(getMenuResults() == null || getMenuResults().equals(""))
        {
            return 0;
        }

        try {
            JSONArray jsonArray = new JSONArray(getMenuResults());
            int totalNumber = 0;
            for(int i =0; i < jsonArray.length(); i++)
            {
                String data = jsonArray.getString(i);
                DrinkOrder drinkOrder = DrinkOrder.newInstanceWithData(data);
                totalNumber += drinkOrder.lNumber + drinkOrder.mNumber;
            }
            return totalNumber;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void getOrdersFromRemote(FindCallback<Order> callback)
    {
        getQuery().findInBackground(callback);
    }

    public static ParseQuery<Order> getQuery()
    {
        return ParseQuery.getQuery(Order.class);
    }
}
