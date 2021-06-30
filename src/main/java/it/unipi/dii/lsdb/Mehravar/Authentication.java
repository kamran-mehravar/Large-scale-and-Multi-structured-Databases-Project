package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public class Authentication {

    public void login(String phone, String password) {
        if (phone.equals("") || password.equals("")) {
            System.out.println("Wrong Phone number Or Password");
        } else {
            try {
                MongoCollection<Document> collection = Main.db.getCollection("users");
                Document document = collection.find(Filters.eq("phone", phone)).first();
                if (document!=null && document.getString("password").equals(password)) {
                    if (document.getString("role").equals("user")) {
                        UserMenu userMenu = new UserMenu();
                        userMenu.userScopeMenu(phone);
                    } else {
                        AdminMenu adminMenu = new AdminMenu();
                        adminMenu.adminScopeMenu();
                    }
                } else {
                    System.out.println("Login failed");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}