
import java.awt.Rectangle;

public class Bullet {
    public Rectangle hitbox;
    public int[] pos;
    
    public Bullet(int x, int y, int size){
        this.pos[0] = x;
        this.pos[1] = y;
        this.hitbox = new Rectangle(x, y, 5*size, 5*size);
    }
}

