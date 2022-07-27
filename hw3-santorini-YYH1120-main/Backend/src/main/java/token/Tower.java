package token;

import java.util.ArrayList;
import java.util.List;

public class Tower {
    private List<Token> items = new ArrayList<>();

    /**
     * Get the items {@link Token} in the tower.
     *
     * @return {@link Token} Return the list of token.
     */
    public List<Token> getItems() {
        return items;
    }

    public Tower(List<Token> token1){
        this.items = token1;
    }

    public Tower(){
        this.items = new ArrayList<>();
    }

    public Tower clone(){

        List<Token> token = new ArrayList<>();
        for (Token subtoken: this.items){
            token.add(subtoken);
        }
        Tower t = new Tower(token);
        return t;

    }

    /**
     * Add the token to the items.
     *
     * @param token The block or dome class.
     */
    public void add(Token token){
        items.add(token);
    }

    /**
     * Get the level of the tower which is the size of list items.
     *
     * @return The size of the items.
     */

    public int getLevel(){
        return items.size();
    }


}
