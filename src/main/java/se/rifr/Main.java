package se.rifr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import se.rifr.service.GarageService;
import se.rifr.service.GarageServiceImpl;

//@SpringBootApplication
public class Main {

    public static void main(String[] args) {

        //SpringApplication.run(Main.class,args);

        GarageService garageService = new GarageServiceImpl();

        garageService.start();
    }
}
