package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import java.sql.Array;
import java.util.Arrays;

public class Statistics {
    public void usersStatistics(){
        MongoCollection<Document> users = Main.db.getCollection("users");
        MongoCollection<Document> cars = Main.db.getCollection("cars");
        System.out.println("::: Welcome to Statistics Area! :::");
        System.out.println("..............................................");
        System.out.println("Total Users in our Platform: "+users.countDocuments());
        System.out.println("Total Cars in our Platform: " + cars.countDocuments());
        System.out.println("..............................................");
        System.out.println(":: Top 3 cities by submitted cars ::");
        cars.aggregate(Arrays.asList(
                Aggregates.group("$region", Accumulators.sum("count", 1)),
                new Document().append("$sort", new Document().append("count", -1)),
                new Document().append("$limit", 3)
        )).forEach(doc -> System.out.println("Region: "+doc.getString("_id") + " "+ "submitted cars: "+ doc.getInteger("count")));

        System.out.println("..............................................");

        System.out.println(":: Top 3 Manufacturer by submitted cars ::");
        cars.aggregate(Arrays.asList(
                Aggregates.group("$manufacturer", Accumulators.sum("count", 1)),
                new Document().append("$sort", new Document().append("count", -1)),
                new Document().append("$limit", 3)
        )).forEach(doc -> System.out.println("Manufacturer: "+doc.getString("_id") + " "+ "submitted cars: "+ doc.getInteger("count")));
        System.out.println("..............................................");
        System.out.println(":: 5 most popular paint Color in our platform ::");
        cars.aggregate(Arrays.asList(
                Aggregates.group("$paint_color", Accumulators.sum("count", 1)),
                new Document().append("$sort", new Document().append("count", -1)),
                new Document().append("$limit", 5)
        )).forEach(doc -> System.out.println("Paint Color: "+doc.getString("_id") + " "+ " number of cars with this Color: "+ doc.getInteger("count")));
    }
}
