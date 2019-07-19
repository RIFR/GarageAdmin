package se.rifr.dao;

import org.junit.Test;
import se.rifr.daoimpl.CustomerDaoImpl;
import se.rifr.domain.Customer;

import java.util.Optional;

import static org.junit.Assert.*;

public class CustomerDaoImplTest {

    @Test
    public void startstop() {

        CustomerDao customerDao = new CustomerDaoImpl();
        customerDao.start("src/test/files/customertest.ser");

        Customer customer = new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("001")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("001")
                .withUserName("KAAN")
                .build();

        customerDao.maintain(customer);

        customerDao.stop();

        customerDao.delete(customer);

        customerDao.start("src/test/files/customertest.ser");

        Optional <Customer> customerOpt = customerDao.read("001");

        assertTrue (customerOpt.isPresent());

        customerDao.stop();

    }

    @Test
    public void maintain() {
        CustomerDao customerDao = new CustomerDaoImpl();

        Customer customer = new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("007")
                .withUserName("KAAN")
                .build();

        customerDao.maintain(customer);

        Optional <Customer> customerOpt = customerDao.read("007");

        assertTrue (customerOpt.isPresent());

        assertEquals(customer,customerOpt.get());

    }

    @Test
    public void delete() {
        CustomerDao customerDao = new CustomerDaoImpl();

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("007")
                .withUserName("KAAN")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withTelephoneNumber("008")
                .withUserName("KAKU")
                .build());

        Optional <Customer> customerOpt = customerDao.read("008");

        assertTrue (customerOpt.isPresent());

        customerDao.delete(customerOpt.get());

        customerOpt = customerDao.read("008");

        assertFalse (customerOpt.isPresent());

    }

    @Test
    public void read() {
        CustomerDao customerDao = new CustomerDaoImpl();

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("007")
                .withUserName("KAAN")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withTelephoneNumber("008")
                .withUserName("KAKU")
                .build());

        Optional <Customer> customerOpt = customerDao.read("007");

        assertTrue (customerOpt.isPresent());

        customerOpt = customerDao.read("008");

        assertTrue (customerOpt.isPresent());

        customerOpt = customerDao.read("009");

        assertFalse (customerOpt.isPresent());
    }

    @Test
    public void readAllCustomers() {
        CustomerDao customerDao = new CustomerDaoImpl();

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("007")
                .withUserName("KAAN")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withTelephoneNumber("008")
                .withUserName("KAKU")
                .build());

        assertTrue (customerDao.readAllCustomers().size()==2);
    }

    @Test
    public void printOut() {

        CustomerDao customerDao = new CustomerDaoImpl();

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("001")
                .withemail("kalle.anka@ankeborg.org")
                .withTelephoneNumber("001")
                .withUserName("KAAN")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Balle")
                .withBarCode("002")
                .withemail("kalle.balle@gmail.com")
                .withTelephoneNumber("002")
                .withUserName("KABA")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Anders")
                .withLastName("Anka")
                .withBarCode("003")
                .withemail("anders.anka@ankeborg.org")
                .withTelephoneNumber("003")
                .withUserName("ANAN")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Anders")
                .withLastName("Kula")
                .withBarCode("004")
                .withemail("anders.kula@gmail.com")
                .withTelephoneNumber("004")
                .withUserName("ANKU")
                .build());

        customerDao.maintain(new Customer.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@gmail.com")
                .withTelephoneNumber("008")
                .withUserName("KAKU")
                .build());

        customerDao.printOut();

    }

}