package com.perrygarg.khanapeena.foodlisting.model;

import com.google.gson.annotations.Expose;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Shubham Gupta on 17-10-2015.
 */
public class Items implements Serializable {

    @Expose
    String id;

    String price;

    String desc;

    String image;

    String name;

    @Expose
    int qty;

    public Items(JSONObject jsonObject) {
        try {
            id = jsonObject.getString("id");
            price = jsonObject.getString("price");
            desc = jsonObject.getString("desc");
            image = jsonObject.getString("image");
            name = jsonObject.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
