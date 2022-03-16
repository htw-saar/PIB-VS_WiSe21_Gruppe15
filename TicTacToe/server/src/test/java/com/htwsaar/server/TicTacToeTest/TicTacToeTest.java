package com.htwsaar.server.TicTacToeTest;


import com.htwsaar.server.Game.TicTacToe;
import com.htwsaar.server.Hibernate.dao.UserDao;
import com.htwsaar.server.Hibernate.entity.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeTest {
    TicTacToe serverLogic = new TicTacToe("testuser");

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
    public void noJoinCodeTest() {
        assertNotNull(serverLogic.getJoinCode());
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

        var gameboard = serverLogic.getGameboard();

        serverLogic.checkWinCondition(TicTacToe.Winner.Player1);
        assertEquals(serverLogic.getGameStatus(),serverLogic.checkWinCondition(TicTacToe.Winner.Player1));
    }

    @Test
    public void compareJoinCodeTest(){
        var joinCode = serverLogic.getJoinCode();
        var comparedJoinCode = serverLogic.compareJoinCode(joinCode);

        assertEquals(comparedJoinCode, 1);
    }


}



