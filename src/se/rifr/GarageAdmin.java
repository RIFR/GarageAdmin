package se.rifr;

import java.time.LocalDateTime;
import java.util.*;

public class GarageAdmin {

    private Map<String, User> userList = new HashMap<>();
    private Map<String, Customer> customerList = new HashMap<>();
    private Map<String, Account> accountList = new HashMap<>();
    private Map<String, Vehicle> vehicleList = new HashMap<>();
    private Map<String, ParkingSlot> parkingSlotList = new HashMap<>();
    private Map<String, Garage> garageList = new HashMap<>();
    private List<Scannings> scanningList = new ArrayList<>();
    //private List<Floor> floorList                    = new ArrayList<>();
    //private List<Garage> garageList                  = new ArrayList<>();

    private String dirName = "C:\\Dev\\GarageAdmin\\";

    private String userFile        = dirName + "userlist.ser";
    private String customerFile    = dirName + "customerlist.ser";
    private String accountFile     = dirName + "accountlist.ser";
    private String vehicleFile     = dirName + "vehiclelist.ser";
    private String scanningFile    = dirName + "scanninglist.ser";
    private String garageFile      = dirName + "garagelist.ser";
    private String parkingSlotFile = dirName + "parkingslotlist.ser";


    public GarageAdmin() {

        LoadReloadData();

        //if (userList == null) {
        User user = new User("Super", "User", "007", "admin@mybank.se", "SuperUser", "SuperUser");
        userList.put(user.getKey(), user);
        //FileIO.writeObject(userList, userFile);
        //}
    }

    public void SaveAllData() {

        if (accountList     != null) FileIO.writeObject(accountList, accountFile);
        if (customerList    != null) FileIO.writeObject(customerList, customerFile);
        if (scanningList    != null) FileIO.writeObject(scanningList, scanningFile);
        if (userList        != null) FileIO.writeObject(userList, userFile);
        if (parkingSlotList != null) FileIO.writeObject(parkingSlotList, parkingSlotFile);
        if (garageList      != null) FileIO.writeObject(garageList, garageFile);
        if (vehicleList     != null) FileIO.writeObject(vehicleList, vehicleFile);

    }

