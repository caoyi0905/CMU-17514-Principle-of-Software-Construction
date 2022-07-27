package santorini;



import game.Game;
import game.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import token.Worker;

import java.util.List;


public class WorkerTest {



    Worker worker = new Worker();
    Worker worker2 = new Worker();
    Player parent = Player.PLAYER0;
    Game game = new Game();

    /**
     * Test of set parent
     */
    @Test
    public void test_set_parent(){
        worker.setParent(parent);
        assertEquals(parent,worker.getParent());
    }

    /**
     * Test of clone function
     */
    @Test
    public void test_clone(){
        worker.setPosition(1,1);
        worker.setParent(parent);
        worker2.setParent(parent);
        Worker cloneworker = worker.clone();
        assertFalse(worker.equals(cloneworker));
        assertEquals(worker.getX(),cloneworker.getY());
        assertEquals(worker.getY(),cloneworker.getY());
        assertEquals(worker.getParent().getValue(), cloneworker.getParent().getValue());
    }

    /**
     * Test of move worker
     */

    /**
     * Test 1: invalid movement of out of boundary
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_invalid_1(){
        worker.moveWorker(game,6,6);
    }

    /**
     * Test 2: invalid movement of occupied by another worker
     */
    @Test
    public void test_invalid_2(){
        worker.moveWorker(game,1,1);
        assertEquals(game,worker2.moveWorker(game,1,1));
        assertFalse(worker2.checkmove(game,1,1));
        assertFalse(game.equals(worker2.moveWorker(game,2,1)));
        assertTrue(worker2.checkmove(game,3,1));
    }

    /**
     * Test 3: invalid movement of exceeds maximum height
     */
    @Test
    public void test_invalid_3(){
        worker.buildtoken(game,3,3);
        worker.buildtoken(game,3,3);
        worker.buildtoken(game,3,2);
        assertEquals(worker2.moveWorker(game,3,3),game);
        assertFalse(worker2.checkmove(game,3,3));
        assertFalse(game.equals(worker2.moveWorker(game,3,2)));
        assertTrue(worker2.checkmove(game,3,4));
    }

    /**
     * Test of build token
     */

    /**
     * Test 1: invalid operation of build token out of the boundary
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void test_invalidbuild_1(){
        worker.buildtoken(game,6,6);
        worker.buildtoken(game,-1,-1);
    }

    /**
     * Test 2: invalid operation of build token in the grid which is occupied by another worker
     */
    @Test
    public void test_invalidbuild_2(){
        worker.moveWorker(game,4,4);
        assertEquals(game,worker2.buildtoken(game,4,4));
        assertFalse(worker2.checkbuild(game,4,4));
        assertFalse(game.equals(worker2.buildtoken(game,4,2)));
        assertTrue(worker2.checkbuild(game,4,2));
    }

    /**
     * Test 3: invalid operation of build token in the tower with dome
     */
    @Test
    public void test_invalidbuild_3(){
        worker.buildtoken(game,4,3);worker.buildtoken(game,4,3);
        worker.buildtoken(game,4,3);worker.buildtoken(game,4,3);
        assertFalse(worker.checkbuild(game,4,3));
        assertEquals(game,worker.buildtoken(game,4,3));
        assertFalse(game.equals(worker.buildtoken(game,1,4)));
        assertTrue(worker.checkbuild(game,2,4));
    }

    /**
     * Test of checkwin function in Worker
     */
    @Test
    public void test_checkwin(){
        worker2.setParent(parent);
        worker.buildtoken(game,0,0); worker.buildtoken(game,0,1); worker.buildtoken(game,0,1);
        worker.buildtoken(game,0,2); worker.buildtoken(game,0,2); worker.buildtoken(game,0,2);
        worker2.moveWorker(game,0,0);
        assertFalse(worker2.checkWin(game) != null);
        worker2.moveWorker(game,0,1);
        assertFalse(worker2.checkWin(game) != null);
        worker2.moveWorker(game,0,2);
        assertTrue(worker2.getParent().getValue() == worker2.checkWin(game).getValue());
    }



}
