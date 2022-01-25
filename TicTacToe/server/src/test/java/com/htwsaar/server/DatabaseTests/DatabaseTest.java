package com.htwsaar.server.DatabaseTests;

import static org.junit.Assert.assertTrue;

import com.htwsaar.server.Database.DatabaseService;

import org.junit.Test;


/**
 * Unit test for simple App.
 */
public class DatabaseTest 
{
    private DatabaseService databaseService;

    @Test
    public void CreateDatabase(){
        initDatabase();
        assertTrue( true );
    }
    /**
     * Rigorous Test :-)
     */
    @Test
    public void DatabaseAddUser()
    {
        initDatabase();
        clearDatabase();
        databaseService.addUser("Simon", "Test");
        // databaseService.deleteUser("Simon", "Test");
    }


    private void initDatabase(){
        databaseService = new DatabaseService();
    }

    private void clearDatabase(){

    }
}
