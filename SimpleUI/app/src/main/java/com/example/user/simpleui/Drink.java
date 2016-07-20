package com.example.user.simpleui;

import android.media.Image;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by user on 2016/7/14.
 */

@ParseClassName("Drink")
public class Drink extends ParseObject{
    int imageId;

    public ParseFile getImage(){return getParseFile("image");}


    public void setName(String name) {
        put("Name", name);
    }

    public String getName() {
        return getString("Name");
    }

    public void setmPrice(int mPrice) {
        put("mPrice", mPrice);
    }

    public int getmPrice() {
        return getInt("mPrice");
    }

    public void setlPrice(int lPrice) {
        put("lPrice", lPrice);
    }

    public int getlPrice() {
        return getInt("lPrice");
    }

    public JSONObject getJsonObject()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("Name", getName());
            jsonObject.put("mPrice", getmPrice());
            jsonObject.put("lPrice", getlPrice());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
    public static Drink newInstanceWithData(String data)
    {
        Drink drink = new Drink();
        try {
            JSONObject jsonObject = new JSONObject(data);
            drink.setName(jsonObject.getString("Name"));
            drink.setmPrice(jsonObject.getInt("mPrice"));
            drink.setlPrice(jsonObject.getInt("lPrice"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return drink;
    }
    public static ParseQuery<Drink> getQuery(){return ParseQuery.getQuery(Drink.class);}

    public static void syncDrinksFromRemote(final FindCallback<Drink> callback)
    {
        Drink.getQuery().findInBackground(new FindCallback<Drink>() {
            @Override
            public void done(final List<Drink> objects, ParseException e) {
                if(e == null)
                {
                    Drink.unpinAllInBackground("Drink", new DeleteCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e == null)
                            {
                                Drink.pinAllInBackground("Drink", objects);
                            }
                        }
                    });
                    callback.done(objects, e);
                }
                else
                {
                    Drink.getQuery().fromLocalDatastore().findInBackground(callback);
                }
            }
        });
    }
}
