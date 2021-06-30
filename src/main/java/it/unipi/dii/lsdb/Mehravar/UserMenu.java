package it.unipi.dii.lsdb.Mehravar;

import com.mongodb.client.MongoCollection;
import it.unipi.dii.lsdb.Mehravar.Model.Advert;
import it.unipi.dii.lsdb.Mehravar.Model.AdvertDA;
import it.unipi.dii.lsdb.Mehravar.Model.CarsDA;
import it.unipi.dii.lsdb.Mehravar.Model.CarsTO;
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
                    selectCarByPhone(phone);
                    userStartMenu();
                    break;
                case "B":
                    insertNewAdvert(phone);
                    userStartMenu();
                case "C":
                    searchInterface();
                    userStartMenu();
                case "D":
                    deleteAdvert(phone);
                    userStartMenu();
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
        System.out.println("A: see your cars advert");
        System.out.println("B: Add new Advertisement");
        System.out.println("C: search for cars");
        System.out.println("D: delete Advert");
        System.out.println("E: Update your Advert");
        System.out.println("Type EXIT for exit!");
    }

    public void selectCarByPhone(String phone) {
        CarsDA carsDA = new CarsDA();
        carsDA.selectCarsByPhone(phone);
    }


    public CarsTO carInterface() throws Exception {
        CarsTO carsTO = new CarsTO();
        System.out.println("insert Car model:");
        carsTO.setModel(UserMenu.Read());
        System.out.println("insert Car's Manufacturer:");
        carsTO.setManufacturer(UserMenu.Read());
        System.out.println("insert Region:");
        carsTO.setRegion(UserMenu.Read());
        System.out.println("insert Price:");
        carsTO.setPrice(UserMenu.Read());
        System.out.println("insert Model(4 digit year):");
        carsTO.setYear(UserMenu.Read());
        System.out.println("insert type of fuel:");
        carsTO.setFuel(UserMenu.Read());
        System.out.println("insert type of transmission:");
        carsTO.setTransmission(UserMenu.Read());
        System.out.println("insert the Type(Sedan, SUV,...):");
        carsTO.setType(UserMenu.Read());
        System.out.println("insert paint color:");
        carsTO.setPaint_color(UserMenu.Read());
        System.out.println("insert phone number");
        carsTO.setPhone(UserMenu.Read());
        System.out.println("insert description");
        carsTO.setDescription(UserMenu.Read());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        carsTO.setPosting_date(dateFormat.format(date));
        return carsTO;
    }


    public String insertNewCar() throws Exception {
        CarsTO carsTO = carInterface();
        CarsDA carsDA = new CarsDA();
        return carsDA.insertCars(carsTO);
    }

    public static String selectCarByID(String ID) {
        CarsDA carsDA = new CarsDA();
        return carsDA.selectCarByID(ID);
    }

    public void insertNewAdvert(String phone) {
        try {
            String carID = insertNewCar();
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            Advert advert = new Advert(carID, dateFormat.format(date), phone);
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
        if (year.equals("") && manufacturer.equals("") && region.equals("")){
            System.out.println("Search failed! You should enter at least one parameter!");
        }else {
            searchForCars(region, manufacturer, year);
        }
    }

    public void searchForCars(String region, String manufacturer, String year) {
        try {
            MongoCollection<Document> cars = Main.db.getCollection("cars");
            Document query = new Document();

            if(!region.isEmpty() && manufacturer.isEmpty() && year.isEmpty()){
                query.append("region", region);
            }else if (!manufacturer.isEmpty() && region.isEmpty() && year.isEmpty()){
                query.append("manufacturer", manufacturer);
            }else if(!year.isEmpty() && region.isEmpty() && manufacturer.isEmpty() ){
                query.append("year", year);
            }else if (!region.isEmpty() && !manufacturer.isEmpty() && year.isEmpty() ){
                query.append("region", region);
                query.append("manufacturer", manufacturer);
            }else if(!region.isEmpty() && !year.isEmpty() && manufacturer.isEmpty()){
                query.append("region", region);
                query.append("year", year);
            }else if(!manufacturer.isEmpty() && !year.isEmpty() && region.isEmpty()){
                query.append("manufacturer", manufacturer);
                query.append("year", year);
            }else if(!region.equals("") && !manufacturer.equals("") && !year.equals("")){
            query.append("region", region);
            query.append("manufacturer", manufacturer);
            query.append("year", year);
            }

            Consumer<Document> processBlock = new Consumer<Document>() {
                @Override
                public void accept(Document document) {
                    System.out.println("::::::::Advert Details::::::::");
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
                    CarsDA carsDA = new CarsDA();
                    ObjectId carId = document.getObjectId("_id");
                    System.out.println("Phone: " + carsDA.selectCarByID(carId.toString()));
                    System.out.println("Car ID: "+carId.toString());
                }
            };
            cars.find(query).forEach(processBlock);
        } catch (MongoException e) {
            // handle MongoDB exception
        }
    }

    public void deleteAdvert(String phone){
        String ID ="";
        System.out.println(":: Delete Your Advert ::");
        System.out.println("Do you know your Car ID? insert Y for yest and N for NO");
        String answer = UserMenu.Read().toUpperCase();
        if (answer.equals("Y")){
            System.out.println("Please insert your Car ID");
            ID = UserMenu.Read();
            if (ID.equals("")){
                System.out.println("Empty input!");
            }else {
                CarsDA carsDA = new CarsDA();
                carsDA.deleteCarByID(ID);
                AdvertDA advertDA = new AdvertDA();
                advertDA.deleteAdvertByCardID(ID);
                System.out.println("Your advert deleted");
            }
        }else {
            System.out.println("Here are your Cars List, please copy the Car ID for deleting: ");
            selectCarByPhone(phone);
            System.out.println("::: Now please insert your Car ID :::");
            ID = UserMenu.Read();
            if (ID.equals("")) {
                System.out.println("Empty input!");
            } else {
                AdvertDA advertDA = new AdvertDA();
                advertDA.deleteAdvertByCardID(ID);
                CarsDA carsDA = new CarsDA();
                carsDA.deleteCarByID(ID);

            }
        }
    }
    public void updateInterface() {
        String haveID = null;
        String field = null;
        String carID = null;
        String newValue = null;
        CarsDA carsDA = new CarsDA();

        System.out.println(":: Update an Advert ::");
        System.out.println("Do you have ID of the car? (y/n)");
        haveID = UserMenu.Read().toUpperCase();

        switch (haveID) {
            case "Y":
                System.out.println("Please enter ID of the car you want to update: ");
                carID = UserMenu.Read();

                System.out.println("Please specify which field you want to update: ");
                System.out.println("A:  Paint Color");
                System.out.println("B:  Description");
                field = UserMenu.Read().toUpperCase();

                switch (field) {
                    case "A":
                        System.out.println("Please enter new value of Paint Color: ");
                        newValue = UserMenu.Read();
                        carsDA.updateCarByID(carID, newValue, null);
                        System.out.println("Changes applied successfully!");

                        break;
                    case "B":
                        System.out.println("Please enter new value of Description: ");
                        newValue = UserMenu.Read();
                        carsDA.updateCarByID(carID, null, newValue);
                        System.out.println("Changes applied successfully!");

                        break;
                }

                break;
            default:
                System.out.println("Go to cars menu and find your car ID for update!");
                break;
        }
    }
}
