package santorini;

import game.Game;
import org.junit.Test;
import token.Demeter;
import token.Minotaur;
import token.Pan;
import token.Worker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Gametest {
    Game game = new Game();

    /**
     * Test of Grids clone function
     */
    @Test
    public void test_gridsclone(){
        assertFalse(game.getBoard().getGrids().equals(game.gridsclone(game
                .getBoard().getGrids())));
    }

    /**
     * Test of set worker function
     */
    @Test
    public void test_setworker(){

        game.setworkers(1,1,0);
        assertEquals(1,game.getBoard().getGrids(1,1).getWorker().size());
    }

    /**
     * Test of build gods function
     */
    @Test
    public void test_buildgod (){
        Pan p = new Pan();
        Demeter d = new Demeter();
        Minotaur m = new Minotaur();
        assertEquals(p.getClass(), game.buildgod("Pan").getClass());
        assertEquals(d.getClass(),game.buildgod("Demeter").getClass());
        assertEquals(m.getClass(),game.buildgod("Minotaur").getClass());
    }

    /**
     * Test of Count workers
     */
    @Test
    public void test_countworker(){

        game.setworkers(1,1,0);
        assertEquals(game.countworker(),1);
        game.setworkers(1,2,0);
        assertFalse(game.countworker() == 1);
        game.setworkers(1,2,0);
        assertEquals(game.countworker(),2);

    }
}
