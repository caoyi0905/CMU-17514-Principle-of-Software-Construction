package token;

public class Block extends Token {
    private final int level;

    /**
     * Initializes a new block instance.
     *
     * @param level The level of the block.
     */
    public Block(int level) {
        this.level = level;
    }

    /**
     * Get the value of level.
     *
     * @return The level number of the block.
     */
    public int getLevel() {
        return this.level;
    }

}
