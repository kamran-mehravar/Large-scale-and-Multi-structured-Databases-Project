package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import org.bson.Document;

import javax.print.Doc;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Statistics {
    public void usersStatistics() {
        MongoCollection<Document> users = Main.db.getCollection("users");
        MongoCollection<Document> cars = Main.db.getCollection("cars");
        MongoCollection<Document> advert = Main.db.getCollection("advert");
        ArrayList <String> top3Regions = new ArrayList();
        ArrayList <String> favoriteBrandsForMen = new ArrayList();
        ArrayList <String> favoriteBrandsForWomen = new ArrayList();

        System.out.println("***-------------- Welcome to Statistics Area! --------------***");
        System.out.println(":. Total Users in our Platform: " + users.countDocuments() + ".:");
        System.out.println(":. Total Advert in our Platform: " + advert.countDocuments() + " .:");

        // top 3 regions
        System.out.println("*--------------Top 3 Regions!--------------*");
        advert.aggregate(
                Arrays.asList(
                        Aggregates.group("$car.region", Accumulators.sum("count", 1)),
                        new Document().append("$sort", new Document().append("count", -1)),
                        new Document().append("$limit", 3)
                )
        ).forEach(document ->{ top3Regions.add(document.getString("_id")); System.out.println("Region Name: " + document.getString("_id") +" " +document.getInteger("count"));});

        // average price in top 3 regions
        System.out.println("*--------------Average Price in Top 3 Regions!--------------*");
        for (String r:top3Regions) {
            advert.aggregate(
                    Arrays.asList(
                            Aggregates.match(new Document().append("car.region", r)),
                            Aggregates.group("$car.region", Accumulators.avg("average", "$car.price"))
                    )
            ).forEach(document -> System.out.println("Region: " + document.getString("_id") + " Avg Price: "+ document.getDouble("average")));
        }

        // Favorite brands for men
        System.out.println("*--------------Favorite Brands For Men!--------------*");
        advert.aggregate(
                Arrays.asList(
                        Aggregates.match(new Document().append("user.gender", "M")),
                        Aggregates.group("$car.manufacturer", Accumulators.sum("count", 1)),
                        new Document().append("$sort", new Document().append("count", -1)),
                        new Document().append("$limit", 3)
                )
        ).forEach(document -> {favoriteBrandsForMen.add(document.getString("_id")); System.out.println("Brand Name: "+document.getString("_id") + " Count: " + document.getInteger("count"));});

        // Favorite brands for women
        System.out.println("*--------------Favorite Brands For Women!--------------*");
        advert.aggregate(
                Arrays.asList(
                        Aggregates.match(new Document().append("user.gender", "F")),
                        Aggregates.group("$car.manufacturer", Accumulators.sum("count", 1)),
                        new Document().append("$sort", new Document().append("count", -1)),
                        new Document().append("$limit", 3)
                )
        ).forEach(document -> {favoriteBrandsForWomen.add(document.getString("_id")); System.out.println("Brand Name: "+document.getString("_id") + " Count: " + document.getInteger("count"));});

        // The most expensive car in mens' favorite car
        System.out.println("*--------------The Most Expensive Car in Mens' Favorite Brand!--------------*");
        for (String mb:favoriteBrandsForMen) {
            advert.find(new Document().append("car.manufacturer", mb).append("user.gender", "M")).sort(new Document().append("car.price", -1)).limit(1)
                    .forEach(document -> {System.out.println("Model: "+((Document)document.get("car")).getString("model") + " price: " +((Document)document.get("car")).getInteger("price") );});
        }
        // The most expensive car in womens' favorite car
        System.out.println("*--------------The Most Expensive Car in Women's' Favorite Brand!--------------*");
        for (String wb: favoriteBrandsForWomen) {
            advert.find(new Document().append("car.manufacturer", wb).append("user.gender", "F")).sort(new Document().append("car.price", -1)).limit(1)
                    .forEach(document -> {System.out.println("Model: "+((Document)document.get("car")).getString("model") + " price: " +((Document)document.get("car")).getInteger("price") );});
        }

        //Top 3 Manufacturer
        System.out.println("*--------------Top 3 Manufacturer by submitted cars --------------*");
        cars.aggregate(Arrays.asList(
                Aggregates.group("$manufacturer", Accumulators.sum("count", 1)),
                new Document().append("$sort", new Document().append("count", -1)),
                new Document().append("$limit", 3)
        )).forEach(doc -> System.out.println("Manufacturer: " + doc.getString("_id") + " " + "submitted cars: " + doc.getInteger("count")));

        //5 most popular paint Color
        System.out.println("*-------------- 5 most popular paint Color in our platform --------------*");
        cars.aggregate(Arrays.asList(
                Aggregates.group("$paint_color", Accumulators.sum("count", 1)),
                new Document().append("$sort", new Document().append("count", -1)),
                new Document().append("$limit", 5)
        )).forEach(doc -> System.out.println("Paint Color: " + doc.getString("_id") + " " + " number of cars with this Color: " + doc.getInteger("count")));

        System.out.println("***----------------------***");
    }
}
