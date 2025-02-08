/*Player.java
 * Angie Huang & Lucas Seto
 * 
 * The player and its interactions
 *      -
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Player {
    public int[] pos;           // current pos of player
    public int[] vel;
    private int size;
    private Rectangle bottom_hitbox;

    private boolean jumping, falling, on_ground;
    private double angle;

    private Random rand;    

    // Constuctor 
    public Player(int px, int py){

        this.pos = new int[]{px, py};
        this.vel = new int[]{0, 0};
        this.size = 25;
        bottom_hitbox = new Rectangle(px - this.size/2, py, this.size, this.size/2);

        this.falling = true;
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - this.pos[1], GamePanel.mouse.pos[0] - this.pos[0]);

        this.rand = new Random();
    }

    
    public void draw(Graphics2D g2D){
        g2D.setColor(new Color(255));
        g2D.drawImage(Utils.bingus, pos[0] - size/2, pos[1] - size/2, null);

        g2D.setColor(new Color(255, 0, 0));
        g2D.drawLine(this.pos[0], this.pos[1], this.pos[0] + (int) (100*Math.cos(angle)), this.pos[1] + (int)(100*Math.sin(angle)));
        //g2D.fill(bottom_hitbox);
    }
    public void update()
    {
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - this.pos[1], GamePanel.mouse.pos[0] - this.pos[0]);
        System.out.printf("%f, %f, %f, %d \n", Math.toDegrees(angle), 10*Math.cos(angle), 10*Math.sin(angle), vel[1]);
        inputs();
        move();
        update_hitboxs();
    }



    public void inputs(){
        if (this.vel[0] < 10 && this.vel[0] > -10){
            if (GamePanel.key.keys[KeyEvent.VK_A]){
                this.vel[0] -= 2;
            } else if (GamePanel.key.keys[KeyEvent.VK_D]){
                this.vel[0] += 2;
            }
            if (GamePanel.key.keys[KeyEvent.VK_W] && on_ground){
                this.jumping = true;
            }
        }
    }
    public void move(){
        this.pos[0] += this.vel[0];
        this.pos[1] += this.vel[1];

        this.vel[0] *= 0.99;

        if (this.jumping){
            falling = false;
            this.vel[1] --;
        } 
        if (this.falling){
            this.vel[1] ++;
        }
        if (this.vel[1] <= -12){
            this.jumping = false;
            this.falling = true;
        }

        check_collision_platform();
    }



    public void update_hitboxs(){
        this.bottom_hitbox.x = this.pos[0] - this.size/2;
        this.bottom_hitbox.y = this.pos[1];
    }
    public void check_collision_platform(){
        boolean intersection = false;
        for (Rectangle platform : GamePanel.platforms){
            if (colliding_top_platform(platform)){
                this.on_ground = true;
                intersection = true;
                if (!jumping)
                {
                    this.vel[1] = 0;
                    this.pos[1] = platform.y - this.size/2 + 1;
                }
            }
        }
        if (!intersection){
            this.on_ground = false;
        }
    }
    public boolean colliding_top_platform(Rectangle platform){
        boolean hitting_top = false;
        for (int i = platform.x; i < platform.x + platform.width; i ++){
            if (this.bottom_hitbox.contains(i, platform.y)){
                hitting_top = true;
            }
        }
        return hitting_top;
    }
}