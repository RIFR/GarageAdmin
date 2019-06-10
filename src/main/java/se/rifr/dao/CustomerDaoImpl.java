package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerDaoImpl implements CustomerDao{

    String fileName;

    private Map<String,Customer> customers = new HashMap<>();

    @Override
    public void maintain(Customer customer) {
        customers.put(customer.getKey(),customer);
        FileIO.writeObject(customers, fileName);
    }

    @Override
    public void delete(Customer customer) {
        customers.remove(customer);
        FileIO.writeObject(customers, fileName);
    }

    @Override
    public Optional<Customer> read(String key) {
        return Optional.ofNullable(customers.get(key));
    }

    @Override
    public Collection<Customer> readAllCustomers() {
        return customers.values().stream()
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        System.out.println(Customer.toStringHeader());
        if (customers != null)
            customers.forEach((k, v) -> System.out.println(v.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Customer> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            customers = tempList;
    };

    @Override
    public void stop(){
        if (customers != null) FileIO.writeObject(customers, fileName);
    };

}
