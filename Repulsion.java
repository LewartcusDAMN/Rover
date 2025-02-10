
import java.awt.Color;
import java.awt.Graphics2D;

public class Repulsion {
    public int radius;
    public int[] pos;
    public int status;
    private final int max_size;

    public Repulsion(int max_size, int x, int y){
        this.pos = new int[]{x, y};
        this.status = 1;
        this.max_size = max_size;
    }

    public void draw(Graphics2D g2D){
        g2D.setColor(Color.red);
        g2D.fillOval(this.pos[0] - this.radius, this.pos[1] - this.radius, this.radius*2, this.radius*2);
    }
    public void update(){
        if (this.radius >= this.max_size){
            this.status = 0;
        }
        switch (this.status) {
            case 1 -> {
                this.radius += 5;
            }
            case 0 -> {
                this.radius -= 2;
            }
        }
    }

    public int[] vel_update(int obj_x, int obj_y){
        int vel_x = 0;
        int vel_y = 0;
        int dist = (int)(Math.sqrt(Math.pow(obj_x - this.pos[0], 2) + Math.pow(obj_y - this.pos[1], 2)));
        if (dist <= this.radius){
            vel_x = (obj_x - this.pos[0])/4;
            vel_y = (obj_y - this.pos[1])/4;
        }

        return new int[]{vel_x, vel_y};
    }
}
