package se.rifr.dao;

import org.junit.Test;
import se.rifr.daoimpl.GarageDaoImpl;
import se.rifr.domain.Garage;

import java.util.Optional;

import static org.junit.Assert.*;

public class GarageDaoImplTest {


    // KEY = GarageName

    @Test
    public void startstop() {

        GarageDao garageDao = new GarageDaoImpl();
        garageDao.start("src/test/files/garagetest.ser");

        Garage garage = new Garage("HUS NUMMER 1","No 1");

        garageDao.maintain(garage);

        garageDao.stop();

        garageDao.delete(garage);

        garageDao.start("src/test/files/garagetest.ser");

        Optional<Garage> garageOpt = garageDao.read("HUS NUMMER 1");

        assertTrue (garageOpt.isPresent());

        garageDao.stop();

    }

    @Test
    public void maintain() {
        GarageDao garageDao = new GarageDaoImpl();

        Garage garage = new Garage("HUS NUMMER 1","No 1");

        garageDao.maintain(garage);

        Optional <Garage> garageOpt = garageDao.read("HUS NUMMER 1");

        assertTrue (garageOpt.isPresent());

        assertEquals(garage,garageOpt.get());

    }

    @Test
    public void delete() {
        GarageDao garageDao = new GarageDaoImpl();

        garageDao.maintain(new Garage("HUS NUMMER 1","No 1"));

        garageDao.maintain(new Garage("HUS NUMMER 2","No 2"));

        Optional <Garage> garageOpt = garageDao.read("HUS NUMMER 1");

        assertTrue (garageOpt.isPresent());

        garageDao.delete(garageOpt.get());

        garageOpt = garageDao.read("HUS NUMMER 1");

        assertFalse (garageOpt.isPresent());

    }

    @Test
    public void read() {
        GarageDao garageDao = new GarageDaoImpl();

        garageDao.maintain(new Garage("HUS NUMMER 1","No 1"));

        garageDao.maintain(new Garage("HUS NUMMER 2","No 2"));

        Optional <Garage> garageOpt = garageDao.read("HUS NUMMER 1");

        assertTrue (garageOpt.isPresent());

        garageOpt = garageDao.read("HUS NUMMER 2");

        assertTrue (garageOpt.isPresent());

        garageOpt = garageDao.read("HUS NUMMER 3");

        assertFalse (garageOpt.isPresent());
    }

    @Test
    public void readAllGarages() {
        GarageDao garageDao = new GarageDaoImpl();

        garageDao.maintain(new Garage("HUS NUMMER 1","No 1"));

        garageDao.maintain(new Garage("HUS NUMMER 2","No 2"));

        assertTrue (garageDao.readAllGarages().size()==2);
    }

    @Test
    public void printOut() {

        GarageDao garageDao = new GarageDaoImpl();

        garageDao.maintain(new Garage("HUS NUMMER 1","No 1"));
        garageDao.maintain(new Garage("HUS NUMMER 2","No 2"));
        garageDao.maintain(new Garage("HUS NUMMER 3","No 3"));
        garageDao.maintain(new Garage("HUS NUMMER 4","No 4"));
        garageDao.maintain(new Garage("HUS NUMMER 5","No 5"));

        garageDao.printOut();

    }
}
