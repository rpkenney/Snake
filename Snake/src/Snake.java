import java.awt.Point;

/**
 * Snake.java
 * 
 * This class creates and maintains a snakes location, length, and points.
 * 
 * @author Robert Kenney
 */
public class Snake{

    /** The number of rows in the grid the snake can occupy */
    public static final int ROWS = 40;

    /** The number of columns in the grid the snake can occupy */
    public static final int COLS = 40;

    /** The starting length of the snake */
    public static final int STARTING_LENGTH = 4;

    /** The length added to the snake each time a piece of food is eaten */
    public static final int GROWTH_RATE = 3;

    /** The directions the snake can travel */
    public static enum Direction{LEFT, RIGHT, UP, DOWN};

    /** The x location of the head of the snake */
    private int headX;

    /** The y location of the head of the snake */
    private int headY;

    /** The point where the tail of the snake was last */
    private Point lastRemoved;

    /** The current length of the snake */
    private int length;

    /** A list of points representing the squares the snake occupies */
    private Point[] snake;

    /** The direction the snake is currently traveling */
    private Direction direction;

    /** The amount of food the snake has eaten */
    private int numFoodEaten;

    /**
     * This is the constructor which initializes the snake
     * and its instance variables.
    */
    public Snake(){
        headX = COLS / 2;
        headY = ROWS / 2;

        numFoodEaten = 0;

        direction = Direction.UP;

        length = STARTING_LENGTH;

        snake = new Point[ROWS * COLS];

        snake[0] = new Point(headX, headY);

    }

    /**
     * Gets the x location of the head
     * @return the x location of the head of the snake
    */
    public int getHeadX(){
        return headX;
    }

    /**
     * Gets the y location of the head
     * @return the y location of the head of the snake
    */
    public int getHeadY(){
        return headY;
    }

    /**
     * Gets the lists of points that the snake occupies
     * @return the lists of points that the snake occupies
    */
    public Point[] getSnake(){
        return snake;
    }

    /**
     * Gets the length of the snake
     * @return the current length of the snake
    */
    public int getLength(){
        int length = 0;
        while(snake[length] != null){
            length++;
        }
        return length;
    }
    /**
     * Gets the location of the last spot the tail was
     * @return the location of the last spot the tail was
    */
    public Point getLastRemoved(){
        return lastRemoved;
    }

    /**
     * Gets the amount of food eaten by the snake
     * @return the amount of food eaten
    */
    public int getNumFoodEaten(){
        return numFoodEaten;
    }

    /**
     * Changes the direction of the snake, given that the snake is not
     * currently moving in the opposite direction
     * @param newDirection the direction that the snake is being changed to
    */
    public void setDirection(Direction newDirection){
        if(newDirection == Direction.UP && direction != Direction.DOWN){
            direction = Direction.UP;
        }
        if(newDirection == Direction.DOWN && direction != Direction.UP){
            direction = Direction.DOWN;
        }
        if(newDirection == Direction.LEFT && direction != Direction.RIGHT){
            direction = Direction.LEFT;
        }
        if(newDirection == Direction.RIGHT && direction != Direction.LEFT){
            direction = Direction.RIGHT;
        }
    }

    /**
     * Moves the head of the snake forward
     * in the direction the snake is moving
     * and moves forward each point in the list
     * of points the snake occupies
     * @return the amount of food eaten
    */
    public void move(){
        switch(direction){
            case LEFT:
                headX--;
                break;
            case RIGHT:
                headX++;
                break;
            case UP:
                headY--;
                break;
            case DOWN:
                headY++;
                break;
        }

        lastRemoved = snake[length - 1];

        for(int i = length - 1; i > 0;  i--){
            snake[i] = snake[i - 1];
        }
        snake[0] = new Point(headX, headY);
    }

    /**
     * Increases the length of the snake, and 
     * the number of food eaten
     */
    public void eat(){
        numFoodEaten++;
        length += GROWTH_RATE;
    }

    /**
     * Verifies if the snake is over the food
     * @param foodX the x location of the food
     * @param foodY the y location of the food
     * @return true if the head of the snake is over the food, false otherwise
     */
    public boolean isOverFood(int foodX, int foodY){
        if(headX == foodX && headY == foodY){
            return true;
        }
        return false;
    }
    
    /**
     * Checks to see if the snake is still alive
     * @return true if the head of the snake is not on its body or the wall, false otherwise
    */
    public boolean isAlive(){
        for(int i = 1; i < getLength(); i++){
            if(headX == snake[i].x && headY == snake[i].y){
                return false;
            }
        }
        if(headX >= COLS || headX < 0 || headY >= ROWS || headY < 0){
            return false;
        }
        return true;
    }
}