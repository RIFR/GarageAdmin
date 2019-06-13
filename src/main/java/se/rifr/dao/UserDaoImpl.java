package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao{

    String fileName;

    private Map<String,User> users = new HashMap<>();

    @Override
    public void maintain(User User) {
        users.put(User.getKey(),User);
        FileIO.writeObject(users, fileName);
    }

    @Override
    public void delete(User User) {
        users.remove(User);
        FileIO.writeObject(users, fileName);
    }

    @Override
    public Optional<User> read(String key) {
        return Optional.ofNullable(users.get(key));
    }

    @Override
    public Collection<User> readAllUsers() {
        return users.values().stream()
                .filter(item -> !item.getKey().equals("SuperUser"))
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){printOut(readAllUsers());}

    @Override
    public void printOut(Collection<User> collection){
        System.out.println(User.toStringHeader());
        if (collection != null)
            collection.stream()
                .sorted(Comparator.comparing(item -> item.getFirstName()))
                .sorted(Comparator.comparing(item -> item.getLastName()))
                .forEach(item -> System.out.println(item.toStringLine()));
    };

    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, User> tempList = FileIO.readObject(fileName);
        if (tempList != null) {users = tempList;}
        else {
            maintain(new User.Builder()
                    .withFirstName("Super")
                    .withLastName("User")
                    .withBarCode("007")
                    .withemail("superadmin@anybank.se")
                    .withUserName("SuperUser")
                    .withPassword("SuperUser").build());
        }
    };

    @Override
    public void stop(){
        if (users != null) FileIO.writeObject(users, fileName);
    };

}
