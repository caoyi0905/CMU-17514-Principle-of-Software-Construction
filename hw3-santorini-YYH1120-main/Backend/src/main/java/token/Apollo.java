package token;

import game.Game;
import game.Player;

import java.util.ArrayList;
import java.util.List;

public class Apollo extends Worker{
    private Player parent;
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

        if (game.getBoard().getGrids().get(xdes).get(ydes).getWorker().size() == 0) {
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {
                    List<Game> newHistory = new ArrayList<>(game.getHistory());
                    newHistory.add(game);
                    game.getBoard().getGrids().get(xsrc).get(ysrc).removeWorker();
                    game.getBoard().getGrids().get(xdes).get(ydes).moveWorker(this);
                    this.setPosition(xdes, ydes);
                    return new Game(game.getBoard(), game.getPlayer(), newHistory, this, 1, game.getGod());

                }
            }
        }else if (game.getBoard().getGrids().get(xdes).get(ydes).getWorker().get(0).getParent()!=game.getPlayer()){
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {
                    List<Game> newHistory = new ArrayList<>(game.getHistory());
                    newHistory.add(game);
                    game.getBoard().getGrids().get(xsrc).get(ysrc).removeWorker();

                    game.getBoard().getGrids(xdes,ydes).getWorker().get(0).moveWorker(game,xsrc,ysrc);
                    game.getBoard().getGrids(xdes,ydes).removeWorker();

                    game.getBoard().getGrids().get(xdes).get(ydes).moveWorker(this);
                    this.setPosition(xdes, ydes);
                    return new Game(game.getBoard(), game.getPlayer(), newHistory, this, 1, game.getGod());
                }
            }
        }

        return game;
    }
}
