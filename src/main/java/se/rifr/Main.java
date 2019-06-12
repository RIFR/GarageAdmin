package se.rifr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import se.rifr.dao.*;

public class Main {

    public static void main(String args[]) {

        GarageAdmin garageAdmin = new GarageAdmin (args);

	    garageAdmin.start();
    }
}
