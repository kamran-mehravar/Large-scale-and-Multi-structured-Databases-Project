package it.unipi.dii.lsdb.Mehravar;

import it.unipi.dii.lsdb.Mehravar.Model.*;

import java.io.BufferedReader;
import java.io.IOException;
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

    public void adminScopeMenu() {
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
                    insertNewAdvert();
                    System.out.println("Advert Inserted successfully");
                    AdminMenu.startMenu();
                    break;
                case "C":
                    selectCarByPhone();
                    AdminMenu.startMenu();
                    break;
                case "D":
                    selectUserInterface();
                    AdminMenu.startMenu();
                    break;
                case "E":
                    selectUserWithCar();
                    AdminMenu.startMenu();
                    break;
                case "F":
                    deleteAdvertForAdmin();
                    AdminMenu.startMenu();
                    break;
                case "G":
                    updateInterface();
                    AdminMenu.startMenu();
                    break;
                case "H":
                    statistics();
                    AdminMenu.startMenu();
                    break;
                case "EXIT":
                    condition = false;
                    System.exit(0);
            }
        }
    }

    public void deleteAdvertForAdmin() {
        String ID = "";
        System.out.println("Do you know your Car ID? (Y/N)");
        String answer = UserMenu.Read().toUpperCase();
        if (answer.equals("Y")) {
            System.out.println("Please insert your Car ID");
            ID = UserMenu.Read();
            if (ID.equals("")) {
                System.out.println("Empty input!");
            } else {
                CarsDA carsDA = new CarsDA();
                carsDA.deleteCarByID(ID);
                AdvertDA advertDA = new AdvertDA();
                advertDA.deleteAdvertByCardID(ID);
            }
        }else if (answer.equals("N")){
            System.out.println("please find the car ID by the following menu, then try again.");
        }else {
            System.out.println("Wrong input");
        }
    }

    public static void startMenu() {
        System.out.println(":::Welcome to Admin Dashboard!:::");
        System.out.println("select one option: ");
        System.out.println("A: Add new user");
        System.out.println("B: Add new Advertisement");
        System.out.println("C: see cars by phone number");
        System.out.println("D: see users by phone");
        System.out.println("E: see users and their cars");
        System.out.println("F: Delete an advert");
        System.out.println("G: Update an advert");
        System.out.println("H: Statistics Area");
        System.out.println("Type EXIT for exit!");
    }

    public UserTO userInterface() {
        UserTO userTO = new UserTO();
        System.out.println("Now please insert the details: ");
        System.out.println("insert Name:");
        userTO.setName(AdminMenu.Read());
        System.out.println("insert Surname:");
        userTO.setSurname(AdminMenu.Read());
        System.out.println("insert username:");
        userTO.setUsername(AdminMenu.Read());
        System.out.println("insert password:");
        userTO.setPassword(AdminMenu.Read());
        System.out.println("insert Email:");
        userTO.setEmail(AdminMenu.Read());
        System.out.println("insert Phone Number:");
        userTO.setPhone(AdminMenu.Read());
        System.out.println("insert Gender:");
        userTO.setGender(AdminMenu.Read());
        System.out.println("insert role(admin or user)");
        userTO.setRole(AdminMenu.Read());
        return userTO;
    }

    public void insertUserInterface() {
        UserDA userDA = new UserDA();
        UserTO userTO = userInterface();
        userDA.insertUser(userTO);
    }

    public void registerNewUser(String phone) {
        UserDA userDA = new UserDA();
        UserTO userTO = new UserTO();
        System.out.println("Now please insert the details: ");
        System.out.println("insert Name:");
        userTO.setName(AdminMenu.Read());
        System.out.println("insert Surname:");
        userTO.setSurname(AdminMenu.Read());
        System.out.println("insert username:");
        userTO.setUsername(AdminMenu.Read());
        System.out.println("insert password:");
        userTO.setPassword(AdminMenu.Read());
        System.out.println("insert Email:");
        userTO.setEmail(AdminMenu.Read());
        userTO.setPhone(phone);
        System.out.println("insert Gender:");
        userTO.setGender(AdminMenu.Read());
        userTO.setRole("user");
        userDA.insertUser(userTO);

    }

    public CarsTO carInterface() {
        CarsTO carsTO = new CarsTO();
        System.out.println("insert Car model:");
        carsTO.setModel(AdminMenu.Read());
        System.out.println("insert Car's Manufacturer:");
        carsTO.setManufacturer(AdminMenu.Read());
        System.out.println("insert Region:");
        carsTO.setRegion(AdminMenu.Read());
        System.out.println("insert Price:");
        carsTO.setPrice(AdminMenu.Read());
        System.out.println("insert Model(4 digit year):");
        carsTO.setYear(AdminMenu.Read());
        System.out.println("insert type of fuel:");
        carsTO.setFuel(AdminMenu.Read());
        System.out.println("insert type of transmission:");
        carsTO.setTransmission(AdminMenu.Read());
        System.out.println("insert the Type(Sedan, SUV,...):");
        carsTO.setType(AdminMenu.Read());
        System.out.println("insert paint color:");
        carsTO.setPaint_color(AdminMenu.Read());
        System.out.println("insert phone number");
        carsTO.setPhone(AdminMenu.Read());
        System.out.println("insert description");
        carsTO.setDescription(AdminMenu.Read());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        carsTO.setPosting_date(dateFormat.format(date));
        return carsTO;
    }

    public String insertNewCar() {
        CarsTO carsTO = carInterface();
        CarsDA carsDA = new CarsDA();
        return carsDA.insertCars(carsTO);
    }

    //select cars by phone number
    public void selectCarByPhone() {
        CarsTO carsTO = new CarsTO();
        CarsDA carsDA = new CarsDA();
        System.out.println("Please insert the phone number");
        carsTO.setPhone(AdminMenu.Read());
        carsDA.selectCarsByPhone(carsTO.getPhone());
    }

    public String selectCarByID(String ID) {
        CarsDA carsDA = new CarsDA();
        return carsDA.selectCarByID(ID);
    }

    public void selectUserInterface() {
        UserDA userDA = new UserDA();
        System.out.println("Please insert the phone");
        userDA.selectUsersByPhone(AdminMenu.Read());
    }


    public void selectUserWithCar() {
        System.out.println("Please insert the phone number");
        CarsDA carsDA = new CarsDA();
        carsDA.selectCarsForUser(AdminMenu.Read());

    }

    public void insertNewAdvert() {
        String cardID = insertNewCar();
        System.out.println("Car added");
        String phone = selectCarByID(cardID);
        System.out.println(phone);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        Advert advert = new Advert(cardID, dateFormat.format(date), phone);
        AdvertDA advertDA = new AdvertDA();
        advertDA.insertNewAdvert(advert);
        System.out.println("Advert added");
    }

    public void statistics() {
        Statistics statistics = new Statistics();
        statistics.usersStatistics();
    }

    public void updateInterface(){
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
