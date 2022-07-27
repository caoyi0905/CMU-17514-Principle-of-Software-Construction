package santorini;

import gameboard.Grid;
import org.junit.Before;
import org.junit.Test;
import token.Block;
import token.Dome;
import token.Worker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;


public class GridTest {


    Grid grid = new Grid(1,2);
    Block block1 = new Block(1);
    Block block2 = new Block(2);
    Block block3 = new Block(3);
    Dome dome = new Dome();
    Worker worker = new Worker();


    /**
     * Test of setting block in the grid.
     */
    @Test
    public void testsetblock(){
        grid.addBlock(block1);
        assertEquals(grid.getTower().getItems().get(0),block1);
    }

    /**
     * Test of getting the level of the grid.
     */
    @Test
    public void testgetlevel1(){
        grid.addBlock(block1);
        assertEquals(grid.getLevel(),1);
    }

    /**
     * Test of adding block on the tower of the grid.
     */
    @Test
    public void testaddblock(){
        grid.addBlock(block1);
        assertFalse(grid.addBlock(block3));
        assertTrue(grid.addBlock(block2));
        assertEquals(grid.getLevel(),2);
    }

    /**
     *Test of adding dome on the tower of the grid.
     */
    @Test
    public void testadddome(){
        grid.addBlock(block1);
        grid.addBlock(block2);
        assertFalse(grid.addDome(dome));
        grid.addBlock(block3);
        assertTrue(grid.addDome(dome));
    }

    /**
     * Test of moving worker to the grid.
     */
    @Test
    public void testmoveworker(){
        assertTrue(grid.moveWorker(worker));
        assertFalse(grid.moveWorker(worker));
        assertEquals(grid.getWorker().size(),1);
    }

    /**
     * Test of remove worker
     */
    @Test
    public void testremoveworker(){
        grid.moveWorker(worker);
        grid.removeWorker();
        assertEquals(grid.getWorker().size(),0);
        grid.removeWorker();
        assertEquals(grid.getWorker().size(),0);
    }


}
