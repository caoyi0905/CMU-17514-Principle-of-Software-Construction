package santorini;

import game.Game;
import game.Player;
import org.junit.Test;
import token.Demeter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Demetertest {
    Game game = new Game();
    Demeter d = new Demeter();
    Player parent = Player.PLAYER0;

    /**
     * Test the functionality that Demeter could build twice
     */
    @Test
    public void test_demeter_build_twice(){
        d.setParent(parent);
        game = d.buildtoken(game,1,1);
        assertEquals(d.getBuildtimes(),1);
        assertEquals(d.getParent(),game.getPlayer());
        game = d.buildtoken(game,1,2);
        assertEquals(d.getBuildtimes(),2);
        assertFalse(d.getParent().equals(game.getPlayer()));
    }

    /**
     * Test that the Demeter could not build twice on the same grid
     */
    @Test
    public void test_build_twice_on_same_grid(){
        assertTrue(d.checkbuild(game,1,1));
        d.buildtoken(game,1,1);
        d.dosomething(game);
        assertFalse(game.getBoard().getGrids(1,1).getBorder());
    }
}
