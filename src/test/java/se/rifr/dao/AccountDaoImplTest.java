package se.rifr.dao;

import org.junit.ClassRule;
import org.junit.Test;
import se.rifr.daoimpl.AccountDaoImpl;
import se.rifr.domain.Account;
import se.rifr.domain.Customer;

import java.util.Optional;

import static org.junit.Assert.*;

public class AccountDaoImplTest {

//static AccountDao accountDao;

//    @Rule
//    public static void initiateDao(){
//        AccountDao accountDao = new AccountDaoImpl();
//        accountDao.start("src/test/files/accounttest.ser");
//    }

    @Test
    public void startstop() {

        AccountDao accountDao = new AccountDaoImpl();
        accountDao.start("src/test/files/accounttest.ser");

        Account account = new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("001")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build();

        accountDao.maintain(account);

        accountDao.stop();

        accountDao.delete(account);

        accountDao.start("src/test/files/accounttest.ser");

        Optional <Account> accountOpt = accountDao.read("001");

        assertTrue (accountOpt.isPresent());

        accountDao.stop();

    }

    @Test
    public void maintain() {
        AccountDao accountDao = new AccountDaoImpl();

        Account account = new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("007")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build();

        accountDao.maintain(account);

        Optional <Account> accountOpt = accountDao.read("007");

        assertTrue (accountOpt.isPresent());

        assertEquals(account,accountOpt.get());

    }

    @Test
    public void delete() {
        AccountDao accountDao = new AccountDaoImpl();

        Account account = new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("008")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build();

        accountDao.maintain(account);

        Optional <Account> accountOpt = accountDao.read("008");

        assertTrue (accountOpt.isPresent());

        accountDao.delete(accountOpt.get());

        accountOpt = accountDao.read("008");

        assertFalse (accountOpt.isPresent());

    }

    @Test
    public void read() {
        AccountDao accountDao = new AccountDaoImpl();

        accountDao.maintain(new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("007")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("008")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build());

        Optional <Account> accountOpt = accountDao.read("007");

        assertTrue (accountOpt.isPresent());

        accountOpt = accountDao.read("008");

        assertTrue (accountOpt.isPresent());

        accountOpt = accountDao.read("009");

        assertFalse (accountOpt.isPresent());
    }

    @Test
    public void readAllAccounts() {
        AccountDao accountDao = new AccountDaoImpl();

        accountDao.maintain(new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("007")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("008")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build());

        assertTrue (accountDao.readAllAccounts().size()==2);
    }

    @Test
    public void printOut() {

        AccountDao accountDao = new AccountDaoImpl();

        accountDao.maintain(new Account.Builder()
                .withBankId("1")
                .withCustomer(new Customer.Builder()
                        .withBarCode("001")
                        .withemail("e-mail")
                        .withFirstName("A")
                        .withLastName("B")
                        .withTelephoneNumber("1-1-1")
                        .withUserName("AB")
                        .build())
                .withDescription("Descr")
                .withSaldo(1000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("2")
                .withCustomer(new Customer.Builder()
                        .withBarCode("002")
                        .withemail("e-mail2")
                        .withFirstName("A2")
                        .withLastName("B2")
                        .withTelephoneNumber("2-2-2")
                        .withUserName("AB2")
                        .build())
                .withDescription("Descr2")
                .withSaldo(2000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("3")
                .withCustomer(new Customer.Builder()
                        .withBarCode("003")
                        .withemail("e-mail")
                        .withFirstName("A3")
                        .withLastName("B3")
                        .withTelephoneNumber("3-3-3")
                        .withUserName("AB3")
                        .build())
                .withDescription("Descr3")
                .withSaldo(3000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("4")
                .withCustomer(new Customer.Builder()
                        .withBarCode("004")
                        .withemail("e-mail4")
                        .withFirstName("A4")
                        .withLastName("B4")
                        .withTelephoneNumber("4-4-4")
                        .withUserName("AB4")
                        .build())
                .withDescription("Descr4")
                .withSaldo(4000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("5")
                .withCustomer(new Customer.Builder()
                        .withBarCode("005")
                        .withemail("e-mail5")
                        .withFirstName("A5")
                        .withLastName("B5")
                        .withTelephoneNumber("5-5-5")
                        .withUserName("AB5")
                        .build())
                .withDescription("Descr5")
                .withSaldo(5000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("9")
                .withCustomer(new Customer.Builder()
                        .withBarCode("009")
                        .withemail("e-mail9")
                        .withFirstName("A9")
                        .withLastName("B9")
                        .withTelephoneNumber("9-9-9")
                        .withUserName("AB9")
                        .build())
                .withDescription("Descr9")
                .withSaldo(9000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("8")
                .withCustomer(new Customer.Builder()
                        .withBarCode("008")
                        .withemail("e-mail")
                        .withFirstName("A8")
                        .withLastName("B8")
                        .withTelephoneNumber("8-8-8")
                        .withUserName("AB8")
                        .build())
                .withDescription("Descr8")
                .withSaldo(8000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("7")
                .withCustomer(new Customer.Builder()
                        .withBarCode("007")
                        .withemail("e-mail7")
                        .withFirstName("A7")
                        .withLastName("B7")
                        .withTelephoneNumber("7-7-7")
                        .withUserName("AB7")
                        .build())
                .withDescription("Descr7")
                .withSaldo(7000d)
                .build());

        accountDao.maintain(new Account.Builder()
                .withBankId("6")
                .withCustomer(new Customer.Builder()
                        .withBarCode("006")
                        .withemail("e-mail6")
                        .withFirstName("A6")
                        .withLastName("B6")
                        .withTelephoneNumber("6-6-6")
                        .withUserName("AB6")
                        .build())
                .withDescription("Descr6")
                .withSaldo(6000d)
                .build());

        accountDao.printOut();

    }

}