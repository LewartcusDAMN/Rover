/*Player.java
 * Angie Huang & Lucas Seto
 * 
 * The player and its interactions
 *      -
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Player {
    // moving variables
    public int[] pos;           // current pos of player
    public int[] vel;
    private int size;

    private Random rand;    

    // Constuctor 
    public Player(int px, int py){

        this.pos = new int[]{px, py};
        this.vel = new int[]{0, 0};

        this.size = 25;

        this.rand = new Random();
    }

    public void inputs(){
        if (this.vel[0] < 10 && this.vel[0] > -10){
            System.out.println(GamePanel.key.keys[KeyEvent.VK_A]);
            if (GamePanel.key.keys[KeyEvent.VK_A]){
                System.out.println("whatat");
                this.vel[0] --;
            }
        }
    }

    public void move(){
        this.pos[0] += this.vel[0];
        this.pos[1] += this.vel[1];
    }

    public void draw(Graphics2D g2D){
        g2D.setColor(new Color(255));
        g2D.fillOval(pos[0] - size/2, pos[1] - size/2, size, size);
    }

    public void update(){
        inputs();
        move();
    }
}