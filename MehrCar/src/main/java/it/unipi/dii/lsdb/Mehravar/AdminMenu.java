package it.unipi.dii.lsdb.Mehravar;

import it.unipi.dii.lsdb.Mehravar.Model.*;
import org.bson.types.ObjectId;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminMenu {
    public static String Read() {
        String str = "";
        try {
            str = new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    public void adminScopeMenu(String phone) {
        UserMenu userMenu = new UserMenu();

        //Main Menu
        startMenu();
        boolean condition = true;
        while (condition) {
            switch (AdminMenu.Read().toUpperCase()) {
                case "A":
                    insertUserInterface();
                    System.out.println("User Inserted successfully");
                    AdminMenu.startMenu();
                    break;
                case "B":
                    userMenu.insertNewAdvert(phone);
                    System.out.println("Advert added successfully");
                    AdminMenu.startMenu();
                    break;
                case "C":
                    AdvertDA advertDA = new AdvertDA();
                    System.out.println("Please insert phone number: ");
                    String phoneNumber = AdminMenu.Read();
                    if (phoneNumber.equals("")){
                        System.out.println("Wrong input!");
                    }else {
                        advertDA.selectAdvertByPhone(phoneNumber);
                    }
                    AdminMenu.startMenu();
                    break;
                case "D":
                    selectUserInterface();
                    AdminMenu.startMenu();
                    break;
                case "E":
                    statistics();
                    AdminMenu.startMenu();
                    break;
                case "F":
                    deleteAdvertByID();
                    AdminMenu.startMenu();
                case "G":
                    userMenu.updateInterface();
                    AdminMenu.startMenu();
                    break;
                case "H":
                    userMenu.searchInterface();
                    AdminMenu.startMenu();
                    break;
                case "EXIT":
                    condition = false;
                    System.exit(0);
            }
        }
    }

    public static void startMenu() {
        System.out.println(":::Welcome to Admin Dashboard!:::");
        System.out.println("select one option: ");
        System.out.println("A: Add new user");
        System.out.println("B: Add new Advertisement");
        System.out.println("C: see adverts by phone number");
        System.out.println("D: see users by phone");
        System.out.println("E: Statistics Area");
        System.out.println("F: Delete Adverts");
        System.out.println("G: Update Adverts");
        System.out.println("H: Search for Car");
        System.out.println("Type EXIT for exit!");
    }

    public User userInterface() {
        User user = new User();
        System.out.println("Now please insert the details: ");
        System.out.println("insert Name:");
        user.setName(AdminMenu.Read());
        System.out.println("insert Surname:");
        user.setSurname(AdminMenu.Read());
        System.out.println("insert username:");
        user.setUsername(AdminMenu.Read());
        System.out.println("insert password:");
        user.setPassword(AdminMenu.Read());
        System.out.println("insert Email:");
        user.setEmail(AdminMenu.Read());
        System.out.println("insert Phone Number:");
        user.setPhone(AdminMenu.Read());
        System.out.println("insert Gender:");
        user.setGender(AdminMenu.Read());
        System.out.println("insert role(admin or user)");
        user.setRole(AdminMenu.Read());
        return user;
    }

    public void insertUserInterface() {
        UserDA userDA = new UserDA();
        User user = userInterface();
        userDA.insertUser(user);
    }

    public void registerNewUser(String phone) {
        UserDA userDA = new UserDA();
        User user = new User();
        System.out.println("Now please insert the details: ");
        System.out.println("insert Name:");
        user.setName(AdminMenu.Read());
        System.out.println("insert Surname:");
        user.setSurname(AdminMenu.Read());
        System.out.println("insert username:");
        user.setUsername(AdminMenu.Read());
        System.out.println("insert password:");
        user.setPassword(AdminMenu.Read());
        System.out.println("insert Email:");
        user.setEmail(AdminMenu.Read());
        user.setPhone(phone);
        System.out.println("insert Gender:");
        user.setGender(AdminMenu.Read());
        user.setRole("user");
        userDA.insertUser(user);

    }

    public Car carInterface() {
        Car car = new Car();
        System.out.println("insert Car model:");
        car.setModel(AdminMenu.Read());
        System.out.println("insert Car's Manufacturer:");
        car.setManufacturer(AdminMenu.Read());
        System.out.println("insert Region:");
        car.setRegion(AdminMenu.Read());
        System.out.println("insert Price:");
        car.setPrice(Integer.parseInt(AdminMenu.Read()));
        System.out.println("insert Model(4 digit year):");
        car.setYear(AdminMenu.Read());
        System.out.println("insert type of fuel:");
        car.setFuel(AdminMenu.Read());
        System.out.println("insert type of transmission:");
        car.setTransmission(AdminMenu.Read());
        System.out.println("insert the Type(Sedan, SUV,...):");
        car.setType(AdminMenu.Read());
        System.out.println("insert paint color:");
        car.setPaint_color(AdminMenu.Read());
        System.out.println("insert description");
        car.setDescription(AdminMenu.Read());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        car.setPosting_date(dateFormat.format(date));
        return car;
    }

    public void selectUserInterface() {
        UserDA userDA = new UserDA();
        System.out.println("Please insert the phone");
        userDA.selectUsersByPhone(AdminMenu.Read());
    }

    public void statistics() {
        Statistics statistics = new Statistics();
        statistics.usersStatistics();
    }

    public void deleteAdvertByID(){
        String advertID=null;
        System.out.println("Please enter ID of the advert you want to delete: ");
        advertID =AdminMenu.Read() ;

        AdvertDA advertDA = new AdvertDA();
        advertDA.deleteAdvertByID(advertID);
        System.out.println("Your advert DELETED successfully!");
    }
}
