package santorini;

import game.Game;
import game.Player;
import org.junit.Test;
import token.Minotaur;
import token.Worker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Minotaurtest {
    Game game = new Game();
    Minotaur m = new Minotaur();
    Worker worker = new Worker();
    Player player1 = Player.PLAYER1;
    Player player0 = Player.PLAYER0;

    /**
     * Test Minotaur could move to the grid which is occupied by another worker
     */
    @Test
    public void test_move_to_occupied_grid(){

        m.setParent(player0);
        worker.setParent(player1);
        worker.moveWorker(game,1,1);
        assertTrue(m.checkmove(game,1,1));
        worker.setParent(player0);
        assertFalse(m.checkmove(game,1,1));

    }
}
