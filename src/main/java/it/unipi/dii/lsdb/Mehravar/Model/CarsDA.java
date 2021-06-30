package it.unipi.dii.lsdb.Mehravar.Model;

import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.function.Consumer;
import static com.mongodb.client.model.Filters.eq;

public class CarsDA {
    public String insertCars(CarsTO carsTO) {
        String objectID = null;
        try {
            MongoCollection<Document> table = Main.db.getCollection("cars");
            Document newCar = new Document("model", carsTO.getModel());
            newCar.append("region", carsTO.getRegion());
            newCar.append("price", carsTO.getPrice());
            newCar.append("year", carsTO.getYear());
            newCar.append("manufacturer", carsTO.getManufacturer());
            newCar.append("fuel", carsTO.getFuel());
            newCar.append("transmission", carsTO.getTransmission());
            newCar.append("type", carsTO.getType());
            newCar.append("paint_color", carsTO.getPaint_color());
            newCar.append("description", carsTO.getDescription());
            newCar.append("posting_date", carsTO.getPosting_date());
            newCar.append("phone", carsTO.getPhone());
            table.insertOne(newCar);
            objectID = newCar.get("_id").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return objectID;
    }

    public void selectCarsByPhone(String phone) {
        try {
            MongoCollection<Document> collection = Main.db.getCollection("cars");
            Document query = new Document();
            query.append("phone", phone);
            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println("::::::::Car Details::::::::");
                    System.out.println("Region: " + document.getString("region"));
                    System.out.println("Price: " + document.getString("price"));
                    System.out.println("Year: " + document.getString("year"));
                    System.out.println("Manufacturer: " + document.getString("manufacturer"));
                    System.out.println("Model: " + document.getString("model"));
                    System.out.println("Fuel: " + document.getString("fuel"));
                    System.out.println("Transmission: " + document.getString("transmission"));
                    System.out.println("Type: " + document.getString("type"));
                    System.out.println("Paint color: " + document.getString("paint_color"));
                    System.out.println("Description: " + document.getString("description"));
                    System.out.println("Posting Date: " + document.getString("posting_date"));
                    System.out.println("Phone: " + document.getString("phone"));
                    ObjectId carId = document.getObjectId("_id");
                    System.out.println("Car ID: "+carId.toString());

                }
            };
            collection.find(query).forEach(processBlock);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public void selectCarsForUser(String phone) {
        try {
            UserDA userDA = new UserDA();
            userDA.selectUsersByPhone(phone);
        } catch (MongoException e) {
            e.printStackTrace();
        }
        try {
            selectCarsByPhone(phone);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public String selectCarByID(String ID){
        String phone = null;
        MongoCollection<Document> myCollection = Main.db.getCollection("cars");
        Document document = myCollection.find(eq("_id", new ObjectId(ID))).first();
        if (document == null) {
            //Document does not exist
        } else {
            phone =  (String) document.get("phone");
        }
        return phone;
    }

    public void deleteCarByID(String ID){
        MongoCollection<Document> cars = Main.db.getCollection("cars");
        DeleteResult result = cars.deleteOne(new Document("_id", new ObjectId(ID)));
        if (result.getDeletedCount()==0) {
            System.out.println("Deleting failed, please try again or check your input Car ID");
        }else {
            System.out.println("Advert deleted Succesfully");
        }
    }
    public boolean updateCarByID(String ID, String paintColor, String description) {
        MongoCollection<Document> myCollection = Main.db.getCollection("cars");
        var res = myCollection.find(new Document().append("_id", new ObjectId(ID))).first();

        myCollection.findOneAndUpdate(
                new Document().append("_id", new ObjectId(ID)),
                new Document().append("$set",
                        new Document().append(paintColor != null ? "paint_color" : "description", paintColor != null ? paintColor : description)
                )
        );

        return true;
    }
}
