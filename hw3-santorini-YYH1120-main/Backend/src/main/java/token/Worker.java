package token;

import game.Game;
import game.Player;
import gameboard.IslandBoard;

import java.util.ArrayList;
import java.util.List;

public class Worker extends Token {
    private Player parent;
    private static final int MAXWIDTH = 7;
    private static final int MAXHEIGHT = 3;


    public int getMaxbuildtimes() {
        return 1;
    }

    public void setParent(Player player){
        this.parent = player;
    }
    /**
     * Get the name of the worker.
     *
     * @return The name of the worker.
     */


    public Player getParent() {
        return parent;
    }

    /**
     * Clone the worker object
     *
     * @return the cloned worker object
     */

    public Worker clone(){
        Worker w = new Worker();
        w.setPosition(this.getX(), this.getY());
        w.setParent(this.parent);

        return w;
    }

    /**
     * Move the worker to the destination cell
     *
     * @param game
     * @param x
     * @param y
     * @return the game with latest state
     */

    public Game moveWorker(Game game,  int x, int y) {

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
                    Game state = new Game(new IslandBoard(game.gridscloneformove(game.getBoard().getGrids())),game.getPlayer(),game.getHistory(),null,game.getMove(), game.getGod());
                    newHistory.add(state);

                    game.getBoard().getGrids(xsrc,ysrc).removeWorker();
                    game.getBoard().getGrids(xdes,ydes).moveWorker(this);
                    this.setPosition(xdes, ydes);
                    return new Game(game.getBoard(), game.getPlayer(), newHistory, game.getMoveworker(), game.getMove(), game.getGod());
                }
            }
        }

        return game;
    }

    /**
     * Build a token in the destination cell
     *
     * @param game
     * @param x
     * @param y
     * @return the game with the latest state
     */

    public Game buildtoken( Game game, int x, int y) {
        System.out.println("build");

        int xdes, ydes;

        xdes = x;
        ydes = y;

        if (game.getBoard().getGrids().get(xdes).get(ydes).getWorker().size() == 0) {
            if (game.getBoard().getGrids().get(xdes).get(ydes).getLevel() <= MAXHEIGHT) {

                List<Game> newHistory = new ArrayList<>(game.getHistory());
                Game state = new Game(new IslandBoard(game.gridsclone(game.getBoard().getGrids())),game.getPlayer(),game.getHistory(), game.getMoveworker(), 1,game.getGod());
                newHistory.add(state);

                if (game.getBoard().getGrids().get(xdes).get(ydes).getLevel() == MAXHEIGHT) {
                    Dome dome = new Dome();
                    game.getBoard().getGrids().get(xdes).get(ydes).addDome(dome);
                } else {
                    Block block = new Block(game.getBoard().getGrids().get(xdes).get(ydes).getLevel() + 1);
                    game.getBoard().getGrids().get(xdes).get(ydes).addBlock(block);
                }

                Player nextPlayer = game.getPlayer() == Player.PLAYER0 ? Player.PLAYER1 : Player.PLAYER0;
                return new Game(game.getBoard(), nextPlayer, newHistory, game.getMoveworker(), game.getMove(),game.getGod());
            }
        }

        return game;
    }

    /**
     * Check whether the worker meets the condition of victory
     *
     * @param game
     * @return the worker's player if the worker win the game
     */

    public Player checkWin(Game game){
        if (game.getBoard().getGrids(this.getX(),this.getY()).getLevel() == 3){
            return this.parent;
        }
        return null;
    }

    /**
     * Check whether the worker could move to the destination cell
     *
     * @param game
     * @param x
     * @param y
     * @return true if it could and false if it could not
     */

    public boolean checkmove (Game game, int x,int y){
        int xsrc = this.getX();
        int ysrc = this.getY();

        int oldlevel = game.getBoard().getGrids().get(xsrc).get(ysrc).getLevel();
        int newlevel = game.getBoard().getGrids().get(x).get(y).getLevel();

        if (x <= 4 && x >= 0 && y <= 4 && y >= 0 &&
                game.getBoard().getGrids().get(x).get(y).getWorker().size() == 0) {
            if (newlevel < MAXHEIGHT + 1) {
                if (newlevel <= oldlevel + 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Check whether the worker could build token in the destination cell
     *
     * @param game
     * @param x
     * @param y
     * @return true if it could and false if it could not
     */

    public boolean checkbuild (Game game, int x, int y){
        if (x <= 4 && x >= 0 && y <= 4 && y >= 0 &&
                game.getBoard().getGrids().get(x).get(y).getWorker().size() == 0) {
            if (game.getBoard().getGrids().get(x).get(y).getLevel() <= MAXHEIGHT) {
                return true;
            }
        }
        return false;
    }

    /**
     * Set the border for the worker's position, if the worker could move to the border cell, the boolean field would be set true.
     *
     * @param game
     * @param x
     * @param y
     */
    public void setBordermove (Game game, int x, int y) {
        int subx, suby;
        for (int i = 1; i < 9; i++) {
            subx = x + game.parsingdes(i).get(0);
            suby = y + game.parsingdes(i).get(1);
            if (subx>=0 && subx <=4 && suby >=0 && suby <=4 && this.checkmove(game,subx,suby) == true){
                game.getBoard().getGrids(subx,suby).setBorder(true);
            }
        }
    }

    /**
     * Set the border for the worker's position, if the worker could build the token in the border cell, the boolean field would be set true.
     *
     * @param game
     * @param x
     * @param y
     */

    public void setBorderbuild (Game game, int x, int y){
        int subx, suby;
        for (int i = 1; i < 9; i++) {
            subx = x + game.parsingdes(i).get(0);
            suby = y + game.parsingdes(i).get(1);
            if (this.checkbuild(game,subx,suby) == true){
                game.getBoard().getGrids(subx,suby).setBorder(true);
            }
        }

    }

    public void dosomething(Game game){
        game.cleanboarder();
        return ;
    }



}
