package se.rifr;

import se.rifr.dao.*;

public class Main {

    public static void main(String args[]) {
	    GarageAdmin garageAdmin = new GarageAdmin
                (new UserDaoImpl(),new CustomerDaoImpl(),new AccountDaoImpl(),new VehicleDaoImpl(),new GarageDaoImpl());

	    garageAdmin.start();
    }
}
