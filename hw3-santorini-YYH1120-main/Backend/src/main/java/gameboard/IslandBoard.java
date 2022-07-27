package gameboard;

import token.Block;
import token.Dome;
import token.Token;
import token.Tower;

import java.util.ArrayList;
import java.util.List;


public class IslandBoard {
    private final List<List<Grid>> grids;
    private static final int MAXWIDTH = 7;
    private static final int MAXHEIGHT = 3;

    /**
     * Initializes a new islandboard which is the main body of game board.
     */


    public IslandBoard() {
        List<List<Grid>> grid = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            List<Grid> sub = new ArrayList<>();
            for (int j = 0; j < 5; j++){
                Grid subsub = new Grid(i,j);
                sub.add(subsub);
            }
            grid.add(sub);
        }
        this.grids = grid;
        System.out.println(this.grids);

        //Hint for user
        System.out.println("The index of direction is shown as follows:");
        System.out.println("1  2  3");
        System.out.println("8  *  4");
        System.out.println("7  6  5");
        System.out.println();
    }

    public IslandBoard(List<List<Grid>> grids){
        this.grids=grids;
    }



    /**
     * Get the nested list of grids in this island board class.
     *
     * @return The nested list contains all the grids in the game board.
     */
    public List<List<Grid>> getGrids() {
        return grids;
    }

    public Grid getGrids(int x,int y){
        return grids.get(x).get(y);
    }


}
