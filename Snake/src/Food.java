import java.util.*;
import java.awt.Point;

/**
 * Food.java
 * 
 * This class creates and maintains the food location
 * 
 * @author Robert Kenney
 */
public class Food{

    /** the x location of the food */
    private int x;
    
    /** the y location of the food */
    private int y;

     /**
     * This is the constructor which initializes the food
     * and its x and y instance variables.
    */
    public Food(){
        x = Snake.COLS / 4;;
        y = Snake.ROWS / 4;;
    }
    
    /**
     * Gets the x location of the food
     * @return the x location of the food
    */
    public int getX(){
        return x;
    }

    /**
     * Gets the y location of the food
     * @return the y location of the food
    */
    public int getY(){
        return y;
    }

    /**
     * Moves the food to a random location
     * within the area the snake can occupy
     * so long that it is not on the snake
     * @param snake the snake which is on the board
    */
    public void moveFood(Snake snake){
        Random rand = new Random();
        x = rand.nextInt(Snake.COLS);
        y = rand.nextInt(Snake.ROWS);

        for(int i = 0; i < snake.getLength(); i++){
            if(snake.getSnake()[i].x == x && snake.getSnake()[i].y == y){
                moveFood(snake);
            }
        }
    }
    
}