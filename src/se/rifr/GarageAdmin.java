package se.rifr;

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

    String dirName = "C:\\Dev\\GarageAdmin\\";

    String userFile        = dirName + "userlist.ser";
    String customerFile    = dirName + "customerlist.ser";
    String accountFile     = dirName + "accountlist.ser";
    String vehicleFile     = dirName + "vehiclelist.ser";
    String scanningFile    = dirName + "scanninglist.ser";
    String garageFile      = dirName + "garagelist.ser";
    String parkingSlotFile = dirName + "parkingslotlist.ser";


    public GarageAdmin() {

        LoadReloadData();

        //if (userList == null) {
        User user = new User("Super", "User", "007", "admin@mybank.se", "SuperUser", "SuperUser");
        userList.put(user.getKey(), user);
        //FileIO.writeObject(userList, userFile);
        //}
    }


    private void LoadReloadData() {

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

            Map<String, Vehicle> tempVehicleList = FileIO.readObject(accountFile);
            if (tempVehicleList != null)
                vehicleList = tempVehicleList;

            Map<String, ParkingSlot> tempParkingSlotList = FileIO.readObject(accountFile);
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
        return (userList.containsKey(username) && userList.get(username).getPassword().equals(password));
    }


    public void start() {

        if (!login()) return;

        String answer = "";
        do {
            answer = menu();
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
                    case "12":
                        createGarage();
                        break;
                    case "13":
                        listGarage();
                        break;
                    case "14":
                        maintainParkingSlots();
                        break;
                    case "15":
                        listParkingSlots();
                        break;
                    case "16":
                        scanParking();
                        break;
                    case "17":
                        listParkings();
                        break;
                    //case "17":
                    //    excludeOrOpeVehicle(true);
                    //    break;
                    //case "18":
                    //    excludeOrOpeVehicle(false);
                    //    break;
                    case "80":

                        Mc    myMc    = new Mc   ("ABC000",customerList.get("189901011111"));
                        System.out.println(myMc +" "+ countNoOfFreeSlots(garageList.get("DROTTNINGGATAN 8"),myMc.getSize()));

                        Car   myCar   = new Car  ("ABC111",customerList.get("189901011111"));
                        System.out.println(myCar +" "+ countNoOfFreeSlots(garageList.get("DROTTNINGGATAN 8"),myCar.getSize()));

                        Truck myTruck = new Truck("ABC222",customerList.get("189901011111"));
                        System.out.println(myTruck +" "+ countNoOfFreeSlots(garageList.get("DROTTNINGGATAN 8"),myTruck.getSize()));

                        ArticulatedLorry myLorry =
                                new ArticulatedLorry("ABC333",customerList.get("189901011111"),2);
                        System.out.println(myLorry +" "+ countNoOfFreeSlots(garageList.get("DROTTNINGGATAN 8"),myLorry.getSize()));

                        break;
                    case "88":

                        Customer customer = new Customer("Kalle", "Anka", "189901011111", "kalle.anka@ankeborg.com", "KALLEANKA");
                        customerList.put(customer.getKey(), customer);

                        User user = new User(customer.getFirstName(), customer.getLastName(), customer.getBarCode(), customer.getEmail(), customer.getUserName(), "KalleAnkaÄrBäst");
                        userList.put(user.getKey(), user);

                        Account account = new Account(customer, "12345678901234567890", 0.0, "Kalles head account");
                        accountList.put(account.getKey(), account);

                        Garage garage = new Garage ("DROTTNINGGATAN 8","Litet garage");
                        garageList.put(garage.getKey(), garage);
                        Floor floor = new Floor(1,"1 bil plats plus två mc platser");
                        ParkingSlot slotCar1 = new ParkingSlot (garage,floor,1,Vehicle.Size.MEDIUM);
                        parkingSlotList.put(slotCar1.getKey(),slotCar1);

                        ParkingSlot slotMc1  = new ParkingSlot (garage,floor,1,Vehicle.Size.SMALL);
                        parkingSlotList.put(slotMc1.getKey(),slotMc1);

                        ParkingSlot slotMc2  = new ParkingSlot (garage,floor,1,Vehicle.Size.SMALL);
                        parkingSlotList.put(slotMc2.getKey(),slotMc2);

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
                        listParkings();
                        System.out.println("");
                        listGarages();System.out.println("");
                        //listFloors();System.out.println("");
                        listParkingSlots();
                        System.out.println("");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                StdIO.ErrorReport("Exception -" + e.toString());
            }
        } while (!answer.equalsIgnoreCase("q"));

        if (accountList != null)
            FileIO.writeObject(accountList, accountFile);
        if (customerList != null)
            FileIO.writeObject(customerList, customerFile);
        if (scanningList != null)
            FileIO.writeObject(scanningList, scanningFile);
        if (userList != null)
            FileIO.writeObject(userList, userFile);
        if (parkingSlotList != null)
            FileIO.writeObject(parkingSlotList, parkingSlotFile);
        //if (atmHwList != null)
        //    FileIO.writeObject(atmHwList,atmHwFile);
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
            StdIO.writeLine("15. Scan Parking ");
            StdIO.writeLine("16. List Parking");
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
        try {
            StdIO.clearScreen();
            StdIO.writeLine("Scan Parking");


        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    //private String TransactionToString (Scannings trans) {
    //    return trans.toString();
    //}
    public String listAllTransactions() {
        String str = "";
        for (Scannings x : scanningList) {
            str += x.toString() + "\r\n";
        }
        return str;
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
            StdIO.write("User name : ");
            String userName = StdIO.readLine();

            Customer customer = new Customer(firstName, lastName, barcode, email, userName);

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

    private void listParkings() {
        System.out.println(Scannings.toStringHeader());
        if (scanningList != null)
            scanningList.stream().forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listUsers() {
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

    private void listVehicles() {
        System.out.println(Vehicle.toStringHeader());
        if (vehicleList != null)
            for (Vehicle x : vehicleList.values() )
                System.out.println(x.toStringLine());
            //vehicleList.forEach((k, v) -> System.out.println(v.toStringLine()));
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
            String regNo = StdIO.readLine().trim();

            StdIO.write("Needs size (SMALL,MEDIUM,LARGE,HUGE): ");
            Vehicle.Size size = Vehicle.Size.valueOf(StdIO.readLine().toUpperCase());

            if (vehicleList.containsKey(regNo)) {
                vehicle = vehicleList.get(regNo);
                if (vehicle.getCustomer().getBarCode() != customer.getBarCode()) {
                    StdIO.ErrorReport("The customer can not be changed");
                    return;
                } else
                    vehicle.setCustomer(customer);

            } else {

                vehicle = new Vehicle(regNo, size, customer);
            }

            vehicleList.put(vehicle.getKey(), vehicle);

            FileIO.writeObject(vehicleList, vehicleFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void listParkingSlots() {
        System.out.println(ParkingSlot.toStringHeader());
        if (parkingSlotList != null)
            parkingSlotList.forEach((k, v) -> System.out.println(v.toStringLine()));
    }

    private int countNoOfFreeSlots (Garage garage, Vehicle.Size withSize) {
        int noOfItems = 0;

        for (ParkingSlot x : parkingSlotList.values())
            if (garage.getKey().equals(x.getGarage().getKey()) && x.isFree() && x.getSize() == withSize ) {
                noOfItems++;
            }

        return noOfItems;
    }

    private ParkingSlot getNextFree (Garage garage, Vehicle.Size withSize) {

        for (ParkingSlot x : parkingSlotList.values()) {
            if (garage.getKey().equals(x.getGarage().getKey()) && x.isFree() && x.getSize() == withSize) {
                return x;
            }
        }

        throw new IllegalCallerException("Full, no free slot exists with the requeste size");
    }

    private void listFilteredItem(String name, ParkingSlot v) {
        if (name.equalsIgnoreCase(v.getGarage().getName()))
            System.out.println(v.toStringLine());
    }

    private void listGarage(Garage garage) {

        System.out.println(garage.getName() + " "+ garage.getDescription());
        System.out.println();
        System.out.println(ParkingSlot.toStringHeader());
        if (parkingSlotList != null)
            parkingSlotList.forEach((k, v) -> listFilteredItem(garage.getName(), v));
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

    private void listGarages() {
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

                for (int j = 0; j < noOfSlots; j++) {

                    ParkingSlot parkingSlot = new ParkingSlot(garage, floor, i, slotSize);

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
