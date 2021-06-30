package it.unipi.dii.lsdb.Mehravar.Model;

import java.util.Date;

public class Advert {
   private String carID;

    public Advert(String carID, String postingDate, String phone) {
        this.carID = carID;
        this.postingDate = postingDate;
        this.phone = phone;
    }

    public String getCarID() {
        return carID;
    }

    public void setCarID(String carID) {
        this.carID = carID;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String postingDate;
   private String phone;

}
