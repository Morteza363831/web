
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SnakeGame2 extends JPanel implements Runnable , KeyListener{
    // game strategis
    private final int width = 800;
    private final int height = 800;
    private final int cell_size = 10;
    private final int row = height/cell_size;
    private final int column = width/cell_size;
    private int snake_start_size = 6;
    private int max_food = 500;
    // game variables
    private List<Point> snake;
    private Point food;
    private List<Point> dams;
    private int direction;
    private int foodsEaten;
    private boolean gameOver;
    private boolean youWon;
    Random random;
    
    
    
    // constructor
    public SnakeGame2() {
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        random= new Random();
        game_init();
        dams();
    }
    // create game
    public void game_init(){
        snake = new ArrayList<>();
        direction = KeyEvent.VK_D;
        foodsEaten = 0;
        gameOver = false;
        // add snake to the game
        for (int i = snake_start_size ; i>=1 ; i--) {
            snake.add(new Point(i , 1));
            
        }
        foods();
    }
    // make dams and make game dificult
    public void dams() {
        dams = new ArrayList<>();
        Random random = new Random();
        int x = random.nextInt(20) + 10;
        for (int i = 0 ; i<x ; i++) {
            int dam_row = random.nextInt(row);
            int dam_column = random.nextInt(column);
            dams.add(new Point(dam_row , dam_column));
            if (dams.equals(snake)) {
                dams.remove(i);
                dams.add(new Point(dam_row , dam_column));
            }
            
        }
    }
    public void foods() {
        Random random = new Random();
        int food_row = random.nextInt(row);
        int food_column = random.nextInt(column);
        
        
            if (!snake.equals(dams)) {
                food = new Point(food_row , food_column);
            }
        
    }
    // move snake
    public void update_game() {
        // find the head
        Point head = snake.get(0);
        switch(direction) {
            case KeyEvent.VK_W :
                head = new Point(head.x , head.y-1);
                break;
            case KeyEvent.VK_S :
                head = new Point(head.x , head.y+1);
                break;
            case KeyEvent.VK_D :
                head = new Point(head.x+1 , head.y);
                break;
            case KeyEvent.VK_A :
                head = new Point(head.x-1 , head.y);
                break;
            case KeyEvent.VK_R :
                // Restart the game
            SnakeGame2 sg2 = new SnakeGame2();
            sg2.game_init();
            break;
        }
        // game over message shows when snake reach to his body or wall
        if (head.x<0 || head.y<0 || head.x>=column || head.y>=row || snake.contains(head) || dams.contains(head)) {
            JOptionPane.showMessageDialog(this, "Game over!");
            gameOver = true;
            return;
            
        }
        // won message when snake eat all foods
        if (head.equals(food)) {
            foodsEaten++;
            if (foodsEaten == max_food) {
                JOptionPane.showMessageDialog(this, "you Woon!");
                
            }
            
            // add a cell to snake body
            snake.add(0,head);
            foods();
        }
        else {
            snake.add(0 , head);
            snake.remove(snake.size()-1); 
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw snake
        g.setColor(new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256)));
        for (Point p : snake) {
            g.fillRect(p.x * cell_size, p.y * cell_size, cell_size, cell_size);
        }
        g.setColor(Color.gray);
        for (Point p :snake.subList(0, 1)) {
            g.fillRect(p.x * cell_size, p.y * cell_size, cell_size, cell_size);
        }
        g.setColor(Color.red);
        for (Point p : dams) {
            g.fillRect(p.x * cell_size, p.y * cell_size, cell_size, cell_size);
        }
        // Draw food
        g.setColor(Color.YELLOW);
        g.fillRect(food.x * cell_size, food.y * cell_size, cell_size, cell_size);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int keyPressed = e.getKeyCode();
        if (keyPressed == KeyEvent.VK_W && direction != KeyEvent.VK_S) {
            direction = KeyEvent.VK_W;
        }
        else if (keyPressed == KeyEvent.VK_S && direction != KeyEvent.VK_W) {
            direction = KeyEvent.VK_S;
        }
        else if (keyPressed == KeyEvent.VK_D && direction != KeyEvent.VK_A) {
            direction = KeyEvent.VK_D;
        }
        else if (keyPressed == KeyEvent.VK_A && direction != KeyEvent.VK_D) {
            direction = KeyEvent.VK_A;
        }
        
    }
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    // this is just for implements and no matter !
    @Override
    public void run() {
        while (gameOver == false) {
            update_game();
            try {
                // time to move sanke from a cell to another ...
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(SnakeGame2.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
            
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame frame = new JFrame("Snake Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new SnakeGame2());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread((Runnable) frame.getContentPane().getComponent(0)).start();
        
    }

    
    
    
    
    
    
    
    
    
    
    
    

    
    
}
