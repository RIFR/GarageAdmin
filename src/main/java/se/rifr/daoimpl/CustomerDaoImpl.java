package se.rifr.daoimpl;

import se.rifr.dao.CustomerDao;
import se.rifr.support.FileIO;
import se.rifr.domain.Customer;

import java.util.*;
import java.util.stream.Collectors;

public class CustomerDaoImpl implements CustomerDao {

    String fileName;

    private Map<String,Customer> customers = new HashMap<>();

    @Override
    public void maintain(Customer customer) {
        customers.put(customer.getKey(),customer);
        if (fileName != null)
            FileIO.writeObject(customers, fileName);
    }

    @Override
    public void delete(Customer customer) {
        boolean ok = customers.remove(customer.getKey(),customer);
        if (ok && fileName != null)
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
        printOut(readAllCustomers());
    };

    @Override
    public void printOut(Collection<Customer> collection){
        System.out.println(Customer.toStringHeader());
        if (collection != null)
            collection.stream()
                .sorted(Comparator.comparing(item -> item.getFirstName()))
                .sorted(Comparator.comparing(item -> item.getLastName()))
                .forEach(item -> System.out.println(item.toStringLine()));
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
        if (customers != null && fileName!=null){
            FileIO.writeObject(customers, fileName);
            fileName = null;
        }
    }

}
