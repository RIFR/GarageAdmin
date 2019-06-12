package se.rifr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import se.rifr.domain.*;
import se.rifr.domain.vehicles.*;
import se.rifr.dao.*;
import se.rifr.support.FileIO;
import se.rifr.support.StdIO;

import java.time.LocalDateTime;
import java.util.*;

public class GarageAdmin {

    //Pointers to interface Implementations (if set)
    private final UserDao        userDao;
    private final CustomerDao    customerDao;
    private final AccountDao     accountDao;
    private final VehicleDao     vehicleDao;
    private final GarageDao      garageDao;
    private final ParkingSlotDao parkingSlotDao;

    //private Map<String, User> userList = new HashMap<>();
    //private Map<String, Customer> customerList = new HashMap<>();
    //private Map<String, Account> accountList = new HashMap<>();
    //private Map<String, Vehicle> vehicleList = new HashMap<>();
    //private Map<String, ParkingSlot> parkingSlotList = new HashMap<>();
    //private Map<String, Garage> garageList = new HashMap<>();
    private List<Scannings> scanningList = new ArrayList<>();
    //private List<Floor> floorList                    = new ArrayList<>();
    //private List<Garage> garageList                  = new ArrayList<>();

    private String dirName = "/var/opt/GarageAdminStorage/";

    private String userFile        = dirName + "userlist.ser";
    private String customerFile    = dirName + "customerlist.ser";
    private String accountFile     = dirName + "accountlist.ser";
    private String vehicleFile     = dirName + "vehiclelist.ser";
    private String scanningFile    = dirName + "scanninglist.ser";
    private String garageFile      = dirName + "garagelist.ser";
    private String parkingSlotFile = dirName + "parkingslotlist.ser";


    public GarageAdmin(String args[]) {

        ApplicationContext applicationContext = null;

        for (String x : args) {

            int nextIx = x.indexOf("=") + 1;

            switch (x.toUpperCase().substring(0,nextIx)) {
                case "CONF=" :
                    if (!x.substring(nextIx).isEmpty())
                        applicationContext = new GenericXmlApplicationContext(x.substring(nextIx));
                case "STORAGE=" :
                    if (!x.substring(nextIx).isEmpty())
                        this.dirName = x.substring(nextIx);
            }
        }

        if (applicationContext == null) {

            System.out.println("USING DEFAULT CONFIGURATION");
            // use default
            this.userDao        = new UserDaoImpl();
            this.customerDao    = new CustomerDaoImpl();
            this.accountDao     = new AccountDaoImpl();
            this.vehicleDao     = new VehicleDaoImpl();
            this.garageDao      = new GarageDaoImpl();
            this.parkingSlotDao = new ParkingSlotDaoImpl();

        } else {

            this.userDao        = applicationContext.getBean(UserDao.class);
            this.customerDao    = applicationContext.getBean(CustomerDao.class);
            this.accountDao     = applicationContext.getBean(AccountDao.class);
            this.vehicleDao     = applicationContext.getBean(VehicleDao.class);
            this.garageDao      = applicationContext.getBean(GarageDao.class);
            this.parkingSlotDao = applicationContext.getBean(ParkingSlotDao.class);
        }

        LoadReloadData();

    }

    public void SaveAllData() {

        userDao.stop();
        //if (userList        != null) FileIO.writeObject(userList, userFile);

        customerDao.stop();
        //if (customerList    != null) FileIO.writeObject(customerList, customerFile);

        accountDao.stop();
        //if (accountList     != null) FileIO.writeObject(accountList, accountFile);

        vehicleDao.stop();
        //if (vehicleList     != null) FileIO.writeObject(vehicleList, vehicleFile);

        garageDao.stop();
        //if (garageList      != null) FileIO.writeObject(garageList, garageFile);

        parkingSlotDao.stop();
        //if (parkingSlotList != null) FileIO.writeObject(parkingSlotList, parkingSlotFile);

        if (scanningList    != null) FileIO.writeObject(scanningList, scanningFile);

    }

