package se.rifr.dao;

import se.rifr.domain.Customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerDao {

    void maintain(Customer Customer);

    Optional<Customer> read(String key);

    Collection<Customer> readAllCustomers();

    void printOut();

    void start(String fileName);
    void stop();
}
