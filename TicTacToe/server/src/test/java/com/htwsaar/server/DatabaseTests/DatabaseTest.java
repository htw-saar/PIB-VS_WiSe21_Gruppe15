package com.htwsaar.server.DatabaseTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.htwsaar.server.Hibernate.entity.User;
import com.htwsaar.server.Services.DatabaseService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


/**
 * Unit test for simple App.
 */
public class DatabaseTest 
{
    private DatabaseService databaseService = new DatabaseService();;
    private List<User> testUserList;
    private String username = "Simon";
    private String password = "test";
    private String username2 = "Simon2";
    private String password2 = "test";
    private String username3 = "Simon3";
    private String password3 = "test";

    /**
     * Rigorous Test :-)
     */
    @Test
    public void DatabaseAddUsers()
    {
        databaseService.addUser(username, password);
        databaseService.addUser(username2, password2);
        databaseService.addUser(username3, password3);

    }
    
    @Test
    public void DatabaseGetScoreboard(){
        databaseService.addWin(username);
        databaseService.addWin(username);
        databaseService.addLose(username);

        databaseService.addWin(username2);
        databaseService.addLose(username2);
        databaseService.addLose(username2);

        databaseService.addWin(username3);
        databaseService.addWin(username3);
        databaseService.addWin(username3);

        testUserList = new ArrayList<>();
        testUserList.add(databaseService.getUserData(username3));
        testUserList.add(databaseService.getUserData(username));
        testUserList.add(databaseService.getUserData(username2));

        List<User> users = databaseService.getScoreboard();

        for (int i = 0; i < testUserList.size(); i++) {
            assertEquals(testUserList.get(i).getScore(), users.get(i).getScore());
        }


    }


    
}
