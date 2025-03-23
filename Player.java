/*Player.java
 * Lucas Seto
 * 
 * The player and its interactions
 *      -
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Player extends Entity{
    private Rectangle platform_hitbox;

    private boolean jumping, falling, on_ground;
    public double angle, stamina;

    private final Random rand;    

    // Constuctor 
    public Player(int px, int py){
        super(new int[]{px, py}, new int[]{0, 0}, 25);

        this.pos = new int[]{px, py};
        this.vel = new int[]{0, 0};
        this.size = 25;

        platform_hitbox = new Rectangle(px - this.size/2, py, this.size, this.size - this.size/2);

        this.falling = true;
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - this.pos[1], GamePanel.mouse.pos[0] - this.pos[0]);

        this.stamina = 3.01;
        this.rand = new Random();
    }

    
    public void draw(Graphics2D g2D){
        g2D.setColor(new Color(255));
        g2D.drawImage(Utils.black_king, pos[0] - size/2 - GamePanel.offset[0], pos[1] - size/2 - GamePanel.offset[1], null);
        draw_gui(g2D);
    }
    public void draw_gui(Graphics2D g2D){
        for (int i = 1; i < stamina; i ++){
            g2D.setColor(new Color(0, 255, 255));
            g2D.fillRect(100 * i, 70, 75, 30);
        }
    }
    public void update()
    {
        this.angle = Math.atan2(GamePanel.mouse.pos[1] - GamePanel.SCREEN_HEIGHT/2, GamePanel.mouse.pos[0] - GamePanel.SCREEN_WIDTH/2);
        inputs();
        move();
        update_hitboxs();
        regen_stamina();
    }

    public void regen_stamina(){
        if (this.stamina < 3){
            this.stamina += 0.01;
        }
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
        if (GamePanel.key.keys[KeyEvent.VK_SHIFT] && !GamePanel.key.previous[KeyEvent.VK_SHIFT] && this.stamina >= 1){// dash
            vel[0] += 30*Math.cos(angle);
            vel[1] += 30*Math.sin(angle);
            this.stamina --;
        }
    }
    public void move(){
        check_collision_with_platform();
        for (Repulsion repulsion : GamePanel.repulsions){
            repulsion.obj_vel_change(this);
        }

        // speed cap
        if (this.vel[0] > 20){
            this.vel[0] = 20;
        }
        if (this.vel[1] > 20){
            this.vel[1] = 20;
        }
        if (this.vel[0] < -20){
            this.vel[0] = -20;
        }
        if (this.vel[1] < -20){
            this.vel[1] = -20;
        }
        // pos change
        this.pos[0] += this.vel[0];
        this.pos[1] += this.vel[1];

        // vel change
        this.vel[0] *= 0.99;

        // Jumping vs Falling logic
        if (!this.on_ground){
            this.falling = true;
        }
        if (this.jumping){
            this.falling = false;
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
        if (this.pos[0] > GamePanel.SCREEN_WIDTH){
            this.pos[0] = GamePanel.SCREEN_WIDTH;
        }
        if (this.pos[0] < 0){
            this.pos[0] = 0;
        }
        if (this.pos[1] > GamePanel.SCREEN_HEIGHT){
            this.pos[1] = GamePanel.SCREEN_HEIGHT;
        }
        if (this.pos[1] < 0){
            this.pos[1] = 0;
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
        if (!intersection){
            this.on_ground = false;
        }
    }
}