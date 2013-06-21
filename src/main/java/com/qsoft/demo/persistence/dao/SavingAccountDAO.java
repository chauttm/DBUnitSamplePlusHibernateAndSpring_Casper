package com.qsoft.demo.persistence.dao;

import com.qsoft.demo.persistence.model.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: lent
 * Date: 6/20/13
 */
public interface SavingAccountDAO extends JpaRepository<SavingAccount, Long>
{
    @Query("SELECT savingAccount FROM SavingAccount savingAccount WHERE savingAccount.balance > 1000000")
    public List<SavingAccount> findAccountWithBalanceGreaterThanAMillion();
}
