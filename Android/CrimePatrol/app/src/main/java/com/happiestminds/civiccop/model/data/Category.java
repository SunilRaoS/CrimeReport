package com.happiestminds.civiccop.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class Category {

    @SerializedName("Items")
    @Expose
    private List<Item> Items = new ArrayList<Item>();

    /**
     * No args constructor for use in serialization
     */
    public Category() {
    }

    /**
     * @param Items
     */
    public Category(List<Item> Items) {
        this.Items = Items;
    }

    /**
     * @return The Items
     */
    public List<Item> getItems() {
        return Items;
    }

    /**
     * @param Items The Items
     */
    public void setItems(List<Item> Items) {
        this.Items = Items;
    }


}