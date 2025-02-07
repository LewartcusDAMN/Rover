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
    private boolean jump, falling;

    private Random rand;    

    // Constuctor 
    public Player(int px, int py){

        this.pos = new int[]{px, py};
        this.vel = new int[]{0, 0};

        this.size = 25;
        this.falling = true;

        this.rand = new Random();
    }

    public void inputs(){
        if (this.vel[0] < 10 && this.vel[0] > -10){
            if (GamePanel.key.keys[KeyEvent.VK_A]){
                this.vel[0] -= 2;
            } else if (GamePanel.key.keys[KeyEvent.VK_D]){
                this.vel[0] += 2;
            }
            if (GamePanel.key.keys[KeyEvent.VK_W] && !this.jump && !this.falling){
                this.jump = true;
                
            }
        }
    }

    public void move(){
        this.pos[0] += this.vel[0];
        this.pos[1] += this.vel[1];
        this.vel[0] *= 0.99;

        if (this.jump){
            this.vel[1] --;
        } 
        if (this.falling){
            this.vel[1] ++;
        }
        if (this.vel[1] <= -12){
            this.jump = false;
            this.falling = true;
        }

        if (colliding_with_floor()){
            this.falling = false;
            this.vel[1] = 0;
            this.pos[1] = GamePanel.floor.y -1;
        }
    }

    public boolean colliding_with_floor(){
        return GamePanel.floor.contains(pos[0], pos[1]);
    }

    public void draw(Graphics2D g2D){
        g2D.setColor(new Color(255));
        g2D.drawImage(Utils.bingus, pos[0] - size/2, pos[1] - size/2, null);
    }

    public void update(){
        inputs();
        move();
    }
}