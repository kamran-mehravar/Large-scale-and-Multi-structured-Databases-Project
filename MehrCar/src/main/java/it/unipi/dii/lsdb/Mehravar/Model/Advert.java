package it.unipi.dii.lsdb.Mehravar.Model;

import org.bson.Document;

public class Advert {
    private Document car;
    private String postingDate;
    private Document user;

    public Advert(Document car, String postingDate, Document user) {
        this.car = car;
        this.postingDate = postingDate;
        this.user = user;
    }

    public Document getCar() {
        return car;
    }

    public void setCar(Document car) {
        this.car = car;
    }

    public String getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }

    public Document getUserTO() {
        return user;
    }

    public void setUserTO(Document user) {
        this.user = user;
    }
}
