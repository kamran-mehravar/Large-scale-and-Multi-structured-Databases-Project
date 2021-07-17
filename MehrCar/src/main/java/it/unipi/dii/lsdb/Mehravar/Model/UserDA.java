package it.unipi.dii.lsdb.Mehravar.Model;

import it.unipi.dii.lsdb.Mehravar.Main;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.function.Consumer;

public class UserDA {
    public void insertUser(User user) {
        try {
            MongoCollection<Document> table = Main.db.getCollection("users");
            Document newUser = new Document("name", user.getName());
            newUser.append("surname", user.getSurname());
            newUser.append("email", user.getEmail());
            newUser.append("username", user.getUsername());
            newUser.append("password", user.getPassword());
            newUser.append("phone", user.getPhone());
            newUser.append("gender", user.getGender());
            newUser.append("role", user.getRole());
            table.insertOne(newUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User selectUserByPhone(String phone) {
        MongoCollection<Document> collection = Main.db.getCollection("users");
        Document query = new Document();
        query.append("phone", phone);
        Document document = collection.find(query).first();
        User user = new User();
        user.set_id(document.getObjectId("_id"));
        user.setName(document.getString("name"));
        user.setSurname(document.getString("surname"));
        user.setGender(document.getString("gender"));
        user.setEmail(document.getString("email"));
        user.setPhone(document.getString("phone"));
        user.setRole(document.getString("role"));
        user.setUsername(document.getString("username"));
        user.setPassword(document.getString("password"));

        return user;
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

    public Document convertUserToDocument(User user) {
        Document document = new Document();
        document.append("_id", user.get_id());
        document.append("name", user.getName());
        document.append("surname", user.getSurname());
        document.append("username", user.getUsername());
        document.append("password", user.getPassword());
        document.append("phone", user.getPhone());
        document.append("email", user.getEmail());

        return document;
    }
}

