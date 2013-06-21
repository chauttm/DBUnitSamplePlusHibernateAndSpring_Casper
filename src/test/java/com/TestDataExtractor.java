package com;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.xml.FlatXmlDataSet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * User: lent
 * Date: 6/20/13
 */
public class TestDataExtractor {

    static private List tableList = new ArrayList();

    public static void main(String[] args) throws ClassNotFoundException, SQLException, DatabaseUnitException, FileNotFoundException, IOException
    {

        Class.forName("org.postgresql.Driver");

        Connection jdbcConnection = DriverManager.getConnection("jdbc:postgresql://localhost/db_dbunit", "qsoft", "H@n0i");
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection);

        populateTableList(jdbcConnection);

        QueryDataSet partialDataSet = new QueryDataSet(connection);
        addTables(partialDataSet);

// XML file into which data needs to be extracted
        FlatXmlDataSet.write(partialDataSet, new FileOutputStream("test-dataset_temp.xml"));
        System.out.println("Dataset written");
    }

    private static void populateTableList(Connection connection) {
        ResultSet rs = null;
        Statement st = null;
        try {
            st = connection.createStatement();
            rs = st.executeQuery("Select tablename from pg_tables WHERE tablename LIKE '%account'");
            while (rs.next()){
                tableList.add(rs.getString("tablename"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally{
            try {
                rs.close();
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    static private void addTables(QueryDataSet dataSet) throws AmbiguousTableNameException
    {
        if (tableList == null) return;
        for (Iterator k = tableList.iterator(); k.hasNext(); ) {
            String table = (String) k.next();
            dataSet.addTable(table);
        }
    }

}