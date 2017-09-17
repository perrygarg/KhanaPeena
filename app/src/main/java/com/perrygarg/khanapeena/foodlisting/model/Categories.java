package com.perrygarg.khanapeena.foodlisting.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Shubham Gupta on 17-10-2015.
 */
public class Categories {

    String categories="categories";
    String name;
    String id;
    String nameOfCategories[];
    ArrayList<Items> items;
    public Categories(JSONObject jsonObject){
        try {
             name = jsonObject.getString("name");
            //nameOfCategories = new String[jsonArray.length()];
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            items = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                Items item = new Items(jsonArray.getJSONObject(i));
                items.add(item);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String[] getNameOfCategories() {
        return nameOfCategories;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }
}
