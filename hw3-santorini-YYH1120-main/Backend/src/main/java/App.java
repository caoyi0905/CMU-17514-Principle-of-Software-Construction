import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import fi.iki.elonen.NanoHTTPD;
import game.Game;
import game.GameState;
import game.Player;
import gameboard.IslandBoard;
import token.Demeter;
import token.Pan;
import token.Worker;

public class App extends NanoHTTPD {

    public static void main(String[] args) {

        try {
            new App();
        } catch (IOException ioe) {
            System.err.println("Couldn't start server:\n" + ioe);
        }
    }

    private Game game;
    private static int maxmove;
    private static int maxbuild;
    private List<Integer> coor = new ArrayList<>();

    public App() throws IOException {
        super(8080);

        this.game = new Game();

        start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
        System.out.println("\nRunning!\n");
    }

    @Override
    public Response serve(IHTTPSession session) {

        String uri = session.getUri();
        Map<String, String> params = session.getParms();
        if (uri.equals("/newgame") || uri.equals("/general") || uri.equals("/god")) {
            this.game = new Game();
        }
        else if (uri.equals("/Pan") || uri.equals("/Demeter") || uri.equals("/Minotaur")){
            String godname = uri.replace("/","");
            this.game.setGod(godname);

        }
        else if (uri.equals("/play")) {
            int x = Integer.parseInt(params.get("x"));
            int y = Integer.parseInt(params.get("y"));

            //The initialization of workers
            if (this.game.countworker()<=3 ){
                if (this.game.getBoard().getGrids(x,y).getWorker().size()==0){
                    this.game = this.game.setworkers(x,y,this.game.countworker());
                }
            } // First step: select a worker
            else if ( this.game.getBoard().getGrids(x,y).getWorker().size()>0 &&
                    this.game.getBoard().getGrids(x,y).getWorker().get(0).getParent() == this.game.getPlayer() &&
                    this.game.getMove() == 0){
                this.game.setMoveworker(this.game.getBoard().getGrids(x,y).getWorker().get(0));
                maxmove = this.game.getMoveworker().getMaxbuildtimes();
                this.game.cleanboarder();
                this.game.getMoveworker().setBordermove(this.game,x,y);

            } //Second step: move a worker
            else if (this.game.getMoveworker()!= null && this.game.getMove() == 0 && this.game.getBoard().getGrids(x,y).getBorder() == true){

                this.game = this.game.getMoveworker().moveWorker(this.game,x,y);
                this.game.setMove(1);
                this.game.cleanboarder();
                this.game.getMoveworker().setBorderbuild(this.game,x,y);


            } //Third step: build a token
            else if (this.game.getMove() == 1  && this.game.getBuild() == 0 && this.game.getBoard().getGrids(x,y).getBorder() == true){

                this.game = this.game.getMoveworker().buildtoken(this.game,x,y);
                this.game.setMove(0);
                this.game.getMoveworker().dosomething(this.game);

            }

            if (this.game.getWinner() != null){
                this.game.setMove(1);
                this.game.setBuild(1);
                this.game.cleanboarder();
            }

        }else if (uri.equals("/undo")) {
            System.out.println("UNDO");
            this.game = this.game.undo();

        }
        // Extract the view-specific data from the game and apply it to the template.
        GameState gameplay = GameState.forGame(this.game);

        return newFixedLengthResponse(gameplay.toString());
    }

    public static class Test{
        public String getText() {
            return "Hello World!";
        }
    }
}