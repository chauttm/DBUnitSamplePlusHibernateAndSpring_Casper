package com.qsoft.demo.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: lent
 * Date: 6/20/13
 */
@Entity
@Table(name = "saving_account")
public class SavingAccount
{
// ------------------------------ FIELDS ------------------------------

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "account_no", length = 255)
    private String accountNo;
    @Column(name = "description", length = 1024)
    private String description;
    @Column(name = "balance")
    private Double balance;

// --------------------- GETTER / SETTER METHODS ---------------------

    public String getAccountNo()
    {
        return accountNo;
    }

    public void setAccountNo(String accountNo)
    {
        this.accountNo = accountNo;
    }

    public Double getBalance()
    {
        return balance;
    }

    public void setBalance(Double balance)
    {
        this.balance = balance;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }
}
