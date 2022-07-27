package gameboard;

import game.Game;
import token.Token;
import token.Block;
import token.Dome;
import token.Tower;
import token.Worker;

import java.util.ArrayList;
import java.util.List;

public class Grid {
    private final int x;
    private final int y;
    private static final int MAXHEIGHT = 3;
    private List<Worker> workers = new ArrayList<>();
    private Tower tower = new Tower();
    private boolean border = false;

    /**
     * Initializes a new grid instance with coordinates x and y.
     *
     * @param x The horizontal coordinate of the grid in the game board.
     * @param y The longitudinal coordinate of the grid in the game board.
     */
    public Grid(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean getBorder (){
        return this.border;
    }

    public void setBorder(boolean boo){
        this.border = boo;
    }

    /**
     * Get the level of the tower in this grid.
     *
     * @return The level of the tower which is built in this grid.
     */
    public int getLevel() {
        return tower.getLevel();
    }

    /**
     * Get the list of Worker in the grid.
     *
     * @return The {@link Worker} workers which occupy this grid.
     */
    public List<Worker> getWorker() {
        return this.workers;
    }

    public Grid clone(){
        Grid g = new Grid(this.x,this.y);
        List<Worker> w = new ArrayList<>();
        for (Worker subworker : this.getWorker()){
            w.add(subworker.clone());
        }
        Tower t = new Tower();
        t = this.tower.clone();
        g.workers = w;
        g.tower = t;
        g.border = this.border;
        return g;
    }

    public Grid cloneformove(){
        Grid g = new Grid(this.x,this.y);
        List<Worker> w = new ArrayList<>();
        for (Worker subworker : this.getWorker()){
            w.add(subworker.clone());
        }
        Tower t = new Tower();
        t = this.tower.clone();
        g.workers = w;
        g.tower = t;
        g.border = false;

        return g;
    }



    /**
     * Get the tower in this grid.
     *
     * @return The tower placed in this grid.
     */
    public Tower getTower() {
        return this.tower;
    }


    /**
     * Add the block to the tower in this grid.
     *
     * @param block The object to add and check.
     * @return {@code true} if the block meets the requirments.
     */
    public boolean addBlock(Block block) {
        if (block.getLevel() == this.tower.getLevel() + 1) {
            this.tower.add(block);
            return true;
        } else {
            System.out.println("Wrong operation of adding block!");
            return false;
        }
    }

    /**
     * Set the block to this grid.
     *
     * @param block The object to add on the tower in this grid.
     */
    public void setBlock(Block block) {
        this.tower = new Tower();
        this.tower.add(block);
    }

    /**
     * Set the tower in this grid.
     *
     * @param tower The object which contains tokens to add in this grid.
     */
    public void setTower(Tower tower) {
        this.tower = tower;
    }

    /**
     * Add the dome on the tower in this grid.
     *
     * @param dome The object which place on the top of the tower with 3 level.
     * @return {@code true} if the tower level is 3.
     */
    public boolean addDome(Dome dome) {
        if (this.tower.getLevel() == MAXHEIGHT) {
            this.tower.add(dome);
            return true;
        } else {
            System.out.println("Wrong operation of adding dome!");
            return false;
        }
    }

    /**
     * Move worker form old grid to this grid.
     *
     * @param worker The token to put in this grid.
     * @return {@code true} if this grid has not been occupied by another worker and the level is meets the requirements.
     */
    public boolean moveWorker(Worker worker) {
        if (this.workers.size() == 0 && this.tower.getLevel() < MAXHEIGHT+1) {
            this.workers.add(worker);
            return true;
        } else {
            System.out.println("Illegal action of moving worker!");
            return false;
        }
    }

    /**
     * Remove the worker in this grid.
     *
     */
    public void removeWorker() {

        this.workers = new ArrayList<>();

    }

    /**
     * Get the x coordinate of this grid.
     *
     * @return The horizontal coordinate of the grid.
     */


    public int getX(){
        return this.x;
    }

    /**
     * Get the y coordinate of this grid.
     *
     * @return The longitudinal coordinate of the grid in the game board.
     */
    public int getY(){
        return this.y;
    }

}
