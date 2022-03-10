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
    private User testuser1 = new User("Simon", "test");
//    private User testuser2 = new User("Simon2", "test");
//    private User testuser3 = new User("Simon3", "test");

    private String changePassword = "testchange";



    /**
     * Rigorous Test :-)
     */
//    @Test
//    public void CheckAddUsers()
//    {
//        databaseService.addUser(testuser1.getUsername(), testuser1.getPassword());
//        databaseService.addUser(testuser2.getUsername(), testuser2.getPassword());
//        databaseService.addUser(testuser3.getUsername(), testuser3.getPassword());
//    }


//    @Test
//    public void CheckGetScoreboard(){
//        databaseService.addWin(testuser1.getUsername());
//        databaseService.addWin(testuser1.getUsername());
//        databaseService.addLose(testuser1.getUsername());
//
//        databaseService.addWin(testuser2.getUsername());
//        databaseService.addLose(testuser2.getUsername());
//        databaseService.addLose(testuser2.getUsername());
//
//        databaseService.addWin(testuser3.getUsername());
//        databaseService.addWin(testuser3.getUsername());
//        databaseService.addWin(testuser3.getUsername());
//
//        testUserList = new ArrayList<>();
//        testUserList.add(databaseService.getUserData(testuser3.getUsername()));
//        testUserList.add(databaseService.getUserData(testuser1.getUsername()));
//        testUserList.add(databaseService.getUserData(testuser2.getUsername()));
//
//        List<User> users = databaseService.getScoreboard();
//
//        for (int i = 0; i < testUserList.size(); i++) {
//            assertEquals(testUserList.get(i).getScore(), users.get(i).getScore());
//        }
//    }


    @Test
    public void CheckChangePassword(){
        databaseService.changePassword(testuser1.getUsername(), testuser1.getPassword(), changePassword);
        User user = databaseService.getUserData(testuser1.getUsername());
        assertEquals(changePassword, user.getPassword());
        databaseService.changePassword(testuser1.getUsername(), changePassword, testuser1.getPassword());
    }

    @Test
    public void clearDatabase(){
//        databaseService.
    }
}