    public void LoadReloadData() {

        try {
            Map<String, User> tempUserList = FileIO.readObject(userFile);
            if (tempUserList != null)
                userList = tempUserList;

            Map<String, Customer> tempCustomerList = FileIO.readObject(customerFile);
            if (tempCustomerList != null)
                customerList = tempCustomerList;

            Map<String, Account> tempAccountList = FileIO.readObject(accountFile);
            if (tempAccountList != null)
                accountList = tempAccountList;

            Map<String, Vehicle> tempVehicleList = FileIO.readObject(vehicleFile);
            if (tempVehicleList != null)
                vehicleList = tempVehicleList;

            Map<String, ParkingSlot> tempParkingSlotList = FileIO.readObject(parkingSlotFile);
            if (tempParkingSlotList != null)
                parkingSlotList = tempParkingSlotList;

            List<Scannings> tempScanningList = FileIO.readObject(scanningFile);
            if (tempScanningList != null)
                scanningList = tempScanningList;

            Map<String, Garage> tempGarageList= FileIO.readObject(garageFile);
            if (tempGarageList != null)
                garageList = tempGarageList;

            //List<Floor> tempFloorList= FileIO.readObject(floorFile);
            //if (tempFloorList != null)
            //    floorList = tempFloorList;

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private boolean login() {

        try {
            int noLogins = 0;
            while (noLogins < 3) {
                StdIO.clearScreen();
                StdIO.writeLine("");
                StdIO.writeLine("Logon");
                StdIO.writeLine("");
                StdIO.write("Username : ");
                String userName = StdIO.readLine();
                StdIO.write("Password : ");
                String password = StdIO.readLine();
                noLogins++;
                if (loginOk(userName, password)) {
                    return true;
                }
            }
            //System.exit(0);
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
        return false;
    }

    public boolean loginOk(String username, String password) {
        return userList.containsKey(username) && userList.get(username).getPassword().equals(password);
    }

    public boolean garageExists(String name) {
        return (garageList.containsKey(name.toUpperCase()));
    }

    public boolean customerExists(String customerBarcode) {
        return customerList.containsKey(customerBarcode.toUpperCase());
    }

    public Customer getCustomer(String barcode) {
        return customerList.get(barcode);
    }
    public void createCustomer(String firstName,String lastName,String barcode,String email,String telno) {

        Customer customer = new Customer(firstName,lastName,barcode,email,telno,
                firstName.substring(0,1).toUpperCase()+lastName.substring(0,1).toUpperCase());

        customerList.put(customer.getKey(),customer);
        FileIO.writeObject(customerList, customerFile);
    }

    public boolean isRegistered(String vehicleBarcode) {
         return vehicleList.containsKey(vehicleBarcode);
    }

    public boolean isParked(String vehicleBarcode) {
        return isRegistered(vehicleBarcode) && getSlot (vehicleList.get(vehicleBarcode.toUpperCase()))!= null;
    }

    public Vehicle getVehicle(String vehicleBarcode) {
        if (vehicleList.containsKey(vehicleBarcode)) return vehicleList.get(vehicleBarcode);
        else throw new IllegalArgumentException("Vehicle "+vehicleBarcode+" not found");
    }

    public void createVehicle(String regNo, String kind, String model, String colour, Customer customer) {
        Vehicle vehicle;
        switch (kind.toUpperCase()){
            case "MC" :
                vehicle = new Mc(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer);
                break;
            case "CAR" :
                vehicle = new Car(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(),customer);
                break;
            case "TRUCK" :
                vehicle = new Truck(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer);
                break;
            case "LORRY" :
                vehicle = new Lorry(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer);
                 break;
            case "BUS" :
                vehicle = new Bus(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer, 20);
                break;
            case "JUGGERNAUT" :
                vehicle = new Juggernaut(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer, 0);
                break;
            default:
                vehicle = new Vehicle(regNo.toUpperCase(), model.toUpperCase(), colour.toUpperCase(), customer);
         }

        vehicleList.put(vehicle.getKey(),vehicle);
        FileIO.writeObject(vehicleList, vehicleFile);

    }

    public String parkVehicle (Vehicle vehicle, Garage garage) {
        ParkingSlot slot = park (vehicle,garage);
        if (slot != null) {

            // Store the updated scanning
            Scannings scanning = new Scannings(vehicle, LocalDateTime.now(), true, garage);
            scanningList.add(scanning);
            FileIO.writeObject(scanningList, scanningFile);

            return slot.toString();}
        else return "";
    }

    public String unparkVehicle (Vehicle vehicle) {

        ParkingSlot slot = unpark (vehicle);

        if (slot != null) {

            // Store the updated scanning
            Scannings scanning = new Scannings(vehicle, LocalDateTime.now(), false, slot.getGarage());
            scanningList.add(scanning);
            FileIO.writeObject(scanningList, scanningFile);

            return slot.toString();
        } else return "";
    }

    public Garage getGarage(String name) {
        if (garageList.containsKey(name)) return garageList.get(name);
        else throw new IllegalArgumentException("Garage "+name+" not found");
    }

    public void start() {

        if (!login()) return;

        while (handleMenu(menu())) {}

        SaveAllData ();

    }

    private boolean handleMenu (String answer) {

        try {
            switch (answer) {
                case "0":
                    LoadReloadData();
                    break;
                case "1":
                    maintainCustomer();
                    break;
                case "2":
                    listCustomers();
                    break;
                case "3":
                    maintainUsers();
                    break;
                case "4":
                    listUsers();
                    break;
                case "5":
                    maintainAccounts();
                    break;
                case "6":
                    listAccounts();
                    break;
                case "7":
                    copyCustomerDataToUser();
                    break;
                case "8":
                    deleteUser();
                    break;
                case "9":
                    maintainVehicles();
                    break;
                case "10":
                    listVehicles();
                    break;
                case "11":
                    createGarage();
                    break;
                case "12":
                    listGarage();
                    break;
                case "13":
                    maintainParkingSlots();
                    break;
                case "14":
                    listParkingSlots();
                    break;
                case "15":
                    listAllFreeParkingSlots();
                    break;
                case "16":
                    listFreeParkingSlots();
                    break;
                case "17":
                    scanParking();
                    break;
                case "18":
                    listScannings();
                    break;
                //case "17":
                //    excludeOrOpeVehicle(true);
                //    break;
                //case "18":
                //    excludeOrOpeVehicle(false);
                //    break;
                case "80":

                    System.out.print("Garage name? (A/B)");
                    Garage myGarage = garageList.get("DROTTNINGGATAN 8"+StdIO.readLine().toUpperCase());

                    System.out.println("No of free slots "+ countFreeSlotsInTheGarage(myGarage));

                    System.out.print("Reg Nos (3 char)");
                    String regNoP0 = StdIO.readLine();

                    Customer customer80 = customerList.get("189901011111");

                    Mc    myMc    = new Mc (regNoP0+"000","YAMAHA","RED",2,
                            7,"Petrol95",customer80);
                    vehicleList.put(myMc.getKey(),myMc);

                    String temp = parkVehicle(myMc,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myMc);
                    else
                        System.out.println("No slot found for "+myMc);

                    Car   myCar   = new Car  (regNoP0+"111","VOLVO XC60","BRAUN",4,
                            3,"Petrol95",customer80);
                    vehicleList.put(myCar.getKey(),myCar);

                    temp = parkVehicle(myCar,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myCar);
                    else
                        System.out.println("No slot found for "+myCar);

                    Truck myTruck = new Truck(regNoP0+"222","VOLVO","BLUE",4,
                            6,"Petrol95",customer80);
                    vehicleList.put(myTruck.getKey(),myTruck);

                    temp = parkVehicle(myTruck,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myTruck);
                    else
                        System.out.println("No slot found for "+myTruck);

                    Lorry myLorry = new Lorry(regNoP0+"333","VOLVO","GREEN",6,
                            5,"Petrol95",customer80);
                    vehicleList.put(myLorry.getKey(),myLorry);

                    temp = parkVehicle(myLorry,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myLorry);
                    else
                        System.out.println("No slot found for "+myLorry);

                    Juggernaut myJuggernaut = new Juggernaut(regNoP0+"444","VOLVO",
                            "YELLOW",8,7,"Petrol95",customer80,2);
                    vehicleList.put(myJuggernaut.getKey(),myJuggernaut);

                    temp = parkVehicle(myJuggernaut,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myJuggernaut);
                    else
                        System.out.println("No slot found for "+myJuggernaut);

                    System.out.println("No of free slots "+ countFreeSlotsInTheGarage(myGarage));

                    break;

                case "81":

                    System.out.print("Reg Nos (3 char)");
                    String regNoP1 = StdIO.readLine();

                    unparkVehicle(vehicleList.get(regNoP1+"000"));
                    unparkVehicle(vehicleList.get(regNoP1+"111"));
                    unparkVehicle(vehicleList.get(regNoP1+"222"));
                    unparkVehicle(vehicleList.get(regNoP1+"333"));
                    unparkVehicle(vehicleList.get(regNoP1+"444"));

                    break;
                case "88":

                    Customer kalleAnka = new Customer
                            ("Kalle", "Anka", "189901011111", "kalle.anka@ankeborg.com",
                                    "+46707155733", "KALLEANKA");
                    customerList.put(kalleAnka.getKey(), kalleAnka);

                    User user = new User(kalleAnka.getFirstName(), kalleAnka.getLastName(), kalleAnka.getBarCode(), kalleAnka.getEmail(), kalleAnka.getUserName(), "KalleAnkaÄrBäst");
                    userList.put(user.getKey(), user);

                    Account account = new Account(kalleAnka, "12345678901234567890", 0.0, "Kalles head account");
                    accountList.put(account.getKey(), account);

                    //----------------------------------------------------------------------------------------

                    Garage garage1 = new Garage ("DROTTNINGGATAN 8A","Litet garage");
                    garageList.put(garage1.getKey(), garage1);
                    Floor floor = new Floor(1,"1 bil plats plus två mc platser");
                    ParkingSlot slotCar1 = new ParkingSlot (garage1,floor,1,Vehicle.Size.MEDIUM,20.0d);
                    parkingSlotList.put(slotCar1.getKey(),slotCar1);

                    ParkingSlot slotMc1  = new ParkingSlot (garage1,floor,2,Vehicle.Size.SMALL,5.0d);
                    parkingSlotList.put(slotMc1.getKey(),slotMc1);

                    ParkingSlot slotMc2  = new ParkingSlot (garage1,floor,3,Vehicle.Size.SMALL,5.0d);
                    parkingSlotList.put(slotMc2.getKey(),slotMc2);

                    //----------------------------------------------------------------------------------------

                    Garage garage2 = new Garage
                            ("DROTTNINGGATAN 8B","Lite större garage, tre våningsplan");

                    garageList.put(garage2.getKey(), garage2);
                    Floor floor1 = new Floor(1,"1 bil plats plus två mc platser");
                    ParkingSlot slotCar21 = new ParkingSlot (garage2,floor1,1,Vehicle.Size.MEDIUM,24.0d);
                    parkingSlotList.put(slotCar21.getKey(),slotCar21);

                    ParkingSlot slotMc21  = new ParkingSlot (garage2,floor1,2,Vehicle.Size.SMALL,6.0d);
                    parkingSlotList.put(slotMc21.getKey(),slotMc21);

                    ParkingSlot slotMc22  = new ParkingSlot (garage2,floor1,3,Vehicle.Size.SMALL,6.0d);
                    parkingSlotList.put(slotMc22.getKey(),slotMc22);

                    Floor floor2 = new Floor(2,"plats för 3 mc, 2 bil, 1 Truck  platser");

                    ParkingSlot slotMc31  = new ParkingSlot (garage2,floor2,1,Vehicle.Size.SMALL,6.0d);
                    parkingSlotList.put(slotMc31.getKey(),slotMc31);

                    ParkingSlot slotMc32  = new ParkingSlot (garage2,floor2,2,Vehicle.Size.SMALL,6.0d);
                    parkingSlotList.put(slotMc32.getKey(),slotMc32);

                    ParkingSlot slotMc33  = new ParkingSlot (garage2,floor2,3,Vehicle.Size.SMALL,6.0d);
                    parkingSlotList.put(slotMc33.getKey(),slotMc33);

                    ParkingSlot slotMc34  = new ParkingSlot (garage2,floor2,4,Vehicle.Size.MEDIUM,24.0d);
                    parkingSlotList.put(slotMc34.getKey(),slotMc34);

                    ParkingSlot slotMc35  = new ParkingSlot (garage2,floor2,5,Vehicle.Size.MEDIUM,24.0d);
                    parkingSlotList.put(slotMc35.getKey(),slotMc35);

                    ParkingSlot slotMc36  = new ParkingSlot (garage2,floor2,6,Vehicle.Size.LARGE,48.0d);
                    parkingSlotList.put(slotMc36.getKey(),slotMc36);

                    Floor floor3 = new Floor(3,"plats för 2 långtradare platser");

                    ParkingSlot slotMc41  = new ParkingSlot (garage2,floor3,1,Vehicle.Size.HUGE,96.0d);
                    parkingSlotList.put(slotMc41.getKey(),slotMc41);

                    ParkingSlot slotMc42  = new ParkingSlot (garage2,floor3,2,Vehicle.Size.HUGE,96.0d);
                    parkingSlotList.put(slotMc42.getKey(),slotMc42);

                    break;
                //case "70":
                //    System.out.println(registerTransaction(getAtmHw("AtmTest001"),getAccount("000000000000001"), false, 500.0));
                //    break;
                //case "71":
                //    System.out.println(registerTransaction(getAtmHw("AtmTest001"),getAccount("000000000000001"), true, 2000.0));
                //    break;
                case "99":
                    listUsers();
                    System.out.println("");
                    listAccounts();
                    System.out.println("");
                    listCustomers();
                    System.out.println("");
                    listVehicles();
                    System.out.println("");
                    listScannings();
                    System.out.println("");
                    listGarages();System.out.println("");
                    listParkingSlots();
                    System.out.println("");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }

        return !answer.equalsIgnoreCase("q");
    }

    private String menu() {
        try {
            String answer;

            StdIO.clearScreen();
            StdIO.writeLine("");
            StdIO.writeLine("Menu");
            StdIO.writeLine("");
            StdIO.writeLine(" 0. Reload");
            StdIO.writeLine(" 1. Maintain Customer");
            StdIO.writeLine(" 2. List Customer");
            StdIO.writeLine(" 3. Maintain users");
            StdIO.writeLine(" 4. List users");
            StdIO.writeLine(" 5. Maintain Accounts");
            StdIO.writeLine(" 6. List Accounts");
            StdIO.writeLine(" 7. Create user from customer");
            StdIO.writeLine(" 8. Delete user");
            StdIO.writeLine(" 9. Maintain Vehicles ");
            StdIO.writeLine("10. List Vehicles");
            StdIO.writeLine("11. Create Garage");
            StdIO.writeLine("12. List Garage");
            StdIO.writeLine("13. Maintain Parkingslots ");
            StdIO.writeLine("14. List Parkingslots ");
            StdIO.writeLine("15. List All Free Parkingslots ");
            StdIO.writeLine("16. List Free Parkingslots in a garage");
            StdIO.writeLine("17. Scan Parking ");
            StdIO.writeLine("18. List Parking");
            StdIO.writeLine("");
            StdIO.writeLine("q. Exit");
            StdIO.writeLine("");
            StdIO.write("Select : ");
            answer = StdIO.readLine();
            return answer;
        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
            return null;
        }
    }

    private void scanParking() {
        Vehicle vehicle;
        Garage garage;
        boolean entering;
        try {
            StdIO.clearScreen();
            StdIO.writeLine("Scan Parking");

            StdIO.writeLine("Vehicle barcode");
            String barcode = StdIO.readLine().trim().toUpperCase();

            if (vehicleList.containsKey(barcode))
                vehicle = vehicleList.get(barcode);
            else {
                StdIO.ErrorReport("Unknown vehicle "+barcode);
                return;
            }

            StdIO.writeLine("Entering (y/n)");
            entering = StdIO.readYesOrNo();

            if (entering) {
                StdIO.writeLine("Garage name");
                String garageName = StdIO.readLine();

                if (garageList.containsKey(garageName)) {
                    garage = garageList.get(garageName);
                    park(vehicle,garage);
                } else {
                    StdIO.ErrorReport("Unknown garage "+garageName);
                    return;
                }
            }else {
                ParkingSlot slot = unpark(vehicle);
                garage = slot.getGarage();
            }

            // Store the updated scanning
            Scannings scanning = new Scannings(vehicle, LocalDateTime.now(), entering, garage);
            scanningList.add(scanning);
            FileIO.writeObject(scanningList, scanningFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

     public ParkingSlot park (Vehicle vehicle, Garage inGarage){

        ParkingSlot slot = getNextFree (inGarage,vehicle.getSize());
        if (slot != null) {

            slot.setParked (vehicle);

            parkingSlotList.put(slot.getKey(),slot);
            FileIO.writeObject(parkingSlotList, parkingSlotFile);
        }

        return slot;
    }

    public ParkingSlot getSlot (Vehicle vehicle){

        for (ParkingSlot slot : parkingSlotList.values()) {
            if (!slot.isFree() && slot.getParked().getBarcode().equals(vehicle.getBarcode())) {
                 return slot; // found, exit loop
            }
        }
        return null;
    }

    public ParkingSlot unpark (Vehicle vehicle){

        ParkingSlot slot = getSlot (vehicle);
        if (slot == null) {
            StdIO.ErrorReport("Not parked "+vehicle);
            return null;
        }

        long minutes = slot.free();
        parkingSlotList.put(slot.getKey(),slot);
        FileIO.writeObject(parkingSlotList, parkingSlotFile);

        if (accountList.containsKey(vehicle.getCustomer().getBarCode())) {
            Account account = accountList.get(vehicle.getCustomer().getBarCode());
            account.updateParkedTime(minutes);
            account.changeSaldo(slot.getFeePerMinute()*minutes,false);
            accountList.put(account.getKey(),account);
            FileIO.writeObject(accountList, accountFile);
        } else
            System.out.println("The "+vehicle+" was parked for "+minutes+
                    " minutes, since the owner did not have any account he/she need to pay the fee now");
        //" minutes, since the owner did not have any account he/she need to pay the fee of "+
        //        slot.getGarage().getFeePerMinute()*minutes +" now");

        return slot;
    }

    private void listCustomers() {
        System.out.println(Customer.toStringHeader());
        if (customerList != null)
            customerList.forEach((k, v) -> System.out.println(v.toStringLine()));
    }

    private void maintainCustomer() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add/update customer");
            StdIO.writeLine("");

            StdIO.write("First name: ");
            String firstName = StdIO.readLine();
            StdIO.write("Last name : ");
            String lastName = StdIO.readLine();
            StdIO.write("Barcode   : ");
            String barcode = StdIO.readLine();
            StdIO.write("email     : ");
            String email = StdIO.readLine();
            StdIO.write("Telephone : ");
            String telephoneNo = StdIO.readLine();
            StdIO.write("User name : ");
            String userName = StdIO.readLine();

            Customer customer = new Customer(firstName, lastName, barcode, email, telephoneNo, userName);

            customerList.put(customer.getKey(), customer);

            FileIO.writeObject(customerList, customerFile);

            updateAccountdWithUpdatedCustomer(customer); // update if exists in accounts

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void updateAccountdWithUpdatedCustomer(Customer customer) {
        for (Account x : accountList.values()) {
            if (x.getCustomer().getKey() == customer.getKey()) {

                x.setCustomer(customer);
                accountList.put(x.getKey(), x);

            }
        }
        FileIO.writeObject(accountList, accountFile);
    }

    public void listScannings() {
        System.out.println(Scannings.toStringHeader());
        if (scanningList != null)
            scanningList.stream().forEach(item -> System.out.println(item.toStringLine()));
    }

    public void listUsers() {
        System.out.println(User.toStringHeader());
        if (userList != null)
            userList.forEach((k, v) -> System.out.println(v.toStringLine()));
    }

    private void maintainUsers() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add a user");
            StdIO.writeLine("");
            StdIO.write("First name: ");
            String firstName = StdIO.readLine();
            StdIO.write("Last name : ");
            String lastName = StdIO.readLine();
            StdIO.write("Barcode   : ");
            String barcode = StdIO.readLine();
            StdIO.write("email     : ");
            String email = StdIO.readLine();
            StdIO.write("User name : ");
            String userName = StdIO.readLine();
            StdIO.write("Password  : ");
            String password = StdIO.readLine();
            User user = new User(firstName, lastName, barcode, email, userName, password);

            userList.put(user.getKey(), user);

            FileIO.writeObject(userList, userFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void copyCustomerDataToUser() {

        try {

            Customer customer;
            StdIO.write("Barcode   : ");
            String barcode = StdIO.readLine().trim();

            if (customerList.containsKey(barcode))
                customer = customerList.get(barcode);
            else {
                StdIO.ErrorReport("Unknown Customer " + barcode);
                return;
            }

            String userName = customer.getUserName();
            while (userList.containsKey(userName)) {
                StdIO.ErrorReport("User Name " + customer.getUserName() + " already exists, enter new");
                userName = StdIO.readLine().trim();
            }

            customer.setUserName(userName);
            customerList.put(customer.getKey(), customer);
            FileIO.writeObject(customerList, customerFile);

            StdIO.write("Password   : ");
            String password = StdIO.readLine().trim();

            User user = new User(customer.getFirstName(), customer.getLastName(), customer.getBarCode(), customer.getEmail(), userName, password);
            userList.put(user.getKey(), user);
            FileIO.writeObject(userList, userFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void deleteUser() {
        try {
            StdIO.write("User Name : ");
            String userName = StdIO.readLine();
            if (userList.containsKey(userName)) {
                User user = userList.get(userName);
                StdIO.writeLine("Confirm deletion for "
                        + user.getFirstName() + " " + user.getLastName() + " " + user.getBarcode() + "(y/n)");
                if (StdIO.readYesOrNo()) {
                    userList.remove(userName);
                    FileIO.writeObject(userList, userFile);
                }
            } else
                StdIO.ErrorReport("Unknown User Name");

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }


    private void listAccounts() {
        System.out.println(Account.toStringHeader());
        if (accountList != null)
            accountList.forEach((k, v) -> System.out.println(v.toStringLine()));
    }

    private void maintainAccounts() {
        Account account;
        double saldo;
        try {
            Customer customer;

            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add an Account");

            StdIO.writeLine("");
            StdIO.writeLine("Customer barcode");
            String barcode = StdIO.readLine();
            if (customerList.containsKey(barcode))
                customer = customerList.get(barcode);
            else {
                System.out.println("Unknown customer " + barcode);
                return;
            }

            StdIO.writeLine("");
            StdIO.write("Account Id  : ");
            String accountID = StdIO.readLine().trim();
            StdIO.write("Description : ");
            String description = StdIO.readLine();

            if (accountList.containsKey(accountID)) {
                account = accountList.get(accountID);
                account.setDescription(description);
                if (account.getCustomer().getBarCode() != customer.getBarCode()) {
                    StdIO.ErrorReport("The customer can not be changed");
                    return;
                } else
                    account.setCustomer(customer);

            } else {
                StdIO.write("Saldo       : ");
                saldo = Double.valueOf(StdIO.readLine());

                account = new Account(customer, accountID, saldo, description);
            }

            accountList.put(account.getKey(), account);

            FileIO.writeObject(accountList, accountFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listVehicles() {
        System.out.println(Vehicle.toStringHeader());
        if (vehicleList != null) {
            vehicleList.values().stream()
                    .sorted(Comparator.comparing(item -> item.getSize()))
                    .sorted(Comparator.comparing(item -> item.getBarcode()))
                    .sorted(Comparator.comparing(item -> item.getCustomer().getFullName()))
                    .forEach(item -> System.out.println(item.toStringLine()));

            //for (Vehicle x : vehicleList.values() )
            //    System.out.println(x.toStringLine());
            //vehicleList.forEach((k, v) -> System.out.println(v.toStringLine()));
        }
    }

    private void maintainVehicles() {
        Vehicle vehicle;
        double saldo;
        try {
            Customer customer;

            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add a Vehicle");

            StdIO.writeLine("");
            StdIO.writeLine("Customer barcode");
            String barcode = StdIO.readLine();
            if (customerList.containsKey(barcode))
                customer = customerList.get(barcode);
            else {
                System.out.println("Unknown customer " + barcode);
                return;
            }

            StdIO.writeLine("");
            StdIO.write("Registration Number : ");
            String regNo = StdIO.readLine().trim().toUpperCase();

            StdIO.write("MODEL: ");
            String model = StdIO.readLine().toUpperCase();

            StdIO.write("COLOUR: ");
            String colour = StdIO.readLine().toUpperCase();

            StdIO.write("NUMBER OF WHEELS: ");
            int noOfWheels = Integer.valueOf(StdIO.readLine());

            StdIO.write("NOISE LEVEL: ");
            int noiseLevel = Integer.valueOf(StdIO.readLine());

            StdIO.write("FUEL: ");
            String fuel = StdIO.readLine().toUpperCase();

            StdIO.write("CLASS (MC,CAR,TRUCK,LORRY,BUS,JUGGERNAUT): ");
            String kind = StdIO.readLine().toUpperCase();

            //StdIO.write("Size ( ");
            //for (Vehicle.Size size : Vehicle.Size.values())
            //    StdIO.write(size.toString() + " ");
            //StdIO.write("): ");
            //Vehicle.Size size = Vehicle.Size.valueOf(StdIO.readLine().toUpperCase());

            // Create and store
            if (!vehicleList.containsKey(regNo)) {createVehicle(regNo, kind, model, colour, customer); }

            vehicle = vehicleList.get(regNo);

            vehicle.setModel(model);
            vehicle.setColour(colour);
            vehicle.setNoOfWheels(noOfWheels);
            vehicle.setNoiseLevel(noiseLevel);
            vehicle.setFuel(fuel);
            vehicle.setCustomer(customer);

            switch (vehicle.getClass().getSimpleName().toUpperCase().trim()) {
                case "BUS" :
                    StdIO.write("No Of Seats: ");
                    int noOfSeats = Integer.valueOf(StdIO.readLine());

                    Bus bus = (Bus)vehicle;
                    bus.setNoOfSeats(noOfSeats);
                    vehicleList.put(bus.getKey(),bus);
                    break;
                case "JUGGERNAUT" :
                    StdIO.write("No Of Beds: ");
                    int noOfBeds = Integer.valueOf(StdIO.readLine());

                    vehicle = new Juggernaut(regNo, model, colour, noOfWheels, noiseLevel, fuel, customer, noOfBeds);
                    break;
            }

            vehicleList.put(vehicle.getKey(), vehicle);
            FileIO.writeObject(vehicleList, vehicleFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listParkingSlots() {
        System.out.println(ParkingSlot.toStringHeader());
        //if (parkingSlotList != null)
         //   parkingSlotList.forEach((k, v) -> System.out.println(v.toStringLine()));

        parkingSlotList.values().stream()
                //.sorted()
                .sorted(Comparator.comparing(item -> item.getPlaceNo()))
                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listFreeParkingSlots() {

        try {
            StdIO.write("Garage Name : ");
            String name = StdIO.readLine();

            System.out.println(ParkingSlot.toStringHeader());

            parkingSlotList.values().stream()
                    .filter(item -> item.getGarage().getName().equals(name))
                    .filter(item -> item.isFree())
                    .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
                    .sorted(Comparator.comparing(item -> item.getSize()))
                    //.sorted(Comparator.comparing(item -> item.getGarage().getName()))
                    .forEach(item -> System.out.println(item.toStringLine()));

            System.out.println();
            System.out.println("Total no of free slots "+countFreeSlotsInTheGarage(garageList.get(name)));

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void listAllFreeParkingSlots() {

        System.out.println(ParkingSlot.toStringHeader());

        parkingSlotList.values().stream()
                .filter(item -> item.isFree())
                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
                .sorted(Comparator.comparing(item -> item.getSize()))
                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
                .forEach(item -> System.out.println(item.toStringLine()));

        System.out.println();
        System.out.println("Total no of free slots "+countAllFreeSlots());
    }


    private int countNoOfFreeSlots (Garage garage, Vehicle.Size withSize) {
        int noOfItems = 0;

        for (ParkingSlot x : parkingSlotList.values())
            if (garage.getKey().equals(x.getGarage().getKey()) && x.isFree() && x.getSize() == withSize ) {
                noOfItems++;
            }

        return noOfItems;
    }

    private int countFreeSlotsInTheGarage(Garage garage) {
        int noOfItems = 0;

        for (Vehicle.Size size : Vehicle.Size.values())
            noOfItems += countNoOfFreeSlots (garage,size);

        return noOfItems;
    }

    private int countAllFreeSlots() {
        int noOfItems = 0;

        for (Garage garage : garageList.values())
            noOfItems += countFreeSlotsInTheGarage (garage);

        return noOfItems;
    }

    private ParkingSlot getNextFreeWithSize (Garage garage, Vehicle.Size withSize) {

        for (ParkingSlot x : parkingSlotList.values()) {
            if (garage.getKey().equals(x.getGarage().getKey()) && x.isFree() && x.getSize() == withSize) {
                return x;
            }
        }

        return null; // Not found
        //throw new IllegalCallerException("Full, no free slot exists with the requeste size");
    }

    private ParkingSlot getNextFree (Garage garage, Vehicle.Size fromSize) {

        //for (Vehicle.Size i=fromSize;i.equals("HUGE");Vehicle.Size.values()) {
        //    System.out.println(i);
        //}

        for ( Vehicle.Size size : Vehicle.Size.values()) {
            if (size.compareTo(fromSize) >=0 ){
                ParkingSlot temp = getNextFreeWithSize(garage,size);
                if (temp != null) return temp;
            }
        }

        return null; // Not found
        //throw new IllegalCallerException("Full, no free slot exists with the requeste size");
    }

    private void listGarage(Garage garage) {

        System.out.println(garage.getName() + " "+ garage.getDescription());
        //System.out.println();
        System.out.println(ParkingSlot.toStringHeader());
        parkingSlotList.values().stream()
                .filter(item -> item.getGarage().getName().equals(garage.getName()))
                .sorted(Comparator.comparing(item -> item.getPlaceNo()))
                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listGarage() {
        try {
            StdIO.write("Name       : ");
            String name = StdIO.readLine();

            listGarage(garageList.get(name));

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listGarages() {
        for (Garage x : garageList.values()) {
            listGarage (x);
            System.out.println();
        }
    }

    private void createGarage() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Create Garage");
            StdIO.writeLine("");
            StdIO.write("Name       : ");
            String name = StdIO.readLine();
            StdIO.write("Description     : ");
            String description = StdIO.readLine();
            //StdIO.write("Fee per hour     : ");
            //String costPerHour = StdIO.readLine();

            Garage garage = new Garage(name, description);

            garageList.put(garage.getKey(),garage);
            FileIO.writeObject(garageList, garageFile);

            StdIO.write("No Of Floors    : ");
            int noOfFloors = Integer.valueOf(StdIO.readLine());

            for (int i = 0; i < noOfFloors; i++) {
                StdIO.write(i + " Floor Description: ");
                description = StdIO.readLine();

                Floor floor = new Floor(i, description);

                StdIO.write("No Of Parking slots on the floor : ");
                int noOfSlots = Integer.valueOf(StdIO.readLine());

                StdIO.write("Size of the slots (SMALL,MEDIUM,LARGE,HUGE) : ");
                Vehicle.Size slotSize = Vehicle.Size.valueOf(StdIO.readLine().toUpperCase());

                StdIO.write("Cost per hour : ");
                double cost = Double.valueOf(StdIO.readLine());

                for (int j = 0; j < noOfSlots; j++) {

                    ParkingSlot parkingSlot = new ParkingSlot(garage, floor, i, slotSize, cost);

                    parkingSlotList.put(parkingSlot.getKey(), parkingSlot);
                }
            }

            FileIO.writeObject(parkingSlotList, parkingSlotFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void maintainParkingSlots() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Maintain i Parking Slot");
            StdIO.writeLine("");
            StdIO.write("Garage Name : ");
            String name = StdIO.readLine();

            StdIO.write("Floor : ");
            int floorIx = Integer.valueOf(StdIO.readLine());

            StdIO.write("Slot : ");
            int slotIx = Integer.valueOf(StdIO.readLine());

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }
}
