package com.happiestminds.civiccop.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

//@Generated("org.jsonschema2pojo")
public class Item {

    @SerializedName("Cat")
    @Expose
    private String Cat;
    @SerializedName("SubCat")
    @Expose
    private List<String> SubCat = new ArrayList<String>();

    /**
     * No args constructor for use in serialization
     */
    public Item() {
    }

    /**
     * @param Cat
     * @param SubCat
     */
    public Item(String Cat, List<String> SubCat) {
        this.Cat = Cat;
        this.SubCat = SubCat;
    }

    /**
     * @return The Cat
     */
    public String getCat() {
        return Cat;
    }

    /**
     * @param Cat The Cat
     */
    public void setCat(String Cat) {
        this.Cat = Cat;
    }

    /**
     * @return The SubCat
     */
    public List<String> getSubCat() {
        return SubCat;
    }

    /**
     * @param SubCat The SubCat
     */
    public void setSubCat(List<String> SubCat) {
        this.SubCat = SubCat;
    }


}