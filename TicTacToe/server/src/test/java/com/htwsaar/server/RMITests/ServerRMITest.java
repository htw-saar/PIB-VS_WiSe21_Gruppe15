package com.htwsaar.server.RMITests;

import org.junit.Test;

import static org.junit.Assert.*;

import com.htwsaar.server.RMI.Server_RMI;
import com.htwsaar.server.Services.DatabaseService;
import com.htwsaar.server.Game.TicTacToe;

public class ServerRMITest {
    DatabaseService databaseService = new DatabaseService();
    Server_RMI server_rmi = new Server_RMI(databaseService);
    TicTacToe tictactoe = new TicTacToe("testuser", databaseService);

    @Test
    public void createGameTest() {
        assertEquals(server_rmi.createGame("testuser"), true);
    }

    @Test
    public void returnGameboardTest() {
        tictactoe.setField(1);
        assertEquals(server_rmi.returnGameboard("testuser")[0], "X");
    }

    @Test
    public void joinGameTest() {

        assertEquals("", "");
    }
}
