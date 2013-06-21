package com.qsoft.demo.persistence.dao;

import com.qsoft.demo.persistence.model.SavingAccount;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

import static junit.framework.Assert.assertTrue;

/**
 * User: lent
 * Date: 6/20/13
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:test-context.xml"})
@TransactionConfiguration(defaultRollback = true)
// Importance, as the transaction will be rollback for each test
// give us a clean state.
@Transactional
public class SavingAccountTest
{
    @Autowired
    private SavingAccountDAO savingAccountDAO;

    @Autowired
    private DataSource dataSource;

    private IDatabaseTester databaseTester;

    @Before
    public void setup() throws Exception {
        IDataSet dataSet = readDataSet();  // read data from xml file
        cleanlyInsert(dataSet);  // empty the db and insert data
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(System.class.getResource("/dataset.xml"));
    }

    private void cleanlyInsert(IDataSet dataSet) throws Exception {
        databaseTester = new DataSourceDatabaseTester(dataSource);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setDataSet(dataSet);
        databaseTester.onSetup();
    }

    @After
    public void tearDown() throws Exception {
        databaseTester.onTearDown();
    }

    @Test
    public void testFindAccountWithBalanceGreaterThanAMillion() {
        List<SavingAccount> savingAccounts = savingAccountDAO.findAccountWithBalanceGreaterThanAMillion();
        assertTrue(savingAccounts.size() == 0);
    }

    @Test
    public void testCreateAccountWithBalanceIsMillions() {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNo("LeNT");
        savingAccount.setDescription("My precious");
        savingAccount.setBalance(20000000d);
        savingAccount.setId(1L); // Usually, id is generated
        savingAccountDAO.save(savingAccount);

        List<SavingAccount> savingAccounts = savingAccountDAO.findAccountWithBalanceGreaterThanAMillion();
        assertTrue(savingAccounts.size() == 1);
    }

    @Test
    public void testIsAccountWithBalanceIsMillionsStillExist() {
        List<SavingAccount> savingAccounts = savingAccountDAO.findAccountWithBalanceGreaterThanAMillion();
        assertTrue(savingAccounts.size() == 0);
    }
}
