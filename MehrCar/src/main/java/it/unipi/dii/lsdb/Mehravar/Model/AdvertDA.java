package it.unipi.dii.lsdb.Mehravar.Model;

import com.mongodb.MongoException;
import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.function.Consumer;

public class AdvertDA {
    public void insertNewAdvert(Advert advert) {
        try {
            MongoCollection<Document> collection = Main.db.getCollection("advert");
            Document newAdvert = new Document("posting_date", advert.getPostingDate());
            newAdvert.append("car", advert.getCar());
            newAdvert.append("user", advert.getUserTO());
            collection.insertOne(newAdvert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectAdvertByPhone(String phone) {
        MongoCollection<Document> collection = Main.db.getCollection("advert");

        try {
            Document query = new Document();
            query.append("user.phone", phone);
            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println(":: Your adverts ::");
                    System.out.println("Published date: " + document.getString("posting_date"));
                    System.out.println("Region: " + ((Document) document.get("car")).getString("region"));
                    System.out.println("Price: " + ((Document) document.get("car")).getInteger("price"));
                    System.out.println("Year: " + ((Document) document.get("car")).getString("year"));
                    System.out.println("Manufacturer: " + ((Document) document.get("car")).getString("manufacturer"));
                    System.out.println("Model: " + ((Document) document.get("car")).getString("model"));
                    System.out.println("Fuel: " + ((Document) document.get("car")).getString("fuel"));
                    System.out.println("Transmission: " + ((Document) document.get("car")).getString("transmission"));
                    System.out.println("Type: " + ((Document) document.get("car")).getString("type"));
                    System.out.println("Paint color: " + ((Document) document.get("car")).getString("paint_color"));
                    System.out.println("Description: " + ((Document) document.get("car")).getString("description"));
                    //System.out.println("** Car ID **: " + ((Document) document.get("car")).getObjectId("_id"));
                    System.out.println("*** Advert ID ***: " + document.getObjectId("_id"));
                }
            };

            collection.find(query).forEach(processBlock);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public boolean updateAdvertByCarID(String advertID, String paintColor, String description) {
        MongoCollection<Document> myCollection = Main.db.getCollection("advert");

        myCollection.findOneAndUpdate(
                new Document().append("_id", new ObjectId(advertID)),
                new Document().append("$set", new Document().append(paintColor != null ? "car.paint_color" : "car.description", paintColor != null ? paintColor : description))
        );
        return true;
    }
    public void deleteAdvertByID(String advertID){
        MongoCollection<Document> myCollection = Main.db.getCollection("advert");
        myCollection.findOneAndDelete(new Document().append("_id",new ObjectId(advertID)));
    }

}
