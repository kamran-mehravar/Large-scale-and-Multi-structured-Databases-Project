package it.unipi.dii.lsdb.Mehravar.Model;

import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;

public class CarDA {
    public String insertCars(Car car) {
        String objectID = null;
        try {
            MongoCollection<Document> table = Main.db.getCollection("cars");
            Document newCar = new Document("model", car.getModel());
            newCar.append("region", car.getRegion());
            newCar.append("price", car.getPrice());
            newCar.append("year", car.getYear());
            newCar.append("manufacturer", car.getManufacturer());
            newCar.append("fuel", car.getFuel());
            newCar.append("transmission", car.getTransmission());
            newCar.append("type", car.getType());
            newCar.append("paint_color", car.getPaint_color());
            newCar.append("description", car.getDescription());
            newCar.append("posting_date", car.getPosting_date());
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
            selectCarsByPhone(phone);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public String selectCarByID(String ID) {
        String phone = null;
        MongoCollection<Document> myCollection = Main.db.getCollection("cars");
        Document document = myCollection.find(eq("_id", new ObjectId(ID))).first();

        if (document == null) {
            //Document does not exist
        } else {
            phone = (String) document.get("phone");
        }

        return phone;
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

    public Document convertCarToDocument(Car car) {
        Document document = new Document();
        document.append("_id", car.get_id());
        document.append("model", car.getModel());
        document.append("manufacturer", car.getManufacturer());
        document.append("region", car.getRegion());
        document.append("price", car.getPrice());
        document.append("year", car.getYear());
        document.append("fuel", car.getFuel());
        document.append("transmission", car.getTransmission());
        document.append("type", car.getType());
        document.append("paint_color", car.getPaint_color());
        document.append("description", car.getDescription());
        document.append("posting_date", car.getPosting_date());

        return document;
    }
}
