package se.rifr;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class GarageAdmin {

    private Map<String, User>         userList       = new HashMap<>();
    private Map<String, Customer> customerList       = new HashMap<>();
    private Map<String, Account>   accountList       = new HashMap<>();
    private Map<String, Vehicle> vehicleList         = new HashMap<>();
    private Map<String, ParkingSlot> parkingSlotList = new HashMap<>();
    private List<Scannings> scanningList             = new ArrayList<>();
    private List<Floor> floorList                    = new ArrayList<>();
    private List<Garage> garageList                  = new ArrayList<>();

    String dirName = "C:\\Dev\\GarageAdmin\\";

    String userFile        = dirName + "userlist.ser";
    String customerFile    = dirName + "customerlist.ser";
    String accountFile     = dirName + "accountlist.ser";
    String vehicleFile     = dirName + "vehiclelist.ser";
    String scanningFile    = dirName + "scanninglist.ser";
    String floorFile       = dirName + "floorlist.ser";
    String garageFile      = dirName + "garagelist.ser";
    String parkingSlotFile = dirName + "parkingslotlist.ser";

    
    public GarageAdmin() {

        LoadReloadData();

        //if (userList == null) {
        User user = new User("Super","User","007","admin@mybank.se","SuperUser","SuperUser");
        userList.put(user.getKey(),user);
        //FileIO.writeObject(userList, userFile);
        //}
    }


    private void LoadReloadData () {

        try{
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
            if (tempAccountList != null)
                parkingSlotList = tempParkingSlotList;

            List<Scannings> tempScanningList= FileIO.readObject(scanningFile);
            if (tempScanningList != null)
                scanningList = tempScanningList;

            List<Garage> tempGarageList= FileIO.readObject(garageFile);
            if (tempGarageList != null)
                garageList = tempGarageList;

            List<Floor> tempFloorList= FileIO.readObject(floorFile);
            if (tempFloorList != null)
                floorList = tempFloorList;

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
                if (loginOk(userName,password)) {
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
            answer =menu();
            try{
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
                    scanTransaction();
                    break;
                case "4":
                    listScannings();
                    break;
                case "5":
                    maintainUsers();
                    break;
                case "6":
                    listUsers();
                    break;
                case "7":
                    maintainAccounts();
                    break;
                case "8":
                    listAccounts();
                    break;
                case "9":
                    maintainGarage();
                    break;
                case "10":
                    listGarage();
                    break;
                case "11":
                    maintainFloors();
                    break;
                case "12":
                    listFloors();
                    break;
                case "13":
                    maintainParkingSlots();
                    break;
                case "14":
                    listParkingSlots();
                    break;
                case "15":
                    copyCustomerDataToUser();
                    break;
                case "16":
                    deleteUser();
                    break;
                case "17":
                    excludeOrOpeVehicle(true);
                    break;
                case "18":
                    excludeOrOpeVehicle(false);
                    break;
                case "88":

                    Customer customer = new Customer("Kalle", "Anka", "189901011111", "kalle.anka@ankeborg.com", "KALLEANKA");
                    customerList.put(customer.getKey(),customer);

                    User user = new User(customer.getFirstName(),customer.getLastName(),customer.getBarCode(),customer.getEmail(),customer.getUserName(),"KalleAnkaÄrBäst");
                    userList.put(user.getKey(),user);

                    Account account = new Account(customer, "12345678901234567890", 50000.0, "Kalles head account");
                    accountList.put(account.getKey(), account);

                    break;
                case "70":
                    System.out.println(registerTransaction(getAtmHw("AtmTest001"),getAccount("000000000000001"), false, 500.0));
                    break;
                case "71":
                    System.out.println(registerTransaction(getAtmHw("AtmTest001"),getAccount("000000000000001"), true, 2000.0));
                    break;
                case "99":
                    listUsers(); System.out.println("");
                    listAccounts();System.out.println("");
                    listCustomers();System.out.println("");
                    listVehicles();System.out.println("");
                    listScannings();System.out.println("");
                    listGarages();System.out.println("");
                    listFloors();System.out.println("");
                    listParkingSlots();System.out.println("");
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
            FileIO.writeObject(scanningList, transactionFile);
        if (userList != null)
            FileIO.writeObject(userList, userFile);
        if (cardList != null)
            FileIO.writeObject(cardList,cardFile);
        if (atmHwList != null)
            FileIO.writeObject(atmHwList,atmHwFile);
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
            StdIO.writeLine(" 3. Scan Transaction");
            StdIO.writeLine(" 4. List Scannings");
            StdIO.writeLine(" 5. Maintain users");
            StdIO.writeLine(" 6. List users");
            StdIO.writeLine(" 7. Maintain Accounts");
            StdIO.writeLine(" 8. List Accounts");
            StdIO.writeLine(" 9. Create user from customer");
            StdIO.writeLine("10. Maintain cards");
            StdIO.writeLine("11. List cards");
            StdIO.writeLine("12. Maintain ATM machines");
            StdIO.writeLine("13. List ATM machines");
            StdIO.writeLine("14. Delete user");
            StdIO.writeLine("15. Exclude Card ");
            StdIO.writeLine("16. Open Card ");
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

    private void scanTransaction() {
        Card card;
        try {
            StdIO.clearScreen();
            StdIO.writeLine("Scan Transaction");

            StdIO.write("AtmHw ID      : ");
            String atmHwId = StdIO.readLine();
            if (!atmHwList.containsKey(atmHwId)){
                StdIO.ErrorReport("Unknown ATM mashine "+atmHwId);
                return;
            }

            if (!atmMachineOk(atmHwId)){
                StdIO.ErrorReport("ATM mashine HW problem: "+atmHwId + " "+atmHwList.get(atmHwId).getHwErrorCode());
                return;
            }

            StdIO.write("Card ID       : ");
            String cardId = StdIO.readLine();
            if (!cardList.containsKey(cardId)){
                StdIO.ErrorReport("Unknown Card " +cardId);
                return;
            }
            card = cardList.get(cardId);

            if (!Str.readAcceptedValue("Pin code      : ",card.getPinCode(),3)){
                StdIO.ErrorReport("Unknown Pin Code, max number of tries reached ");
                return;
            };

            if (!cardOk(card.getCardId())) {
                StdIO.ErrorReport("The card is not accepted ");
                return;
            }

            StdIO.write("Deposit (y/n) : ");
            boolean deposit = StdIO.readYesOrNo();

            StdIO.write("Amount        : ");
            double amount = Double.valueOf(StdIO.readLine());

            if (!atmMachinSaldoOk(atmHwId,deposit,amount)) {
                StdIO.ErrorReport("The ATM HW rejected your request ");
                return;
            }

            String res = registerFullTransaction(atmHwId,cardId,deposit,amount);
            System.out.println(res);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    public String registerFullTransaction(String atmHwId, String CardId, boolean deposit, double amount) {

        AtmHw   atmhw   = atmHwList.get(atmHwId);
        Card    card    = cardList.get(CardId);
        Account account = accountList.get(card.getAccount().getAccountId());

        if (!atmMachineOk(atmhw)) return "";
        if (!atmMachinSaldoOk(atmhw,deposit,amount)) return "";
        if (!cardOk(card)) return "";
        if (!cardSaldoOk(card,amount)) return "";


        String res = registerTransaction(atmhw, account,deposit,amount);
        if (res.isEmpty()) return "";
        else {
            card.setAccount(account);
            cardList.put(card.getKey(),card);
            FileIO.writeObject(cardList, cardFile);

            registerAtmTransaction(atmhw,deposit,amount);

        }

        return res;
    }

    public void registerAtmTransaction(AtmHw atmHw, boolean deposit, double amount) {

        try {
            atmHw.changeSaldo(amount,deposit);
            atmHwList.put(atmHw.getKey(),atmHw);
            FileIO.writeObject(atmHwList, atmHwFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
       }
    }

    public String registerTransaction(AtmHw atmHw, Account account, boolean deposit, double amount) {

        String returnStr = "";
        try {

            if (!(deposit && account.getSaldo() < amount)) {

                Scannings scannings = new Scannings(account, LocalDateTime.now(), deposit,amount,atmHw);

                scanningList.add(scannings);
                FileIO.writeObject(scanningList, transactionFile);

                System.out.println("Before " +account.getSaldo() +" "+ deposit);
                account.changeSaldo(amount,deposit);
                System.out.println("After " +account.getSaldo() +" amount"+ amount);

                returnStr = account.getCustomer().getFullName() + " current saldo " +account.getSaldo();

                accountList.put(account.getKey(),account);
                FileIO.writeObject(accountList, accountFile);

                return returnStr;

            }

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
            return "EXCEPTION: " +e;
        }

        return returnStr;
    }

    //private String TransactionToString (Scannings trans) {
    //    return trans.toString();
    //}
    public String listAllTransactions() {
        String str = "";
        for (Scannings x : scanningList){
            str += x.toString() +"\r\n";
        }
        return str;
    }

    private void listCustomers() {
        System.out.println(Customer.toStringHeader());
        if (customerList != null)
            customerList.forEach((k,v) -> System.out.println(v.toStringLine()));
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

            updateAccountdWithUpdatedCustomer (customer); // update if exists in accounts

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void updateAccountdWithUpdatedCustomer (Customer customer) {
        for (Account x : accountList.values()) {
            if (x.getCustomer().getKey() == customer.getKey()){

                x.setCustomer(customer);
                accountList.put(x.getKey(),x);

                updateCardWithUpdatedAccount(x);
            }
        }
        FileIO.writeObject(accountList, accountFile);
    }

    private void updateCardWithUpdatedAccount (Account account) {
        for (Card x : cardList.values()) {
            if (x.getKey() == account.getKey()){

                x.setAccount(account);
                cardList.put(x.getKey(),x);
            }
        }
        FileIO.writeObject(cardList, cardFile);
    }

    private void listScannings() {
        System.out.println(Scannings.toStringHeader());
        if (scanningList != null)
            scanningList.stream().forEach(item -> System.out.println(item.toStringLine()));
    }

    private void listUsers() {
        System.out.println(User.toStringHeader());
        if (userList != null)
            userList.forEach((k,v) -> System.out.println(v.toStringLine()));
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
            User user = new User(firstName,lastName,barcode,email,userName,password);

            userList.put(user.getKey(),user);

            FileIO.writeObject(userList,userFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void copyCustomerDataToUser () {

        try {

            Customer customer;
            StdIO.write("Barcode   : ");
            String barcode = StdIO.readLine().trim();

            if (customerList.containsKey(barcode))
                customer = customerList.get(barcode);
            else {
                StdIO.ErrorReport("Unknown Customer " +barcode);
                return;
            }

            String userName = customer.getUserName();
            while (userList.containsKey(userName)){
                StdIO.ErrorReport("User Name " + customer.getUserName() + " already exists, enter new");
                userName = StdIO.readLine().trim();
            }

            customer.setUserName(userName);
            customerList.put(customer.getKey(),customer);
            FileIO.writeObject(customerList,customerFile);

            StdIO.write("Password   : ");
            String password = StdIO.readLine().trim();

            User user = new User(customer.getFirstName(),customer.getLastName(),customer.getBarCode(),customer.getEmail(),userName,password);
            userList.put(user.getKey(),user);
            FileIO.writeObject(userList,userFile);

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
                        +user.getFirstName()+" " +user.getLastName() + " "+user.getBarcode()+ "(y/n)");
                if (StdIO.readYesOrNo()) {
                    userList.remove(userName);
                    FileIO.writeObject(userList,userFile);
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
            accountList.forEach((k,v) -> System.out.println(v.toStringLine()));
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
                System.out.println("Unknown customer " +barcode);
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

    private void listFloors() {
        System.out.println(Card.toStringHeader());
        if (cardList != null)
            cardList.forEach((k,v) -> System.out.println(v.toStringLine()));
    }

    private void maintainCards() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Maintain Cards");
            StdIO.writeLine("");
            StdIO.write("Card Id     : ");
            String cardId = StdIO.readLine();
            StdIO.write("Pincode     : ");
            String pincode = StdIO.readLine();

            LocalDate validThrou = LocalDateTime.now().plusYears(5).toLocalDate();
            StdIO.writeLine("ValidThrou "+validThrou.toString());

            StdIO.write("Account Id  : ");
            String accountID = StdIO.readLine().trim();

            StdIO.write("Max per week: ");
            String maxPerWeek = StdIO.readLine().trim();

            if (accountList.containsKey(accountID)) {
                Card card = new Card(cardId,pincode,validThrou,accountList.get(accountID),Double.valueOf(maxPerWeek));

                cardList.put(card.getKey(),card);

                FileIO.writeObject(cardList,cardFile);
            } else {
                StdIO.ErrorReport("Unknown Account Id " +accountID);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void excludeOrOpenCard (boolean exclude) {

        try {
            StdIO.write("Card Id     : ");
            String cardId = StdIO.readLine();

            if (!cardList.containsKey(cardId)) {
                StdIO.ErrorReport("Unknown Card " +cardId);
                return;
            }

            Card card = cardList.get(cardId);

            card.setWithDrawn(exclude);
            cardList.put(card.getKey(),card);

            FileIO.writeObject(cardList,cardFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }

    private void listParkingSlots() {
        System.out.println(AtmHw.toStringHeader());
        if (atmHwList != null)
            atmHwList.forEach((k,v) -> System.out.println(v.toStringLine()));
    }

    private void maintainAtmMachines() {
        try {
            StdIO.clearScreen();

            StdIO.writeLine("");
            StdIO.writeLine("Maintain an ATM mashine");
            StdIO.writeLine("");
            StdIO.write("MachineId       : ");
            String mashineId = StdIO.readLine();
            StdIO.write("Saldo           : ");
            String saldo = StdIO.readLine();
            StdIO.write("Description     : ");
            String description = StdIO.readLine();
            StdIO.write("HwErrorCode     : ");
            String hwErrorCode = StdIO.readLine();
            StdIO.write("Max withdraw    : ");
            String maxWithdraw = StdIO.readLine();

            AtmHw atmHw = new AtmHw(mashineId,Double.valueOf(saldo),description,hwErrorCode,Double.valueOf(maxWithdraw));

            atmHwList.put(atmHw.getKey(),atmHw);

            FileIO.writeObject(atmHwList,atmHwFile);

        } catch (Exception e) {
            e.printStackTrace();
            StdIO.ErrorReport("Exception -" + e.toString());
        }
    }
}
