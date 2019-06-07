package se.rifr;

import se.rifr.dao.AccountDaoImpl;
import se.rifr.dao.CustomerDaoImpl;

public class Main {

    public static void main(String args[]) {
	    GarageAdmin garageAdmin = new GarageAdmin
                (new AccountDaoImpl(),new CustomerDaoImpl());
	    garageAdmin.start();
    }
}
