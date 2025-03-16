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
    private Rectangle platform_hitbox;

    private boolean jumping, falling, on_ground;
    private double angle;

    private Random rand;    

    // Constuctor 
    public Player(int px, int py){

        this.pos = new int[]{px, py};
        this.vel = new int[]{0, 0};
        this.size = 25;
        platform_hitbox = new Rectangle(px - this.size/2, py, this.size, this.size - this.size/2);

        this.falling = true;
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - this.pos[1], GamePanel.mouse.pos[0] - this.pos[0]);

        this.rand = new Random();
    }

    
    public void draw(Graphics2D g2D){
        g2D.setColor(new Color(255));
        g2D.drawImage(Utils.bingus, pos[0] - size/2, pos[1] - size/2, null);

        g2D.setColor(new Color(255, 0, 0));
        g2D.drawLine(this.pos[0], this.pos[1], this.pos[0] + (int) (100*Math.cos(angle)), this.pos[1] + (int)(100*Math.sin(angle)));
        g2D.fill(platform_hitbox);
    }
    public void update()
    {
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - this.pos[1], GamePanel.mouse.pos[0] - this.pos[0]);
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
        check_collision_with_platform();
        for (Repulsion repulsion : GamePanel.repulsions){
            int[] repulsion_vel = repulsion.vel_update(this.pos[0], this.pos[1]);
            this.vel[0] += repulsion_vel[0];
            this.vel[1] += repulsion_vel[1];
            
            this.pos[0] += repulsion_vel[0]/2;
            this.pos[1] += repulsion_vel[1]/2;
        }
        if (this.vel[0] > 20 || this.vel[0] < -20){
            this.vel[0] = 20;
        }
        if (this.vel[1] > 20 || this.vel[1] < -20){
            this.vel[1] = 20;
        }
        this.pos[0] += this.vel[0];
        this.pos[1] += this.vel[1];

        this.vel[0] *= 0.99;

        // Jumping vs Falling logic
        //
        if (!this.on_ground){
            this.falling = true;
        }
        if (this.jumping){
            falling = false;
            this.vel[1] --;
        } 
        if (this.falling){
            this.vel[1] ++;
        }
        if (this.jumping && this.vel[1] <= -12){
            this.jumping = false;
            this.falling = true;
        }

        // player remains on screen
        //
        if (pos[0] > GamePanel.SCREEN_WIDTH){
            pos[0] = GamePanel.SCREEN_WIDTH;
        }
        if (pos[0] < 0){
            pos[0] = 0;
        }
        if (pos[1] > GamePanel.SCREEN_HEIGHT){
            pos[1] = GamePanel.SCREEN_HEIGHT;
        }
        if (pos[1] < 0){
            pos[1] = 0;
        }
    }


    public void update_hitboxs(){
        this.platform_hitbox.x = this.pos[0] - this.size/2;
        this.platform_hitbox.y = this.pos[1];
    }
    public void check_collision_with_platform(){
        boolean intersection = false;
        for (Rectangle platform : GamePanel.platforms){
            
            if (platform.intersects(this.platform_hitbox)){
                intersection = true;
                if (this.vel[1] >= 0){
                    this.on_ground = true;
                    this.falling = false;
                    if (this.vel[1] != 0){
                        this.vel[1] = 0;
                    }
                    this.pos[1] = platform.y - this.size/2 + 1;
                }
            }
        }
        System.out.println(jumping + ", " + falling + ", " + on_ground);
        if (!intersection){
            this.on_ground = false;
        }
    }
}