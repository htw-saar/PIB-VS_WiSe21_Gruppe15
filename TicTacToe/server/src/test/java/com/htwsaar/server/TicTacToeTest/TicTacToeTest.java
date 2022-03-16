package com.htwsaar.server.TicTacToeTest;


import com.htwsaar.server.Game.TicTacToe;
import org.junit.Test;

import static org.junit.Assert.*;

public class TicTacToeTest {
    TicTacToe serverLogic = new TicTacToe("testuser");

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    //@Test
    //TicTacToe

    @Test
    public void serverGameInitTest() {
        assertEquals("testuser", serverLogic.getActivePlayer());
    }

    @Test
    public void serverGameInitTestfalse(){
        assertNotEquals("Toni", serverLogic.getActivePlayer());
    }

   // @Test
    //public void getGameStatusGameFalse(){
       // assertNotEquals(serverLogic.getGameStatus(), TicTacToe.Winner.NONE);

    //}

    @Test
    public void EmptyGameBoard(){
        assertNotNull(serverLogic.getGameboard());
    }

    @Test
    public void noJoinCode(){
        assertNotNull(serverLogic.getJoinCode());
    }




}



