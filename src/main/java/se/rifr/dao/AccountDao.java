package se.rifr.dao;

import se.rifr.domain.Account;

import java.util.Collection;
import java.util.Optional;

public interface AccountDao {

    void maintain(Account account);
    void delete(Account account);

    Optional<Account> read(String key);

    Collection<Account> readAllAccounts();
    Collection<Account> readAccountsWithNegativSaldo();

    void printOut();
    void printOut(Collection<Account> collection);

    void start(String fileName);
    void stop();
}
