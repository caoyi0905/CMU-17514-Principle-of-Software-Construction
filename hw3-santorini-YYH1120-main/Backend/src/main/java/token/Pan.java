package token;

import game.Game;
import game.Player;
import gameboard.IslandBoard;

import java.util.ArrayList;
import java.util.List;

public class Pan extends Worker{
    private boolean movedown = false;
    private static final int MAXHEIGHT = 3;

    @Override
    public Game moveWorker(Game game, int x, int y) {
        int xsrc, xdes;
        int ysrc, ydes;
        int oldlevel, newlevel;

        xsrc = this.getX();
        ysrc = this.getY();

        xdes = x;
        ydes = y;
        oldlevel = game.getBoard().getGrids().get(xsrc).get(ysrc).getLevel();
        newlevel = game.getBoard().getGrids().get(xdes).get(ydes).getLevel();
        if (oldlevel - newlevel >=2){
            this.movedown = true;
        }
        if (game.getBoard().getGrids().get(xdes).get(ydes).getWorker().size() == 0) {
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {

                    List<Game> newHistory = new ArrayList<>(game.getHistory());
                    Game state = new Game(new IslandBoard(game.gridscloneformove(game.getBoard().getGrids())),game.getPlayer(),game.getHistory(),null,game.getMove(), game.getGod());
                    newHistory.add(state);

                    game.getBoard().getGrids().get(xsrc).get(ysrc).removeWorker();
                    game.getBoard().getGrids().get(xdes).get(ydes).moveWorker(this);
                    this.setPosition(xdes, ydes);
                    return new Game(game.getBoard(), game.getPlayer(), newHistory, this, 1, game.getGod());
                }
            }
        }

        return game;
    }

    @Override
    public Player checkWin(Game game){
        if (game.getBoard().getGrids(this.getX(),this.getY()).getLevel() == 3){
            return this.getParent();
        }
        if (this.movedown == true){
            return this.getParent();
        }
        return null;
    }

    @Override
    public Pan clone(){
        System.out.println("call pan clone");
        Pan p = new Pan();
        p.setPosition(this.getX(), this.getY());
        p.setParent(this.getParent());
        p.movedown = this.movedown;

        return p;
    }
}
