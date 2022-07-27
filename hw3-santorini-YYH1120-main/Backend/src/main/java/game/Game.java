package game;

import gameboard.Grid;
import gameboard.IslandBoard;
import token.Worker;
import token.Pan;
import token.Demeter;
import token.Minotaur;
import token.Apollo;

//import java.util.*;
import java.util.ArrayList;
import java.util.List;


public class Game {
    private final IslandBoard board;
    private final Player player;
    private final List<Game> history;
    private static final int MAXHEIGHT = 3;
    private  Worker moveworker = null;
    private  Worker buildworker = null;
    private int move = 0;
    private int build = 0;
    private String god = "";

    public void setMove(int move) {
        this.move = move;
    }

    public int getBuild() {
        return build;
    }

    public void setBuild(int build) {
        this.build = build;
    }

    public String getGod() {
        return god;
    }

    /**
     * Processing the string of god name.
     *
     * @param godname
     */

    public void setGod(String godname){
        if (this.god == ""){
            this.god = godname;
        }else {

            this.god = this.god.concat(" ").concat(godname);
        }

    }

    public Worker getBuildworker() {
        return buildworker;
    }

    public Worker getMoveworker() {
        return moveworker;
    }

    public void setMoveworker(Worker worker){
        this.moveworker = worker;
    }

    public void setBuildworker(Worker worker){
        this.buildworker = worker;
    }


    public Game() {
        this(new IslandBoard(), Player.PLAYER0);
    }

    public Game(IslandBoard board, Player nextPlayer) {
        List<Game> newhistory = new ArrayList<>();
        this.board = board;
        this.player = nextPlayer;
        this.history = newhistory;

    }

    public Game(IslandBoard board, Player nextPlayer, List<Game> history) {
        this.board = board;
        this.player = nextPlayer;
        this.history = history;
    }

    public Game(IslandBoard board, Player nextPlayer, List<Game> history, Worker moveworker, int move, String god) {
        this.board = board;
        this.player = nextPlayer;
        this.history = history;
        this.move = move;
        this.moveworker = moveworker;
        this.god = god;
    }

    /**
     * Convert the string of god name to the god class object.
     *
     * @param name
     * @return the instance of corresponding god class
     */
    public Worker buildgod (String name){
        if (name == null){
            return new Worker();
        }else if (name.equals("Pan")){
            return new Pan();
        }else if (name.equals("Demeter")){

            return new Demeter();
        }else if (name.equals("Minotaur")){
            return new Minotaur();
        }else if (name.equals("Apollo")){
            return new Apollo();
        }else{
            return new Worker();
        }
    }

    /**
     * Clone the grids in the game.
     *
     * @param grids
     * @return the cloned grids
     */

    public List<List<Grid>> gridsclone(List<List<Grid>> grids){
        List<List<Grid>> output = new ArrayList<>();
        for (List<Grid> sub: grids){
            List<Grid> subcopy = new ArrayList<>();
            for (Grid subsub: sub){
                subcopy.add(subsub.clone());
            }
            output.add(subcopy);
        }
        return output;
    }

    public List<List<Grid>> gridscloneformove(List<List<Grid>> grids){
        List<List<Grid>> output = new ArrayList<>();
        for (List<Grid> sub: grids){
            List<Grid> subcopy = new ArrayList<>();
            for (Grid subsub: sub){
                subcopy.add(subsub.cloneformove());
            }
            output.add(subcopy);
        }
        return output;
    }



    public IslandBoard getBoard() {
        return board;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Game> getHistory() {
        return history;
    }

    public int getMove() {
        return move;
    }

    /**
     * Withdraw the latest opeartion
     *
     * @return updated the game with the last element in history
     */

    public Game undo() {
        int s = this.history.size();
        if (s == 0) return this;

        Game oldGame = this.history.get(s-1);

        this.history.remove(s-1);
        return new Game(oldGame.board, oldGame.player, oldGame.history, oldGame.getMoveworker(), oldGame.getMove(), oldGame.getGod());
    }

    /**
     * Set the worker for the initialization
     *
     * @param x
     * @param y
     * @param turn
     * @return the updated state of game with worker initialization
     */
    public Game setworkers( int x, int y, int turn){

        String[] godnamelist = this.god.split(" ");
        ArrayList<String> godname1 = new ArrayList<String>();
        for (String e : godnamelist){
            if (!godname1.contains(e)){
                godname1.add(e);
            }
        }

        List<Game> newHistory = new ArrayList<>(this.history);
        Game state = new Game(new IslandBoard(this.gridsclone(this.getBoard().getGrids())),
                this.getPlayer(),this.history, this.getMoveworker(),this.move,this.getGod());
        newHistory.add(state);

        Worker worker = new Worker();
        if (this.god == ""){
            System.out.println("");
        }else{
            worker = buildgod(godname1.get(this.player.value));
        }

        worker.setParent(this.player);
        worker.setPosition(x,y);

        this.board.getGrids(x,y).moveWorker(worker);

        if (turn ==1 || turn == 3){
            Player nextPlayer = this.player == Player.PLAYER0 ? Player.PLAYER1 : Player.PLAYER0;
            return new Game(this.board, nextPlayer, newHistory, this.getMoveworker(),this.move,this.getGod());
        }else{
            return new Game(this.board, this.player, newHistory, this.getMoveworker(),this.move,this.getGod());
        }
    }

    /**
     * count the number of worker in the game board
     *
     * @return the number of worker
     */
    public int countworker(){
        int count = 0 ;
        for (List<Grid> gridlist : this.board.getGrids()){
            for (Grid grid: gridlist){
                count += grid.getWorker().size();

            }
        }
        return count;
    }


    /**
     * Get the winner of the game
     *
     * @return the worker's player if the worker meets the condition of victory. Otherwise, return null.
     */
    public Player getWinner(){
        for (List<Grid> sub : this.getBoard().getGrids()){
            for (Grid subsub: sub){
                if (subsub.getWorker().size()>0 && subsub.getWorker().get(0).checkWin(this) != null){
                    return subsub.getWorker().get(0).checkWin(this);
                }
            }
        }
        return null;
    }

    public void cleanboarder(){
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                this.getBoard().getGrids(i,j).setBorder(false);
            }
        }
    }

    /**
     * Parsing the input des to the {@link Integer} destination coordinates.
     *
     * @param des The input parameter which refer the coordinate of a grid.
     * @return The List of integer which contains two values: the x coordinate and the y coordinate.
     */

    public List<Integer> parsingdes(int des) {
        int x;
        int y;
        List<Integer> result = new ArrayList<>();
        if (des <= MAXHEIGHT) {
            y = -1;
        } else if (des > (MAXHEIGHT+1) && des < (MAXHEIGHT*MAXHEIGHT-1)) {
            y = 1;
        } else {
            y = 0;
        }
        if (des == MAXHEIGHT-1 || des == 2*MAXHEIGHT) {
            x = 0;
        } else if (des > MAXHEIGHT-1 && des < 2*MAXHEIGHT) {
            x = 1;
        } else {
            x = -1;
        }
        result.add(y);
        result.add(x);
        return result;
    }

}
