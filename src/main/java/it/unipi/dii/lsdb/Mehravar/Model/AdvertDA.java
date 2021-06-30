package it.unipi.dii.lsdb.Mehravar.Model;


import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

public class AdvertDA {
    public void insertNewAdvert(Advert advert) {
        try {
            MongoCollection<Document> collection = Main.db.getCollection("advert");
            Document newAdvert = new Document("car_id", advert.getCarID());
            newAdvert.append("posting_date", advert.getPostingDate());
            newAdvert.append("phone", advert.getPhone());
            collection.insertOne(newAdvert);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteAdvertByCardID(String ID){
        MongoCollection<Document> adverts = Main.db.getCollection("advert");
        DeleteResult result = adverts.deleteOne(new Document("car_id", new ObjectId(ID)));
    }
}
