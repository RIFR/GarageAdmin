package se.rifr.dao;

import se.rifr.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserDao {

    void maintain(User User);
    void delete(User User);

    Optional<User> read(String key);

    Collection<User> readAllUsers();

    void printOut();

    void start(String fileName);
    void stop();
}
