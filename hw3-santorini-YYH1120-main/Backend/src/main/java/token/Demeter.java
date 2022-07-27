package token;

import game.Game;
import game.Player;
import gameboard.IslandBoard;

import java.util.ArrayList;
import java.util.List;

public class Demeter extends Worker{
    private int buildtimes = 0;
    private static final int MAXWIDTH = 7;
    private static final int MAXHEIGHT = 3;
    private int xlastbuild, ylastbuild;

    @Override
    public Game buildtoken(Game game, int x, int y) {
        System.out.println("buildtimes = " + buildtimes);

        int xdes, ydes,xsrc,ysrc;
        xsrc = this.getX();
        ysrc = this.getY();

        xdes = x;
        ydes = y;

        if (game.getBoard().getGrids().get(xdes).get(ydes).getWorker().size() == 0  || (this.buildtimes >=1 && x == this.getX() && y == this.getY())) {
            if (game.getBoard().getGrids().get(xdes).get(ydes).getLevel() <= MAXHEIGHT) {

                List<Game> newHistory = new ArrayList<>(game.getHistory());
                Game state = new Game(new IslandBoard(game.gridsclone(game.getBoard().getGrids())),
                        game.getPlayer(),game.getHistory(), game.getMoveworker(), 1,game.getGod());
                newHistory.add(state);
                if (xsrc == xdes && ysrc == ydes && this.buildtimes >=1 ){

                }else{

                    if (game.getBoard().getGrids().get(xdes).get(ydes).getLevel() == MAXHEIGHT) {
                        Dome dome = new Dome();
                        game.getBoard().getGrids().get(xdes).get(ydes).addDome(dome);
                    } else {
                        Block block = new Block(game.getBoard().getGrids().get(xdes).get(ydes).getLevel() + 1);
                        game.getBoard().getGrids().get(xdes).get(ydes).addBlock(block);
                    }
                }

                this.buildtimes += 1;
                this.xlastbuild = x;
                this.ylastbuild = y;
                Player nextPlayer = game.getPlayer();
                if (this.buildtimes > 1){

                    nextPlayer = game.getPlayer() == Player.PLAYER0 ? Player.PLAYER1 : Player.PLAYER0;
                }

                return new Game(game.getBoard(), nextPlayer, newHistory, this, game.getMove(),game.getGod());
            }
        }

        return game;
    }

    @Override
    public boolean checkbuild (Game game, int x, int y){
        if (x <= 4 && x >= 0 && y <= 4 && y >= 0 &&
                game.getBoard().getGrids().get(x).get(y).getWorker().size() == 0) {
            if (game.getBoard().getGrids().get(x).get(y).getLevel() <= MAXHEIGHT) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void dosomething(Game game){

        if (this.buildtimes==1){
            game.setMove(1);
            game.getBoard().getGrids(xlastbuild,ylastbuild).setBorder(false);
            game.getBoard().getGrids(this.getX(),this.getY()).setBorder(true);
        }else{
            System.out.println("clean the boarder!");
            game.setMove(0);
            this.buildtimes = 0;
            game.cleanboarder();
        }
    }

    @Override
    public Demeter clone(){
        System.out.println("call demeter clone");
        Demeter d = new Demeter();
        d.setPosition(this.getX(), this.getY());
        d.setParent(this.getParent());
        d.buildtimes = this.buildtimes;
        d.xlastbuild = this.xlastbuild;
        d.ylastbuild = this.ylastbuild;
        return d;
    }

    public int getBuildtimes() {
        return buildtimes;
    }
}
