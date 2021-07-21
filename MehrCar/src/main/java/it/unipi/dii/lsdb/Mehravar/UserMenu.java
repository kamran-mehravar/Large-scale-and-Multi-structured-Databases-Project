package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.client.MongoCollection;
import it.unipi.dii.lsdb.Mehravar.Model.*;
import com.mongodb.MongoException;

import java.util.function.Consumer;

import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserMenu {
    public static String Read() {
        String str = "";
        try {
            str = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void userScopeMenu(String phone) {
        userStartMenu();

        boolean condition = true;
        while (condition) {
            switch (UserMenu.Read().toUpperCase()) {
                case "A":
                    AdvertDA advertDA = new AdvertDA();
                    advertDA.selectAdvertByPhone(phone);
                    userStartMenu();
                    break;
                case "B":
                    insertNewAdvert(phone);
                    userStartMenu();
                    break;
                case "C":
                    searchInterface();
                    userStartMenu();
                    break;
                case "D":
                    deleteAdvertInterface(phone);
                    userStartMenu();
                    break;
                case "E":
                    updateInterface();
                    userStartMenu();
                    break;
                case "EXIT":
                    condition = false;
                    System.exit(0);
            }
        }
    }
    
    public static void userStartMenu() {
        System.out.println(":::Welcome to User Dashboard!:::");
        System.out.println("select one option: ");
        System.out.println("A: see your adverts");
        System.out.println("B: Add new Advert");
        System.out.println("C: search for adverts");
        System.out.println("D: Delete an Advert");
        System.out.println("E: Update a car");
        System.out.println("Type EXIT for exit!");
    }

    public void selectCarByPhone(String phone) {
        CarDA carDA = new CarDA();
        carDA.selectCarsByPhone(phone);
    }

    public Car carInterface() {
        Car car = new Car();
        System.out.println("insert Car model:");
        car.setModel(UserMenu.Read());
        System.out.println("insert Car's Manufacturer:");
        car.setManufacturer(UserMenu.Read());
        System.out.println("insert Region:");
        car.setRegion(UserMenu.Read());
        System.out.println("insert Price:");
        car.setPrice(Integer.parseInt(UserMenu.Read()));
        System.out.println("insert Model(4 digit year):");
        car.setYear(UserMenu.Read());
        System.out.println("insert type of fuel:");
        car.setFuel(UserMenu.Read());
        System.out.println("insert type of transmission:");
        car.setTransmission(UserMenu.Read());
        System.out.println("insert the Type(Sedan, SUV,...):");
        car.setType(UserMenu.Read());
        System.out.println("insert paint color:");
        car.setPaint_color(UserMenu.Read());
        System.out.println("insert description");
        car.setDescription(UserMenu.Read());


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        car.setPosting_date(dateFormat.format(date));

        return car;
    }

    public Car insertNewCar() {
        Car car = carInterface();
        CarDA carDA = new CarDA();
        String carID = carDA.insertCars(car);
        car.set_id(new ObjectId(carID));

        return car;
    }

    public static String selectCarByID(String ID) {
        CarDA carDA = new CarDA();
        return carDA.selectCarByID(ID);
    }

    public void insertNewAdvert(String phone) {
        try {

            Car car = carInterface();
            UserDA userDA = new UserDA();
            User user = userDA.selectUserByPhone(phone);
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Advert advert = new Advert(new CarDA().convertCarToDocument(car), dateFormat.format(date), new UserDA().convertUserToDocument(user));
            AdvertDA advertDA = new AdvertDA();
            advertDA.insertNewAdvert(advert);
            System.out.println("Advert added");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchInterface() {
        String region = null;
        String manufacturer = null;
        String year = null;
        System.out.println(":: Search for Cars ::");
        System.out.println("Please insert Region: ");
        region = UserMenu.Read();
        System.out.println("Please insert Manufacturer(like Ford,Chevrolet, BMW, ...: ");
        manufacturer = UserMenu.Read();
        System.out.println("Please insert Production Year (4 number digit): ");
        year = UserMenu.Read();

        if (year.equals("") && manufacturer.equals("") && region.equals("")) {
            System.out.println("Search failed! You should enter at least one parameter!");
        } else {
            searchForCars(region, manufacturer, year);
        }
    }

    public void searchForCars(String region, String manufacturer, String year) {
        try {
            MongoCollection<Document> cars = Main.db.getCollection("advert");
            Document query = new Document();

            if (!region.isEmpty() && manufacturer.isEmpty() && year.isEmpty()) {
                query.append("car.region", region);
            } else if (!manufacturer.isEmpty() && region.isEmpty() && year.isEmpty()) {
                query.append("car.manufacturer", manufacturer);
            } else if (!year.isEmpty() && region.isEmpty() && manufacturer.isEmpty()) {
                query.append("car.year", year);
            } else if (!region.isEmpty() && !manufacturer.isEmpty() && year.isEmpty()) {
                query.append("car.region", region);
                query.append("car.manufacturer", manufacturer);
            } else if (!region.isEmpty() && !year.isEmpty() && manufacturer.isEmpty()) {
                query.append("car.region", region);
                query.append("car.year", year);
            } else if (!manufacturer.isEmpty() && !year.isEmpty() && region.isEmpty()) {
                query.append("car.manufacturer", manufacturer);
                query.append("car.year", year);
            } else if (!region.equals("") && !manufacturer.equals("") && !year.equals("")) {
                query.append("car.region", region);
                query.append("car.manufacturer", manufacturer);
                query.append("car.year", year);
            }

            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println("::::::::Advert Details::::::::");
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
                    System.out.println("Phone Number: " + ((Document) document.get("user")).getString("phone"));
                }
            };

            cars.find(query).forEach(processBlock);
        } catch (MongoException e) {
            // handle MongoDB exception
        }
    }

    public void updateInterface() {
        String haveID = null;
        String field = null;
        String advertID = null;
        String newValue = null;
        AdvertDA advertDA = new AdvertDA();

        System.out.println(":: Update a Car Details ::");
        System.out.println("Do you have ID of the Advert? (y/n)");
        haveID = UserMenu.Read().toUpperCase();

        switch (haveID) {
            case "Y":
                System.out.println("Please enter the ID: ");
                advertID = UserMenu.Read();

                System.out.println("Please specify which field you want to update: ");
                System.out.println("A:  Paint Color");
                System.out.println("B:  Description");
                field = UserMenu.Read().toUpperCase();

                switch (field) {
                    case "A":
                        System.out.println("Please enter new value of Paint Color: ");
                        newValue = UserMenu.Read();
                        advertDA.updateAdvertByCarID(advertID, newValue, null);
                        System.out.println("Changes applied successfully!");

                        break;
                    case "B":
                        System.out.println("Please enter new value of Description: ");
                        newValue = UserMenu.Read();
                        advertDA.updateAdvertByCarID(advertID, null, newValue);
                        System.out.println("Changes applied successfully!");
                        break;
                }

                break;
            default:
                System.out.println("Back to main menu and find your car ID for update!");
                break;
        }
    }

    public void deleteAdvertInterface(String phone){
        String haveID = null;
        String advertID = null;
        System.out.println(":: Delete an Advert ::");
        System.out.println("Do you have ID of the advert? (y/n)");
        haveID = UserMenu.Read().toUpperCase();

        switch (haveID) {
            case "Y":
                System.out.println("Please enter ID of the advert you want to delete: ");
                advertID = UserMenu.Read();
                if (isUserEligible(phone,advertID)){
                    AdvertDA advertDA = new AdvertDA();
                    advertDA.deleteAdvertByID(advertID);
                    System.out.println("Your advert DELETED successfully!");
                }else {
                    System.out.println("Wrong Advert ID OR You are not the owner of this advert!");
                }
                break;
            default:
                System.out.println("Back to menu and find your advert ID for delete!");
                break;
        }

    }
    public boolean isUserEligible(String phone, String advertID){
        MongoCollection<Document> cars = Main.db.getCollection("advert");
        Document query = new Document();
        query.append("user.phone",phone);
        query.append("_id",advertID);
        if (query.isEmpty()){
            return false;
        }else return true;
    }
}
