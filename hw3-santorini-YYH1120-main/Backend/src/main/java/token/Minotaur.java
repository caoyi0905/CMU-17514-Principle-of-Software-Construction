package token;

import game.Game;
import game.Player;
import gameboard.IslandBoard;

import java.util.ArrayList;
import java.util.List;

public class Minotaur extends Worker{

    private static final int MAXHEIGHT = 3;

    @Override
    public Game moveWorker(Game game, int x, int y) {
        int xsrc;
        int ysrc;
        int oldlevel, newlevel;

        xsrc = this.getX();
        ysrc = this.getY();


        oldlevel = game.getBoard().getGrids().get(xsrc).get(ysrc).getLevel();
        newlevel = game.getBoard().getGrids().get(x).get(y).getLevel();

        if (game.getBoard().getGrids().get(x).get(y).getWorker().size() == 0) {
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {

                    List<Game> newHistory = new ArrayList<>(game.getHistory());
                    Game state = new Game(new IslandBoard(game.gridscloneformove(game.getBoard().getGrids())),game.getPlayer(),game.getHistory(),null,game.getMove(), game.getGod());
                    newHistory.add(state);

                    game.getBoard().getGrids().get(xsrc).get(ysrc).removeWorker();
                    game.getBoard().getGrids().get(x).get(y).moveWorker(this);
                    this.setPosition(x, y);
                    return new Game(game.getBoard(), game.getPlayer(), newHistory, this, game.getMove(), game.getGod());
                }
            }
        }else if (game.getBoard().getGrids(x,y).getWorker().get(0).getParent().getValue()!=game.getPlayer().getValue()){
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {

                    List<Game> newHistory = new ArrayList<>(game.getHistory());
                    Game state = new Game(new IslandBoard(game.gridscloneformove(game.getBoard().getGrids())),game.getPlayer(),game.getHistory(),null,game.getMove(), game.getGod());
                    newHistory.add(state);

                    game.getBoard().getGrids().get(xsrc).get(ysrc).removeWorker();
                    for (int j = 1 + 3; j < 9 + 3; j ++){
                        int i = j;
                        if (i > 8){
                            i = i-8;
                        }
                        int xx = x + game.parsingdes(i).get(0);
                        int yy = y + game.parsingdes(i).get(1);
                        if (xx<=4 && xx >= 0 && yy >=0 && yy <= 4 &&
                                game.getBoard().getGrids(xx,yy).getWorker().size()==0 && game.getBoard().getGrids(xx,yy).getLevel()<4){
                            game.getBoard().getGrids(x,y).getWorker().get(0).moveWorker(game,xx,yy);
                            game.getBoard().getGrids(x,y).removeWorker();
                            game.getBoard().getGrids().get(x).get(y).moveWorker(this);
                            this.setPosition(x, y);
                            return new Game(game.getBoard(), game.getPlayer(), newHistory, this, game.getMove(), game.getGod());

                        }
                    }

                }
            }
        }

        return game;
    }

    public boolean checkmove (Game game, int x,int y){
        int xsrc = this.getX();
        int ysrc = this.getY();

        int oldlevel = game.getBoard().getGrids(xsrc,ysrc).getLevel();
        int newlevel = game.getBoard().getGrids(x,y).getLevel();

        if (x <= 4 && x >= 0 && y <= 4 && y >= 0 &&
                ((game.getBoard().getGrids(x,y).getWorker().size() == 0) ||
                (game.getBoard().getGrids(x,y).getWorker().size()>0 &&
                        game.getBoard().getGrids(x,y).getWorker().get(0).getParent() != this.getParent()))) {


            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Minotaur clone(){
        System.out.println("call minotaur clone");
        Minotaur m = new Minotaur();
        m.setPosition(this.getX(), this.getY());
        m.setParent(this.getParent());

        return m;
    }

}
