package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static MongoClient mongoClient;
    public static MongoDatabase db;

    public String Read() {
        String str = "";
        try {
            str = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void main(String[] args) throws Exception {

        ConnectionString uriString = new ConnectionString("mongodb://localhost:27017"); //single local instance
        // ConnectionString uriString= new ConnectionString("mongodb://localhost:27018,localhost:27019,localhost:27020"); //clusters

        MongoClientSettings mcs = MongoClientSettings.builder().applyConnectionString(uriString).build();
        mongoClient = MongoClients.create(mcs);

        db = mongoClient.getDatabase("MehrCar");
        db.getCollection("users").createIndex(new Document().append("phone", 1));
        db.getCollection("advert").createIndex(new Document().append("car.price", 1));
        db.getCollection("advert").createIndex(new Document().append("car.model", 1));
        db.getCollection("advert").createIndex(new Document().append("car.manufacturer", 1));
        db.getCollection("advert").createIndex(new Document().append("car.region", 1));
        db.getCollection("advert").createIndex(new Document().append("user.gender", 1));

        Main main = new Main();
        System.out.println("::::::::: login OR Register :::::::::");
        System.out.println(":: Insert L for login OR R for Register as a new user ::");
        String str = main.Read().toUpperCase();
        if (str.equals("L")) {
            main.login();
        } else if (str.equals("R")) {
            main.register();
        } else {
            System.out.println("wrong input!");
        }
    }

    public void login() {
        System.out.println(":: login ::");
        System.out.println("insert phone number: ");
        Main main = new Main();
        String phone = main.Read();
        System.out.println("insert password");
        String password = main.Read();
        Authentication authentication = new Authentication();
        authentication.login(phone, password);
    }

    public void register() {
        System.out.println(":: Register ::");
        System.out.println("Please insert phone number");
        Main main = new Main();
        String phone = main.Read();
        if (checkUserRegisteredBefore(phone)) {
            System.out.println("You are registered before, please login with your phone number and password");
        } else {
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.registerNewUser(phone);
            System.out.println("You are registered successfully! Now please login");
        }

        login();
    }

    public static boolean checkUserRegisteredBefore(String phone) {
        MongoCollection<Document> collection = Main.db.getCollection("users");
        Document document = collection.find(Filters.eq("phone", phone)).first();

        return document != null;
    }
}
