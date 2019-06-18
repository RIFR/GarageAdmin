package se.rifr.dao;

import se.rifr.support.FileIO;
import se.rifr.domain.Account;

import java.util.*;
import java.util.stream.Collectors;

public class AccountDaoImpl implements AccountDao{

    String fileName;

    private Map<String,Account> accounts = new HashMap<>();

    @Override
    public void maintain(Account account) {
        accounts.put(account.getKey(),account);
        if (fileName != null)
            FileIO.writeObject(accounts, fileName);
    }

    @Override
    public void delete(Account account) {
        boolean ok = accounts.remove(account.getKey(),account);
        if (ok && fileName != null)
            FileIO.writeObject(accounts, fileName);
    }


    @Override
    public Optional<Account> read(String key) {
        return Optional.ofNullable(accounts.get(key));
    }

    @Override
    public Collection<Account> readAllAccounts() {
        return accounts.values().stream().collect(Collectors.toSet());
    }
    @Override
    public Collection<Account> readAccountsWithNegativSaldo() {
        return accounts.values().stream()
                .filter(account -> account.getSaldo() <= 0.0)
                .collect(Collectors.toSet());
    }

    @Override
    public void printOut(){
        printOut(readAllAccounts());
    };

    @Override
    public void printOut(Collection<Account> collection){
        System.out.println(Account.toStringHeader());
        if (collection != null)
            collection.stream()
                    .sorted(Comparator.comparing(item -> item.getCustomer().getFirstName()))
                    .sorted(Comparator.comparing(item -> item.getCustomer().getLastName()))
                    .forEach(item -> System.out.println(item.toStringLine()));
    };


    @Override
    public void start(String fileName){
        this.fileName = fileName;

        Map<String, Account> tempList = FileIO.readObject(fileName);
        if (tempList != null)
            accounts = tempList;
    };

    @Override
    public void stop(){
        if (accounts != null && fileName != null) {
            FileIO.writeObject(accounts, fileName);
            fileName = null;
        }
    }

}
