package santorini;

import game.Game;
import game.Player;
import gameboard.IslandBoard;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class StructureTest {
    Game game = new Game();
    Player player0 = Player.PLAYER0;
    Player player1 = Player.PLAYER1;

    @Test
    public void Structuraltest(){

        game = game.setworkers(1,1,0);
        game = game.setworkers(1,2,0);

        assertEquals(game.countworker(),2);
        assertEquals(game.getBoard().getGrids(1,1).getWorker().get(0).getParent(),player0);

        game = game.setworkers(3,3,1);
        game = game.setworkers(0,2,1);
        assertEquals(game.countworker(),4);


        game = game.getBoard().getGrids(1,1).getWorker().get(0).moveWorker(game,0,1);
        assertEquals(game.getBoard().getGrids(0,1).getWorker().size(),1);

        game = game.getBoard().getGrids(0,1).getWorker().get(0).buildtoken(game,0,0);
        assertEquals(game.getPlayer(),player1);

        game = game.getBoard().getGrids(3,3).getWorker().get(0).moveWorker(game,3,4);
        assertEquals(game.getBoard().getGrids(3,3).getWorker().size(),0);

        game = game.getBoard().getGrids(3,4).getWorker().get(0).buildtoken(game,3,3);
        assertEquals(game.getPlayer(),player0);
        assertFalse(game.getBoard().getGrids(3,4).getWorker().get(0).checkWin(game) != null);




    }



}
