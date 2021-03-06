package it.unipi.dii.lsdb.Mehravar.Model;

import org.bson.types.ObjectId;

public class Car {
    private ObjectId _id;
    private String model;
    private String manufacturer;
    private String region;
    private int price;
    private String year;
    private String fuel;
    private String transmission;
    private String type;
    private String paint_color;
    private String description;
    private String posting_date;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPaint_color() {
        return paint_color;
    }

    public void setPaint_color(String paint_color) {
        this.paint_color = paint_color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPosting_date() {
        return posting_date;
    }

    public void setPosting_date(String posting_date) {
        this.posting_date = posting_date;
    }

}
