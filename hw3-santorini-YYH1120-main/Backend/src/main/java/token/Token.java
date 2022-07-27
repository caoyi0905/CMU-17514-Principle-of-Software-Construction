package token;

public abstract class Token {
    private int x;
    private int y;
    private static final int MAXWIDTHFORWORKER = 5;

    /**
     * Set the position for the token, change the value of x and y with the input value.
     *
     * @param x
     * @param y
     * @return {@code true} if the coodinates of x and y is in the range of 1 to 5.
     */

    public boolean setPosition(int x, int y) {
        if ((x < MAXWIDTHFORWORKER && x >= 0) && (x < MAXWIDTHFORWORKER && x >= 0)) {
            this.x = x;
            this.y = y;
            return true;
        } else {
            System.out.println("Illegal coordinate of position!");
            return false;
        }
    }



    /**
     * Get the value of x.
     *
     * @return The value of x.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Get the value of y.
     *
     * @return The value of y.
     */
    public int getY() {
        return this.y;
    }

}

