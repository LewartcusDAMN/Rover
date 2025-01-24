/*GamePanel.java
 * Lucas Seto
 * 
 * main class for updating and painting the game
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    // Constants
    public static final int SCREEN_WIDTH = 800, SCREEN_HEIGHT = 800;

    // Class objects
    private Thread thread;
    private static Random rand;

    private KeyHandler kH = new KeyHandler();
    private MouseHandler mouse = new MouseHandler();
    private Menu menu;
    public static Player player;

    // Regular fields
    public int gamestate;
    private final int FPS = 60;
    public static boolean[] keys;

    public GamePanel() {// Constructor
        this.thread = new Thread();
        this.menu = new Menu();
        rand = new Random();

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.DARK_GRAY);
        this.addKeyListener(kH);
        this.addMouseListener(mouse);
        this.addMouseWheelListener(mouse);
        this.setFocusable(true);

        keys = kH.getKeys();
        player = new Player((GamePanel.SCREEN_WIDTH/32), (GamePanel.SCREEN_WIDTH/32));
        this.gamestate = 0;
    }

    public void startGameThread(){// Starts the game loop
        this.thread = new Thread(this);
        this.thread.start();
    }
    @Override
    public void run() {//1 second in nano secs per frame
        double frame_interval = 1000000000/FPS;// var for the amount of nanoseconds between frames 
        double next_frame_time = System.nanoTime() + frame_interval;// time for the next frame is the current time plus the interval
        while(thread != null){// Game loop
            this.update();
            this.repaint();
            try{
                double remaining_time = next_frame_time - System.nanoTime();// var for the remaining time before thenext frame
                remaining_time /= 1000000;
                if (remaining_time < 0){
                    remaining_time = 0;
                }
                Thread.sleep((long) remaining_time);
                next_frame_time += frame_interval;// the system time for the next frame is moved up one second (frame interval)
            }catch (InterruptedException e){// catch error
            }
        }
    }

    public void update(){
        try {// try to set the mouse position to where it is relative to the JPanel
            mouse.pos[0] = getMousePosition().x;
            mouse.pos[1] = getMousePosition().y;
        } catch (Exception e) {// incase mouse leaves the screen
        }

        switch (gamestate){
            case 0 -> {// menu
                System.out.println(mouse.pos[0] + " "+ mouse.pos[0]);
            }
        }
        kH.previous = keys.clone();
        mouse.previous = mouse.pressed;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D)g;

        switch (gamestate) {
            case 0 -> {// menu
            }
        }
        g2D.dispose();
    }
}