    public void LoadReloadData() {

        try {
            userDao.start(userFile);
//            Map<String, User> tempUserList = FileIO.readObject(userFile);
//            if (tempUserList != null)
//                userList = tempUserList;

            customerDao.start(customerFile);
//            Map<String, Customer> tempCustomerList = FileIO.readObject(customerFile);
//            if (tempCustomerList != null)
//                customerList = tempCustomerList;

            accountDao.start(accountFile);
//            Map<String, Account> tempAccountList = FileIO.readObject(accountFile);
//            if (tempAccountList != null)
//                accountList = tempAccountList;

            vehicleDao.start(vehicleFile);
//            Map<String, Vehicle> tempVehicleList = FileIO.readObject(vehicleFile);
//            if (tempVehicleList != null)
//                vehicleList = tempVehicleList;

            garageDao.start(garageFile);
            //Map<String, Garage> tempGarageList= FileIO.readObject(garageFile);
            //if (tempGarageList != null)
            //    garageList = tempGarageList;

            parkingSlotDao.start(parkingSlotFile);
//            Map<String, ParkingSlot> tempParkingSlotList = FileIO.readObject(parkingSlotFile);
//            if (tempParkingSlotList != null)
//                parkingSlotList = tempParkingSlotList;

            List<Scannings> tempScanningList = FileIO.readObject(scanningFile);
            if (tempScanningList != null)
                scanningList = tempScanningList;

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
        return userDao.read(username).isPresent() && userDao.read(username).get().getPassword().equals(password);
    }
//    public boolean loginOk(String username, String password) {
//        return userList.containsKey(username) && userList.get(username).getPassword().equals(password);
//    }

    public boolean customerExists(String customerBarcode) {
        return customerDao.read(customerBarcode.toUpperCase()).isPresent();
        //return customerList.containsKey(customerBarcode.toUpperCase());
    }

    public Customer getCustomer(String barcode) {
        return customerDao.read(barcode).get();
    }
    public void createCustomer(String firstName,String lastName,String barcode,String email,String telno) {

        Customer customer = new Customer.Builder()
                .withFirstName(firstName)
                .withLastName(lastName)
                .withBarCode(barcode)
                .withemail(email)
                .withTelephoneNumber(telno)
                .withUserName(firstName.substring(0,1).toUpperCase()+lastName.substring(0,1).toUpperCase())
                .build();

        customerDao.maintain(customer);

        //customerList.put(customer.getKey(),customer);
        //FileIO.writeObject(customerList, customerFile);
    }

    public boolean isRegistered(String vehicleBarcode) {
         return vehicleDao.read(vehicleBarcode.toUpperCase()).isPresent();
    }

    public boolean isParked(String vehicleBarcode) {
        return isRegistered(vehicleBarcode) && getSlot (vehicleDao.read(vehicleBarcode.toUpperCase()).get())!= null;
    }

    public Vehicle getVehicle(String vehicleBarcode) {
        if (isRegistered(vehicleBarcode)) return vehicleDao.read(vehicleBarcode.toUpperCase()).get();
        else throw new IllegalArgumentException("Vehicle "+vehicleBarcode+" not found");
    }

    public void createVehicle(String regNo, String kind, String model, String colour, Customer customer) {
        Vehicle vehicle;
        switch (kind.toUpperCase()){
            case "MC" :
                vehicle = new Mc.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withSize(Vehicle.Size.SMALL)
                        .withCustomer(customer).build();
                break;
            case "CAR" :
                vehicle = (new Car.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withSize(Vehicle.Size.MEDIUM)
                        .withCustomer(customer)).build();
                break;
            case "TRUCK" :
                vehicle = new Truck.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withSize(Vehicle.Size.LARGE)
                        .withCustomer(customer).build();
                break;
            case "LORRY" :
                vehicle = new Lorry.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withSize(Vehicle.Size.LARGE)
                        .withCustomer(customer).build();
                 break;
            case "BUS" :
                vehicle = new Bus.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withSize(Vehicle.Size.LARGE)
                        .withCustomer(customer).build();
                break;
            case "JUGGERNAUT" :
                vehicle = new Juggernaut.Builder()
                        .withBarcode(regNo.toUpperCase())
                        .withModel(model.toUpperCase())
                        .withColour(colour.toUpperCase())
                        .withSize(Vehicle.Size.HUGE)
                        .withCustomer(customer).build();
                break;
            default:
                throw new IllegalArgumentException("UNKNOWN VEHICLE TYPE " +kind);
//                vehicles = new Vehicle.Builder()
//                        .withBarcode(regNo.toUpperCase())
//                        .withModel(model.toUpperCase())
//                        .withColour(colour.toUpperCase())
//                        .withCustomer(customer).build();
         }

         vehicleDao.maintain(vehicle);

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

    public boolean garageExists(String name) {
        return (garageDao.read(name.toUpperCase())).isPresent();
    }
    //public boolean garageExists(String name) { return (garageList.containsKey(name.toUpperCase()));}

    public Garage getGarage(String name) {
        return garageDao.read(name.toUpperCase()).get();
    }
//    public Garage getGarage(String name) {
//        if (garageList.containsKey(name)) return garageList.get(name);
//        else throw new IllegalArgumentException("Garage "+name+" not found");
//    }

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
                    Garage myGarage = garageDao.read("DROTTNINGGATAN 8"+StdIO.readLine().toUpperCase()).get();
                    //Garage myGarage = garageList.get("DROTTNINGGATAN 8"+StdIO.readLine().toUpperCase());

                    System.out.println("No of free slots "+ countFreeSlotsInTheGarage(myGarage));

                    System.out.print("Reg Nos (3 char)");
                    String regNoP0 = StdIO.readLine();

                    Customer customer80 = customerDao.read("189901011111").get();

                    Mc myMc = new Mc.Builder()
                            .withBarcode(regNoP0+"000".toUpperCase())
                            .withModel("YAMAHA")
                            .withColour("RED")
                            .withNoOfWheels(2)
                            .withNoiseLevel(7)
                            .withFuel("GASOLINE")
                            .withSize(Vehicle.Size.SMALL)
                            .withCustomer(customer80)
                            .build();

                    vehicleDao.maintain(myMc);

                    String temp = parkVehicle(myMc,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myMc);
                    else
                        System.out.println("No slot found for "+myMc);

                    Car myCar = new Car.Builder()
                            .withBarcode(regNoP0+"111".toUpperCase())
                            .withModel("VOLVO XC60")
                            .withColour("BRAUN")
                            .withNoOfWheels(4)
                            .withNoiseLevel(3)
                            .withFuel("DIESEL")
                            .withSize(Vehicle.Size.MEDIUM)
                            .withCustomer(customer80)
                            .build();

                    vehicleDao.maintain(myCar);

                    temp = parkVehicle(myCar,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myCar);
                    else
                        System.out.println("No slot found for "+myCar);

                    Truck myTruck = new Truck.Builder()
                            .withBarcode(regNoP0+"222".toUpperCase())
                            .withModel("VOLVO")
                            .withColour("BLUE")
                            .withNoOfWheels(4)
                            .withNoiseLevel(6)
                            .withFuel("DIESEL")
                            .withSize(Vehicle.Size.LARGE)
                            .withCustomer(customer80)
                            .build();

                    vehicleDao.maintain(myTruck);

                    temp = parkVehicle(myTruck,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myTruck);
                    else
                        System.out.println("No slot found for "+myTruck);

                    Lorry myLorry = new Lorry.Builder()
                            .withBarcode(regNoP0+"333".toUpperCase())
                            .withModel("VOLVO")
                            .withColour("GREEN")
                            .withNoOfWheels(6)
                            .withNoiseLevel(5)
                            .withFuel("DIESEL")
                            .withSize(Vehicle.Size.LARGE)
                            .withCustomer(customer80)
                            .build();

                    vehicleDao.maintain(myLorry);

                    temp = parkVehicle(myLorry,myGarage);
                    if (!temp.isEmpty())
                        System.out.println("Slot "+temp+" found for "+myLorry);
                    else
                        System.out.println("No slot found for "+myLorry);

                    Juggernaut myJuggernaut = new Juggernaut.Builder()
                            .withBarcode(regNoP0+"444".toUpperCase())
                            .withModel("VOLVO")
                            .withColour("YELLOW")
                            .withNoOfWheels(8)
                            .withNoiseLevel(7)
                            .withFuel("DIESEL")
                            .withSize(Vehicle.Size.HUGE)
                            .withCustomer(customer80)
                            .withNoOfBeds(2)
                            .build();

                    vehicleDao.maintain(myJuggernaut);

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

                    unparkVehicle(vehicleDao.read(regNoP1+"000").get());
                    unparkVehicle(vehicleDao.read(regNoP1+"111").get());
                    unparkVehicle(vehicleDao.read(regNoP1+"222").get());
                    unparkVehicle(vehicleDao.read(regNoP1+"333").get());
                    unparkVehicle(vehicleDao.read(regNoP1+"444").get());

                    break;
                case "88":

                    Customer kalleAnka = new Customer.Builder()
                            .withFirstName("Kalle")
                            .withLastName("Anka")
                            .withBarCode("189901011111")
                            .withemail( "kalle.anka@ankeborg.com")
                            .withTelephoneNumber("+46707155733")
                            .withUserName("KALLEANKA")
                            .build();

                    customerDao.maintain(kalleAnka);

                    User user = new User.Builder()
                            .withFirstName(kalleAnka.getFirstName())
                            .withLastName(kalleAnka.getLastName())
                            .withBarCode(kalleAnka.getBarCode())
                            .withemail(kalleAnka.getEmail())
                            .withUserName(kalleAnka.getUserName())
                            .withPassword("KalleAnkaÄrBäst").build();

                    userDao.maintain(user);

                    Account account = new Account.Builder()
                            .withCustomer(kalleAnka)
                            .withBankId(kalleAnka.getBarCode()+"-1")
                            .withDescription("Kalle AB current account")
                            .withSaldo(500.0).build();

                    accountDao.maintain(account);

                    //----------------------------------------------------------------------------------------

                    Garage garage1 = new Garage ("DROTTNINGGATAN 8A","Litet garage");
                    garageDao.maintain(garage1);

                    Floor floor = new Floor(1,"1 bil plats plus två mc platser");

                    ParkingSlot slotCar1 = new ParkingSlot.Builder()
                            .withGarage(garage1)
                            .withFloor(floor)
                            .withPlaceNo(1)
                            .withSize(Vehicle.Size.MEDIUM)
                            .withFee(20.0d)
                            .build();

                    parkingSlotDao.maintain(slotCar1);
                    //parkingSlotList.put(slotCar1.getKey(),slotCar1);

                    ParkingSlot slotMc1 = new ParkingSlot.Builder()
                            .withGarage(garage1)
                            .withFloor(floor)
                            .withPlaceNo(2)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(5.0d)
                            .build();

                    parkingSlotDao.maintain(slotMc1);
                    //parkingSlotList.put(slotMc1.getKey(),slotMc1);

                    ParkingSlot slotMc2 = new ParkingSlot.Builder()
                            .withGarage(garage1)
                            .withFloor(floor)
                            .withPlaceNo(3)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(5.0d)
                            .build();

                    parkingSlotDao.maintain(slotMc2);
                    //parkingSlotList.put(slotMc2.getKey(),slotMc2);

                    //----------------------------------------------------------------------------------------

                    Garage garage2 = new Garage
                            ("DROTTNINGGATAN 8B","Lite större garage, tre våningsplan");
                    garageDao.maintain(garage2);

                    Floor floor1 = new Floor(1,"1 bil plats plus två mc platser");

                    ParkingSlot slot11 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor1)
                            .withPlaceNo(1)
                            .withSize(Vehicle.Size.MEDIUM)
                            .withFee(24.0d)
                            .build();

                    parkingSlotDao.maintain(slot11);
                    //ParkingSlot slot11 = new ParkingSlot (garage2,floor1,1,Vehicle.Size.MEDIUM,24.0d);
                    //parkingSlotList.put(slot11.getKey(),slot11);

                    ParkingSlot slot12 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor1)
                            .withPlaceNo(2)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(6.0d)
                            .build();

