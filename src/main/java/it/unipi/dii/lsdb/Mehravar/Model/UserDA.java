package it.unipi.dii.lsdb.Mehravar.Model;

import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.function.Consumer;

public class UserDA {
    public void insertUser(UserTO userTO) {
        try {
            MongoCollection<Document> table = Main.db.getCollection("users");
            Document newUser = new Document("name", userTO.getName());
            newUser.append("surname", userTO.getSurname());
            newUser.append("email", userTO.getEmail());
            newUser.append("username", userTO.getUsername());
            newUser.append("password", userTO.getPassword());
            newUser.append("phone", userTO.getPhone());
            newUser.append("gender", userTO.getGender());
            newUser.append("role", userTO.getRole());
            table.insertOne(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectUsersByPhone(String phone) {
        try {
            MongoCollection<Document> collection = Main.db.getCollection("users");
            Document query = new Document();
            query.append("phone", phone);
            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                        System.out.println("::::::::User Details::::::::");
                        System.out.println("Name: " + document.getString("name"));
                        System.out.println("Surname: " + document.getString("surname"));
                        System.out.println("Gender: " + document.getString("gender"));
                        System.out.println("Email: " + document.getString("email"));
                        System.out.println("Phone: " + document.getString("phone"));
                        System.out.println("Role: " + document.getString("role"));
                        System.out.println("Username: " + document.getString("username"));
                        System.out.println("Password: ********");
                }
            };
            collection.find(query).forEach(processBlock);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}

