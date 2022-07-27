package game;

import gameboard.Grid;
import gameboard.IslandBoard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class GameState {

    private final Cell[] cells;
    private final Player winner;
    private final int turn;
    private String god = "";

    private GameState(Cell[] cells, Player winner, int turn, String god) {
        this.cells = cells;
        this.turn = turn;
        this.winner = winner;
        this.god = god;
    }


    @Override
    public String toString() {
        if (this.winner == null) {

            return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                    "\"turn\": " + String.valueOf(this.turn) + "," +

                    "\"god\": " + "\"" + String.valueOf(this.god) +
                    "\"}";
        }
        return "{ \"cells\": " + Arrays.toString(this.cells) + "," +
                "\"turn\": " + String.valueOf(this.turn) + "," +
                "\"winner\": " + String.valueOf(this.winner.value) + "," +

                "\"god\": " + "\"" + String.valueOf(this.god) +
                "\"}";
    }


    public static GameState forGame(Game game) {
        Cell[] cells = getCells(game);
        Player winner = getWinner(game);
        int turn = getTurn(game);
        String god = game.getGod();
        return new GameState(cells, winner, turn, god);
    }

    public static Cell[] getCells(Game game) {

        Cell cells[] = new Cell[25];
        IslandBoard board = game.getBoard();
        for (int x = 0; x <= 4; x++) {
            for (int y = 0; y <= 4; y++) {
                String text = "";
                String link = "";
                String clazz = "";
                Player player = null;
                if (board.getGrids(x,y).getWorker().size()>0){
                    player = board.getGrids(x,y).getWorker().get(0).getParent();
                }
                if (player == Player.PLAYER0) text = "X";
                else if (player == Player.PLAYER1) text = "O";
                else if (player == null) {
                    clazz = "playable";
                }
                link = "/play?x=" + x + "&y=" + y;
                if(board.getGrids(x,y).getBorder() == true){
                    text = "*";
                }
                if(board.getGrids(x,y).getLevel() == 1){
                    clazz = "one";
                }else if (board.getGrids(x,y).getLevel() == 2){
                    clazz = "two";
                }else if (board.getGrids(x,y).getLevel() == 3){
                    clazz = "three";
                }else if (board.getGrids(x,y).getLevel() == 4){
                    clazz = "dome";
                }

                cells[5 * y + x] = new Cell(text, clazz, link);
            }
        }

        return cells;
    }

    public static int getTurn(Game game) {
        return game.getPlayer().value;
    }

    public static Player getWinner(Game game) {
        return game.getWinner();
    }


}

class Cell {
    private final String text;
    private final String clazz;
    private final String link;

    Cell(String text, String clazz, String link) {
        this.text = text;
        this.clazz = clazz;
        this.link = link;
    }

    public String getText() {
        return this.text;
    }

    public String getClazz() {
        return this.clazz;
    }

    public String getLink() {
        return this.link;
    }


    @Override
    public String toString() {
        return "{ \"text\": \"" + this.text + "\"," +
                " \"clazz\": \"" + this.clazz + "\"," +
                " \"link\": \"" + this.link + "\"}";
    }
}