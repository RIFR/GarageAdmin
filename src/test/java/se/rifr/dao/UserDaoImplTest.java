package se.rifr.dao;

import org.junit.Test;
import se.rifr.daoimpl.UserDaoImpl;
import se.rifr.domain.User;

import java.util.Optional;

import static org.junit.Assert.*;

public class UserDaoImplTest {

    // KEY = UserName

    @Test
    public void startstop() {

        UserDao userDao = new UserDaoImpl();
        userDao.start("src/test/files/usertest.ser");

        User user = new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("001")
                .withemail("kalle.anka@ankeborg.org")
                .withUserName("KAAN")
                .withPassword("001")
                .build();

        userDao.maintain(user);

        userDao.stop();

        userDao.delete(user);

        userDao.start("src/test/files/usertest.ser");

        Optional<User> userOpt = userDao.read("KAAN");

        assertTrue (userOpt.isPresent());

        userDao.stop();

    }

    @Test
    public void maintain() {
        UserDao userDao = new UserDaoImpl();

        User user = new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withPassword("007")
                .withUserName("KAAN")
                .build();

        userDao.maintain(user);

        Optional <User> userOpt = userDao.read("KAAN");

        assertTrue (userOpt.isPresent());

        assertEquals(user,userOpt.get());

    }

    @Test
    public void delete() {
        UserDao userDao = new UserDaoImpl();

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withPassword("007")
                .withUserName("KAAN")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withPassword("008")
                .withUserName("KAKU")
                .build());

        Optional <User> userOpt = userDao.read("KAKU");

        assertTrue (userOpt.isPresent());

        userDao.delete(userOpt.get());

        userOpt = userDao.read("KAKU");

        assertFalse (userOpt.isPresent());

    }

    @Test
    public void read() {
        UserDao userDao = new UserDaoImpl();

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withPassword("007")
                .withUserName("KAAN")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withPassword("008")
                .withUserName("KAKU")
                .build());

        Optional <User> userOpt = userDao.read("KAAN");

        assertTrue (userOpt.isPresent());

        userOpt = userDao.read("KAKU");

        assertTrue (userOpt.isPresent());

        userOpt = userDao.read("HEML");

        assertFalse (userOpt.isPresent());
    }

    @Test
    public void readAllUsers() {
        UserDao userDao = new UserDaoImpl();

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("007")
                .withemail("kalle.anka@ankeborg.org")
                .withPassword("007")
                .withUserName("KAAN")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@kulan.se")
                .withPassword("008")
                .withUserName("KAKU")
                .build());

        assertTrue (userDao.readAllUsers().size()==2);
    }

    @Test
    public void printOut() {

        UserDao userDao = new UserDaoImpl();

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Anka")
                .withBarCode("001")
                .withemail("kalle.anka@ankeborg.org")
                .withPassword("001")
                .withUserName("KAAN")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Balle")
                .withBarCode("002")
                .withemail("kalle.balle@gmail.com")
                .withPassword("002")
                .withUserName("KABA")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Anders")
                .withLastName("Anka")
                .withBarCode("003")
                .withemail("anders.anka@ankeborg.org")
                .withPassword("003")
                .withUserName("ANAN")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Anders")
                .withLastName("Kula")
                .withBarCode("004")
                .withemail("anders.kula@gmail.com")
                .withPassword("004")
                .withUserName("ANKU")
                .build());

        userDao.maintain(new User.Builder()
                .withFirstName("Kalle")
                .withLastName("Kula")
                .withBarCode("008")
                .withemail("kalle.kula@gmail.com")
                .withPassword("008")
                .withUserName("KAKU")
                .build());

        userDao.printOut();

    }
}