                    parkingSlotDao.maintain(slot12);
                    //ParkingSlot slot12  = new ParkingSlot (garage2,floor1,2,Vehicle.Size.SMALL,6.0d);
                    //parkingSlotList.put(slot12.getKey(),slot12);

                    ParkingSlot slot13 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor1)
                            .withPlaceNo(3)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(6.0d)
                            .build();

                    parkingSlotDao.maintain(slot13);
                    //ParkingSlot slot13  = new ParkingSlot (garage2,floor1,3,Vehicle.Size.SMALL,6.0d);
                    //parkingSlotList.put(slot13.getKey(),slot13);

                    Floor floor2 = new Floor(2,"plats för 3 mc, 2 bil, 1 Truck  platser");

                    ParkingSlot slot21 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(1)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(6.0d)
                            .build();

                    parkingSlotDao.maintain(slot21);
                    //ParkingSlot slot21  = new ParkingSlot (garage2,floor2,1,Vehicle.Size.SMALL,6.0d);
                    //parkingSlotList.put(slot21.getKey(),slot21);

                    ParkingSlot slot22 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(2)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(6.0d)
                            .build();

                    parkingSlotDao.maintain(slot22);
                    //ParkingSlot slot22  = new ParkingSlot (garage2,floor2,2,Vehicle.Size.SMALL,6.0d);
                    //parkingSlotList.put(slot22.getKey(),slot22);

                    ParkingSlot slot23 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(3)
                            .withSize(Vehicle.Size.SMALL)
                            .withFee(6.0d)
                            .build();

                    parkingSlotDao.maintain(slot23);
                    //ParkingSlot slot23  = new ParkingSlot (garage2,floor2,3,Vehicle.Size.SMALL,6.0d);
                    //parkingSlotList.put(slot23.getKey(),slot23);

                    ParkingSlot slot24 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(4)
                            .withSize(Vehicle.Size.MEDIUM)
                            .withFee(24.0d)
                            .build();

                    parkingSlotDao.maintain(slot24);
                    //ParkingSlot slot24  = new ParkingSlot (garage2,floor2,4,Vehicle.Size.MEDIUM,24.0d);
                    //parkingSlotList.put(slot24.getKey(),slot24);

                    ParkingSlot slot25 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(5)
                            .withSize(Vehicle.Size.MEDIUM)
                            .withFee(24.0d)
                            .build();

                    parkingSlotDao.maintain(slot25);
                    //ParkingSlot slot25  = new ParkingSlot (garage2,floor2,5,Vehicle.Size.MEDIUM,24.0d);
                    //parkingSlotList.put(slot25.getKey(),slot25);

                    ParkingSlot slot26 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor2)
                            .withPlaceNo(6)
                            .withSize(Vehicle.Size.LARGE)
                            .withFee(48.0d)
                            .build();

                    parkingSlotDao.maintain(slot26);
                    //ParkingSlot slot26  = new ParkingSlot (garage2,floor2,6,Vehicle.Size.LARGE,48.0d);
                    //parkingSlotList.put(slot26.getKey(),slot26);

                    Floor floor3 = new Floor(3,"plats för 2 långtradare platser");

                    ParkingSlot slot31 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor3)
                            .withPlaceNo(1)
                            .withSize(Vehicle.Size.HUGE)
                            .withFee(96.0d)
                            .build();

                    parkingSlotDao.maintain(slot31);
                    //ParkingSlot slot31  = new ParkingSlot (garage2,floor3,1,Vehicle.Size.HUGE,96.0d);
                    //parkingSlotList.put(slot31.getKey(),slot31);

                    ParkingSlot slot32 = new ParkingSlot.Builder()
                            .withGarage(garage2)
                            .withFloor(floor3)
                            .withPlaceNo(2)
                            .withSize(Vehicle.Size.HUGE)
                            .withFee(96.0d)
                            .build();

                    parkingSlotDao.maintain(slot32);
                    //ParkingSlot slot32  = new ParkingSlot (garage2,floor3,2,Vehicle.Size.HUGE,96.0d);
                    //parkingSlotList.put(slot32.getKey(),slot32);

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
                    listGarages();
                    System.out.println("");
                    //listParkingSlots();
                    //System.out.println("");
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
        //Vehicle vehicle;
        Garage garage;
        boolean entering;
        try {
            StdIO.clearScreen();
            StdIO.writeLine("Scan Parking");

            StdIO.writeLine("Vehicle barcode");
            String barcode = StdIO.readLine().toUpperCase();

            Optional <Vehicle> optVehicle = vehicleDao.read(barcode);
            if (!optVehicle.isPresent()) {
                StdIO.ErrorReport("Unknown vehicles "+barcode);
                return;
            }

            StdIO.writeLine("Entering (y/n)");
            entering = StdIO.readYesOrNo();

            if (entering) {
                StdIO.writeLine("Garage name");
                String garageName = StdIO.readLine().toUpperCase();

                Optional<Garage> optGarage = garageDao.read(garageName);
                if (optGarage.isPresent()) {
                    garage = optGarage.get();
                    park(optVehicle.get(),garage);
                } else {
                    StdIO.ErrorReport("Unknown garage "+garageName);
                    return;
                }
            }else {
                ParkingSlot slot = unpark(optVehicle.get());
                garage = slot.getGarage();
            }

            // Store the updated scanning
            Scannings scanning = new Scannings(optVehicle.get(), LocalDateTime.now(), entering, garage);
            scanningList.add(scanning);
            FileIO.writeObject(scanningList, scanningFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

     public ParkingSlot park (Vehicle vehicle, Garage inGarage){

         ParkingSlot slot = getSlot (vehicle);
         if (slot != null) {
             if (!slot.getGarage().getKey().equals(inGarage.getKey())) {
                 StdIO.ErrorReport("Ended parking for "+vehicle.getBarcode() +" in "+ slot.getGarage().getName());
                 unpark (vehicle);

             } else {
                 StdIO.ErrorReport("Already parked "+vehicle.getBarcode()+" in "+ slot.getGarage().getName());
                 return null;
             }
         }

        slot = getNextFree (inGarage,vehicle.getSize());
        if (slot != null) {

            slot.setParked (vehicle);

            parkingSlotDao.maintain(slot);
            //parkingSlotList.put(slot.getKey(),slot);
            //FileIO.writeObject(parkingSlotList, parkingSlotFile);
        }

        return slot;
    }

    public ParkingSlot getSlot (Vehicle vehicle){

        for (ParkingSlot slot : parkingSlotDao.readAllParkingSlots()) {
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
        parkingSlotDao.maintain(slot);
        //parkingSlotList.put(slot.getKey(),slot);
        //FileIO.writeObject(parkingSlotList, parkingSlotFile);

        Optional<Account> optAccount = accountDao.read(vehicle.getCustomer().getBarCode());

         if(optAccount.isPresent()){
             //Account account =optAccount.get();

             optAccount.get().updateParkedTime(minutes);
             optAccount.get().changeSaldo(slot.getFeePerMinute()*minutes,false);
             accountDao.maintain(optAccount.get());
        } else
            System.out.println("The "+vehicle+" was parked for "+minutes+
                    " minutes, since the owner did not have any account he/she need to pay the fee now");
        //" minutes, since the owner did not have any account he/she need to pay the fee of "+
        //        slot.getGarage().getFeePerMinute()*minutes +" now");

        return slot;
    }

    private void listCustomers() {
        customerDao.printOut();
//        System.out.println(Customer.toStringHeader());
//        if (customerList != null)
//            customerList.forEach((k, v) -> System.out.println(v.toStringLine()));
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

            Customer customer = new Customer.Builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withBarCode(barcode)
                    .withemail(email)
                    .withTelephoneNumber(telephoneNo)
                    .withUserName(userName)
                    .build();

            customerDao.maintain(customer);
//            customerList.put(customer.getKey(), customer);
//            FileIO.writeObject(customerList, customerFile);

            updateAccountdWithUpdatedCustomer(customer); // update if exists in accounts

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void updateAccountdWithUpdatedCustomer(Customer customer) {
        for (Account x : accountDao.readAllAccounts()) {
            if (x.getCustomer().getKey() == customer.getKey()) {

                x.setCustomer(customer);
                accountDao.maintain(x);

            }
        }
    }

    public void listScannings() {
        System.out.println(Scannings.toStringHeader());
        if (scanningList != null)
            scanningList.stream().forEach(item -> System.out.println(item.toStringLine()));
    }

    public void listUsers() {
        userDao.printOut();
//        System.out.println(User.toStringHeader());
//        if (userList != null)
//            userList.values().stream()
//                    .filter(item -> !item.getKey().equals("SuperUser"))
//                    .forEach((item -> System.out.println(item.toStringLine())));
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

            userDao.maintain(new User.Builder()
                    .withFirstName(firstName)
                    .withLastName(lastName)
                    .withBarCode(barcode)
                    .withemail(email)
                    .withUserName(userName)
                    .withPassword(password).build());

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void copyCustomerDataToUser() {

        try {

            StdIO.write("Barcode   : ");
            String barcode = StdIO.readLine().trim();

            Optional<Customer> optCustomer = customerDao.read(barcode);
            if (!optCustomer.isPresent()) {
                StdIO.ErrorReport("Unknown Customer " + barcode);
                return;
            }

            String userName = optCustomer.get().getUserName();
            while (userDao.read(userName).isPresent()) {
                StdIO.ErrorReport("User Name " + optCustomer.get().getUserName() + " already exists, enter new");
                userName = StdIO.readLine().trim();
            }

            optCustomer.get().setUserName(userName);
            customerDao.maintain(optCustomer.get());

            StdIO.write("Password   : ");
            String password = StdIO.readLine().trim();

            userDao.maintain(new User.Builder()
                    .withFirstName(optCustomer.get().getFirstName())
                    .withLastName(optCustomer.get().getLastName())
                    .withBarCode(optCustomer.get().getBarCode())
                    .withemail(optCustomer.get().getEmail())
                    .withUserName(optCustomer.get().getUserName())
                    .withPassword(password).build());

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void deleteUser() {
        try {
            StdIO.write("User Name : ");
            String userName = StdIO.readLine().toUpperCase();

            Optional <User> optUser = userDao.read(userName);

            if (optUser.isPresent()) {
                StdIO.writeLine("Confirm deletion for "
                        + optUser.get().getFirstName() + " " + optUser.get().getLastName() + " " + optUser.get().getBarcode() + "(y/n)");
                if (StdIO.readYesOrNo()) {
                    userDao.delete(optUser.get());
                }
            } else
                StdIO.ErrorReport("Unknown User "+userName);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }


    private void listAccounts() {
        accountDao.printOut();
    }

    private void maintainAccounts() {
        Account account;
        double saldo;
        try {
            //Customer customer;

            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add an Account");

            StdIO.writeLine("");
            StdIO.writeLine("Customer barcode");
            String barcode = StdIO.readLine();

            Optional<Customer> optCustomer = customerDao.read(barcode);

            if (!optCustomer.isPresent()) {
                System.out.println("Unknown customer " + barcode);
                return;
            }

            String accountID = barcode + "-1";

            StdIO.writeLine("");
            //StdIO.write("Account Id  : ");
            //String accountID = StdIO.readLine().trim();
            StdIO.write("Description : ");
            String description = StdIO.readLine();

            Optional<Account> optAccount = accountDao.read(accountID);

            if(optAccount.isPresent()) {
                if (!optAccount.get().getCustomer().getBarCode().equals(optCustomer.get().getBarCode())) {
                    StdIO.ErrorReport("The customer can not be changed");
                    return;
                }

                optAccount.get().setDescription(description);
                optAccount.get().setCustomer(optCustomer.get());

                accountDao.maintain(optAccount.get());

            }else {
                StdIO.write("Saldo       : ");
                saldo = Double.valueOf(StdIO.readLine());

                account = new Account.Builder()
                        .withCustomer(optCustomer.get())
                        .withBankId(accountID)
                        .withDescription(description)
                        .withSaldo(saldo).build();

                accountDao.maintain(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listVehicles() { vehicleDao.printOut(); }

    private void maintainVehicles() {
        Vehicle vehicle;
        double saldo;
        try {

            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Add a Vehicle");

            StdIO.writeLine("");
            StdIO.writeLine("Customer barcode");
            String barcode = StdIO.readLine();

            Optional<Customer> optCustomer = customerDao.read(barcode);

            if (!optCustomer.isPresent()) {
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

            // Create and store
            if (!vehicleDao.read(regNo).isPresent()) {createVehicle(regNo, kind, model, colour, optCustomer.get()); }

            vehicle = vehicleDao.read(regNo).get();

            vehicle.setModel(model);
            vehicle.setColour(colour);
            vehicle.setNoOfWheels(noOfWheels);
            vehicle.setNoiseLevel(noiseLevel);
            vehicle.setFuel(fuel);
            vehicle.setCustomer(optCustomer.get());

            switch (vehicle.getClass().getSimpleName().toUpperCase().trim()) {
                case "BUS" :
                    StdIO.write("No Of Seats: ");
                    int noOfSeats = Integer.valueOf(StdIO.readLine());

                    Bus bus = (Bus)vehicle;
                    bus.setNoOfSeats(noOfSeats);

                    vehicleDao.maintain(bus);
                    return;
                case "JUGGERNAUT" :
                    StdIO.write("No Of Beds: ");
                    int noOfBeds = Integer.valueOf(StdIO.readLine());

                    Juggernaut juggernaut = (Juggernaut)vehicle;
                    juggernaut.setNoOfBeds(noOfBeds);

                    vehicleDao.maintain(juggernaut);
                    return;
            }

            vehicleDao.maintain(vehicle);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listParkingSlots() {
        parkingSlotDao.printOut();
//        System.out.println(ParkingSlot.toStringHeader());
//
//        parkingSlotList.values().stream()
//                //.sorted()
//                .sorted(Comparator.comparing(item -> item.getPlaceNo()))
//                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
//                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
//                .forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listFreeParkingSlots() {

        try {
            StdIO.write("Garage Name : ");
            String name = StdIO.readLine().toUpperCase();

            parkingSlotDao.printOut(parkingSlotDao.readFreeParkingSlots(name));

//            System.out.println(ParkingSlot.toStringHeader());
//
//            parkingSlotList.values().stream()
//                    .filter(item -> item.getGarage().getName().equals(name))
//                    .filter(item -> item.isFree())
//                    .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
//                    .sorted(Comparator.comparing(item -> item.getSize()))
//                    .forEach(item -> System.out.println(item.toStringLine()));

            System.out.println();
            System.out.println("Total no of free slots "+countFreeSlotsInTheGarage(garageDao.read(name).get()));

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void listAllFreeParkingSlots() {

        parkingSlotDao.printOut(parkingSlotDao.readAllFreeParkingSlots());

//        System.out.println(ParkingSlot.toStringHeader());
//
//        parkingSlotList.values().stream()
//                .filter(item -> item.isFree())
//                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
//                .sorted(Comparator.comparing(item -> item.getSize()))
//                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
//                .forEach(item -> System.out.println(item.toStringLine()));

        System.out.println();
        System.out.println("Total no of free slots "+countAllFreeSlots());
    }


    private int countNoOfFreeSlots (Garage garage, Vehicle.Size withSize) {
        int noOfItems = 0;

        for (ParkingSlot x : parkingSlotDao.readFreeParkingSlots(garage.getName()))
            if (x.getSize() == withSize ) {
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

        for (Garage garage : garageDao.readAllGarages())
            noOfItems += countFreeSlotsInTheGarage (garage);

        return noOfItems;
    }

    private ParkingSlot getNextFreeWithSize (Garage garage, Vehicle.Size withSize) {

        for (ParkingSlot x : parkingSlotDao.readFreeParkingSlots(garage.getName())) {
            if (x.getSize() == withSize && x.getFeePerMonth() == 0.0d) { return x; }
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
        parkingSlotDao.printOut(parkingSlotDao.readParkingSlots(garage.getName()));
//        System.out.println(ParkingSlot.toStringHeader());
//        parkingSlotList.values().stream()
//                .filter(item -> item.getGarage().getName().equals(garage.getName()))
//                .sorted(Comparator.comparing(item -> item.getPlaceNo()))
//                .sorted(Comparator.comparing(item -> item.getFloor().getLevel()))
//                .sorted(Comparator.comparing(item -> item.getGarage().getName()))
//                .forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listGarage() {
        try {
            StdIO.write("Name       : ");
            String name = StdIO.readLine().toUpperCase();

            listGarage(garageDao.read(name).get());

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public void listGarages() {
        for (Garage x : garageDao.readAllGarages()) {
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
            StdIO.write("Name        : ");
            String name = StdIO.readLine().toUpperCase();
            StdIO.write("Description : ");
            String description = StdIO.readLine();

            Garage garage = new Garage(name, description);

            garageDao.maintain(garage);

            StdIO.write("No Of Floors: ");
            final int noOfFloors = Integer.valueOf(StdIO.readLine());

            for (int i = 1; i <= noOfFloors; i++) {
                StdIO.write(i + " Floor Description: ");
                description = StdIO.readLine();

                Floor floor = new Floor(i, description);

                StdIO.write("No Of Parking slots on the floor: ");
                final int noOfSlots = Integer.valueOf(StdIO.readLine());

                StdIO.write("Size of the slots (SMALL,MEDIUM,LARGE,HUGE): ");
                Vehicle.Size slotSize = Vehicle.Size.valueOf(StdIO.readLine().toUpperCase());

                StdIO.write("Cost per hour: ");
                double cost = Double.valueOf(StdIO.readLine());

                for (int j = 0; j < noOfSlots; j++) {

                    ParkingSlot parkingSlot = new ParkingSlot.Builder()
                            .withGarage(garage)
                            .withFloor(floor)
                            .withPlaceNo(j+1)
                            .withSize(slotSize)
                            .withFee(cost)
                            .build();

                    parkingSlotDao.maintain(parkingSlot);
                    //parkingSlotList.put(parkingSlot.getKey(), parkingSlot);
                }
            }

            //FileIO.writeObject(parkingSlotList, parkingSlotFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void maintainParkingSlots() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Maintain Parking Slot");
            StdIO.writeLine("");
            StdIO.write("Garage Name: ");
            String name = StdIO.readLine().toUpperCase();
            Optional<Garage> optGarage = garageDao.read(name);
            if (!optGarage.isPresent()) {
                StdIO.ErrorReport("Unknown garage " + name);
                return;
            }

            StdIO.write("Floor      : ");
            int floorIx = Integer.valueOf(StdIO.readLine());

            StdIO.write("Slot       : ");
            int slotIx = Integer.valueOf(StdIO.readLine());

            StdIO.write("Slot size (SMALL,MEDIUM,LARGE,HUGE): ");
            Vehicle.Size slotSize = Vehicle.Size.valueOf(StdIO.readLine().toUpperCase());

            StdIO.write("Cost hour or month (>200): ");
            double costIx = Double.valueOf(StdIO.readLine());

            ParkingSlot parkingSlot = new ParkingSlot.Builder()
                    .withGarage(optGarage.get())
                    .withFloor(new Floor(floorIx, String.valueOf(floorIx)))
                    .withPlaceNo(slotIx)
                    .withSize(slotSize)
                    .withFee(costIx)
                    .build();

            parkingSlotDao.maintain(parkingSlot);


        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }
}
