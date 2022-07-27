package santorini;

import game.Game;
import game.Player;
import org.junit.Test;
import token.Pan;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class Pantest {
    Pan p = new Pan();
    Game game = new Game();
    Player parent = Player.PLAYER1;

    @Test
    public void test_pan_win_condition(){
        p.setParent(parent);
        p.buildtoken(game,1,1);
        p.buildtoken(game,1,1);
        p.buildtoken(game,0,1);
        p.moveWorker(game,0,1);
        assertFalse(p.checkWin(game) == p.getParent() );
        p.moveWorker(game,1,1);
        assertFalse(p.checkWin(game) == p.getParent() );
        p.moveWorker(game,1,2);
        assertTrue(p.checkWin(game) == p.getParent() );
    }
}
