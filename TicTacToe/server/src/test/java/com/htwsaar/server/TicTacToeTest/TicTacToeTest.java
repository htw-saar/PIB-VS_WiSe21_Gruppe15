package com.htwsaar.server.TicTacToeTest;


import com.htwsaar.server.Game.TicTacToe;
import com.htwsaar.server.Services.DatabaseService;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeTest {
    DatabaseService databaseService = new DatabaseService();
    TicTacToe serverLogic = new TicTacToe("testuser", databaseService);

    @Test
    public void shouldAnswerWithTrueTest() {
        assertTrue(true);
    }

    @Test
    public void getGameStatusTest() {
        assertEquals(serverLogic.getGameStatus(), TicTacToe.Winner.NONE);
    }

    @Test
    public void setGetActivePlayerTest() {
        serverLogic.setActivePlayer("nikolai");
        assertEquals("nikolai", serverLogic.getActivePlayer());
    }


    @Test
    public void serverGameInitTest() {
        assertEquals("testuser", serverLogic.getActivePlayer());
    }

    @Test
    public void serverGameInitTestFalse() {
        assertNotEquals("Toni", serverLogic.getActivePlayer());
    }

    @Test
    public void EmptyGameBoardTest() {
        assertNotNull(serverLogic.getGameboard());
    }

    @Test
    public void setFieldTest() {
        serverLogic.setField(2);
        var gameboard = serverLogic.getGameboard();

        assertNotNull(gameboard[2]);
    }

    @Test
    public void checkWinConditionTest() {
        serverLogic.setField(3);
        serverLogic.setField(4);
        serverLogic.setField(5);

        serverLogic.checkWinCondition(TicTacToe.Winner.Player1);
        assertEquals(serverLogic.getGameStatus(), serverLogic.checkWinCondition(TicTacToe.Winner.Player1));
    }

    @Test
    public void checkWinConditionTestFalse() {
        serverLogic.setField(3);
        serverLogic.setField(1);
        serverLogic.setField(5);

        serverLogic.checkWinCondition(TicTacToe.Winner.NONE);
        assertEquals(serverLogic.getGameStatus(), serverLogic.checkWinCondition(TicTacToe.Winner.NONE));
    }

    @Test
    public void compareJoinCodeTest() {
        var joinCode = serverLogic.getJoinCode();
        var comparedJoinCode = serverLogic.compareJoinCode(joinCode);

        assertEquals(comparedJoinCode, 1);
    }

    @Test
    public void arePlayersInGameTest() {
        assertNotNull(serverLogic.getPlayers());
    }

    /*@Test
    public void isGameboardFullTest() {
        TicTacToe serverLogic2 = new TicTacToe("testuser2");
        serverLogic.setField(0);
        serverLogic2.setField(1);
        serverLogic.setField(2);
        serverLogic2.setField(3);
        serverLogic.setField(4);
        serverLogic2.setField(5);
        serverLogic2.setField(6);
        serverLogic.setField(7);
        serverLogic2.setField(8);
        var gameboard = serverLogic.getGameboard();

        assertEquals(serverLogic.checkWinCondition(TicTacToe.Winner.Player1), TicTacToe.Winner.UNSETTELD);
    }*/

}



