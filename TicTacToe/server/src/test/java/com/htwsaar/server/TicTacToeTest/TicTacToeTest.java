package com.htwsaar.server.TicTacToeTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import com.htwsaar.server.Game.TicTacToe;
import org.junit.Test;

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


    //}
    //public void enumtest(){
    // assertTrue(true);
    //}


}



