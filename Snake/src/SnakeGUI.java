import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

/**
 * SnakeGUI.java
 * 
 * This class creates a graphical representation of a game of Snake.
 * 
 * @author Robert Kenney
 */
public class SnakeGUI extends JFrame implements KeyListener{

    /** Width of GUI */
    public static final int WIDTH = 500;

    /** Height of GUI */
    public static final int HEIGHT = 550;

    /** Delay between frames in milliseconds */
    public static final int FRAME_DELAY = 75;

    /** Required delay between inputs in frames */
    public static final int INPUT_DELAY = 1;

    /** A list of keys represented by key code that can be used to direct the snake up */
    public static final int[] UP_KEYS = {87, 38};

    /** A list of keys represented by key code that can be used to direct the snake down */
    public static final int[] DOWN_KEYS = {83, 40};

    /** A list of keys represented by key code that can be used to direct the snake left */
    public static final int[] LEFT_KEYS = {65, 37};
    
    /** A list of keys represented by key code that can be used to direct the snake right */
    public static final int[] RIGHT_KEYS = {68, 39};
    
    /** A list of keys represented by key code that can be used to pause */
    public static final int[] PAUSE_KEYS = {32, 16};

    /** The font used for the scoreboard text*/
    public static final Font SCOREBOARD_FONT = new Font("TimesRoman", Font.BOLD, 18);

    /** The color used for the scoreboard text */
    public static final Color SCOREBOARD_COLOR = Color.BLACK;

    /** The color used to represent the snake */
    public static final Color SNAKE_COLOR = Color.BLACK;

    /** The color used to represent the food*/
    public static final Color FOOD_COLOR = Color.RED;
    
    /** The color used to represent the background */
    public static final Color BACKGROUND_COLOR = Color.GREEN;
    
    /** The color used to represent the area the snake can occupy */
    public static final Color SNAKE_AREA_COLOR = Color.LIGHT_GRAY;

    /** An array of squares making up both the snake and area the snake occupies */
    private static JPanel[][] squares;

    /** This is the snake to be portrayed */
    private static Snake snake;

    /** This is the food to be portrayed */
    private static Food food;

    /** This label displays the current score and high score */
    private static JLabel scoreBoard;

    /** The score of the current life */
    private static int score;

    /** The high score of the current session */
    private static int highScore;

    /** This tells whether or not the game has been paused */
    private static boolean paused;

    /** This array list caches the moves the snake needs to make */
    private static ArrayList<Snake.Direction> moveCache;

    /**
     * This is the constructor which initializes and displays the GUI
    */
    public SnakeGUI(){
        //sets the snake and food to a new instance
        snake = new Snake();
        food = new Food();
        
        moveCache = new ArrayList<Snake.Direction>();
        //initializes the scoreboard
        scoreBoard = new JLabel("", SwingConstants.CENTER);
        scoreBoard.setFont(SCOREBOARD_FONT);
        scoreBoard.setForeground(SCOREBOARD_COLOR);

        //creates a new panel with a gridlayout for the area the snake can occupy
        JPanel grid = new JPanel();
        grid.setBackground(BACKGROUND_COLOR);
        grid.setLayout(new GridLayout(Snake.ROWS, Snake.COLS));

        //creates and initializes a list of the squares in the grid
        squares =  new JPanel[Snake.ROWS][Snake.COLS];
        for(int row = 0; row < Snake.ROWS; row++){
            for(int col = 0; col < Snake.COLS; col++){
                squares[row][col] = new JPanel();
                squares[row][col].setBackground(SNAKE_AREA_COLOR);
                grid.add(squares[row][col]);
            }
        }

        //sets the background and adds the scoreboard and grid to the frame
        this.getContentPane().setBackground(BACKGROUND_COLOR);
        add(scoreBoard, BorderLayout.NORTH);
        add(grid, BorderLayout.CENTER);

        //initializes the frame
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(dim.width / 2 - WIDTH / 2, dim.height / 2 - HEIGHT / 2);
        addKeyListener(this);
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Snake");
        setVisible(true);
    }

