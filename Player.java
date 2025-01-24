/*Player.java
 * Angie Huang & Lucas Seto
 * 
 * The player and its interactions
 *      -
 */

import java.util.Random;

public class Player {
    // moving variables
    public int[] pos;           // current pos of player
    public int[] vel;
    private boolean canMove;    // flag for if player can move
    private int size;

    private Random rand;    

    // Constuctor 
    public Player(int px, int py){

        this.pos = new int[]{px, py};

        this.size = 25;

        this.rand = new Random();
    }

    public void move(){
        
    }
    public void update(){
        move();
    }
}