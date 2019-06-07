package se.rifr.dao;

import se.rifr.FileIO;
import se.rifr.domain.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerDaoImpl implements CustomerDao{

    String fileName;

    private Map<String,Customer> Customers = new HashMap<>();

    @Override
    public void maintain(Customer Customer) {
        Customers.put(Customer.getKey(),Customer);
        FileIO.writeObject(Customers, fileName);
    }

    @Override
    public Optional<Customer> read(String key) {
        return Optional.ofNullable(Customers.get(key));
    }

    @Override
    public Collection<Customer> readAllCustomers() {
        return Customers.values().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        System.out.println(Customer.toStringHeader());
        if (Customers != null)
            Customers.forEach((k, v) -> System.out.println(v.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Customer> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            Customers = tempList;
    };

    @Override
    public void stop(){
        if (Customers != null) FileIO.writeObject(Customers, fileName);
    };

}