    /**
     * This method is unused
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }
    
    /**
     * This method handles user input from the keyboard
     * to control the direction of the snake
     * and pause the game
     * @param e the KeyEvent
     */
    @Override
    public void keyPressed(KeyEvent e) {
        for(int i = 0; i < UP_KEYS.length; i++){
            if(UP_KEYS[i] == e.getKeyCode()){
                if(moveCache.isEmpty()){
                    moveCache.add(Snake.Direction.UP);
                } else if(moveCache.get(0) != Snake.Direction.UP){
                    moveCache.add(Snake.Direction.UP);
                }
            }
        }
        for(int i = 0; i < DOWN_KEYS.length; i++){
            if(DOWN_KEYS[i] == e.getKeyCode()){
                if(moveCache.isEmpty()){
                    moveCache.add(Snake.Direction.DOWN);
                } else if(moveCache.get(0) != Snake.Direction.DOWN){
                    moveCache.add(Snake.Direction.DOWN);
                }
            }
        }
        for(int i = 0; i < LEFT_KEYS.length; i++){
            if(LEFT_KEYS[i] == e.getKeyCode()){
                if(moveCache.isEmpty()){
                    moveCache.add(Snake.Direction.LEFT);
                } else if(moveCache.get(0) != Snake.Direction.LEFT){
                    moveCache.add(Snake.Direction.LEFT);
                }
            }
        }
        for(int i = 0; i < RIGHT_KEYS.length; i++){
            if(RIGHT_KEYS[i] == e.getKeyCode()){
                if(moveCache.isEmpty()){
                    moveCache.add(Snake.Direction.RIGHT);
                } else if(moveCache.get(0) != Snake.Direction.RIGHT){
                    moveCache.add(Snake.Direction.RIGHT);
                }
            }
        }
        for(int i = 0; i < PAUSE_KEYS.length; i++){
            if(PAUSE_KEYS[i] == e.getKeyCode()){
                paused = true;
                JOptionPane.showMessageDialog(null,"Paused\n" + "Score: " + snake.getNumFoodEaten() + "  High Score: " + highScore);
                paused = false;
            }
        }
    }

    /**
     * This method is unused
     */
    @Override
    public void keyReleased(KeyEvent e) {

    }
    /**
     * This is the main method, which creates the SnakeGUI object the game is
     * played through, and houses the game loop.
     * 
     * @param args Command line arguments, not used
     */
    public static void main(String[]args){
        SnakeGUI gui = new SnakeGUI();

        while(true){
            while(!paused && snake.isAlive()){
                squares[snake.getHeadY()][snake.getHeadX()].setBackground(SNAKE_COLOR);

                if(snake.getLastRemoved() != null){
                    squares[snake.getLastRemoved().y][snake.getLastRemoved().x].setBackground(SNAKE_AREA_COLOR);
                }

                squares[food.getY()][food.getX()].setBackground(FOOD_COLOR);

                scoreBoard.setText("Score: " + score + "  High Score: " + highScore);
                if(!moveCache.isEmpty()){
                    snake.setDirection(moveCache.get(0));
                    moveCache.remove(0);
                }
                snake.move();
                if(snake.isOverFood(food.getX(), food.getY())){
                    snake.eat();
                    food.moveFood(snake);
                    score = snake.getNumFoodEaten();
                    if(score > highScore){
                        highScore = score;
                    }
                }

                try{
                    Thread.sleep(FRAME_DELAY);
                } catch(InterruptedException e){
                    System.err.println("Thread interrupted!");
                }
            }

            if(!snake.isAlive()){
                if(JOptionPane.showConfirmDialog(null, "You Lose. Play Again?") == JOptionPane.YES_OPTION){
                    for(int i = 1; i < snake.getLength(); i++){
                        squares[snake.getSnake()[i].y][snake.getSnake()[i].x].setBackground(SNAKE_AREA_COLOR);
                    }
                    if(snake.getLastRemoved() != null){
                        squares[snake.getLastRemoved().y][snake.getLastRemoved().x].setBackground(SNAKE_AREA_COLOR);
                    }
                    squares[food.getY()][food.getX()].setBackground(SNAKE_AREA_COLOR);
                    snake = new Snake();
                    food = new Food();
                    score = snake.getNumFoodEaten();
                    moveCache = new ArrayList<Snake.Direction>();
                } else {
                    gui.dispose();
                    break;
                }
            }
            if(paused){
                scoreBoard.setText("(Paused)");
            }
        }
    }
}