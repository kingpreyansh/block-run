package block.run;

import static block.run.BlockRun.SCENE_WIDTH;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class World {
    private Group root = new Group();
    private int height;
    private int width;
    private ImageView background;
    Random r = new Random();
    
    ArrayList<Obstacle> obstacles = new ArrayList<Obstacle>();
    
    /**
     * Creates a world with the given dimensions.
     *
     * @param width    width of the world, in pixels.
     * @param height   height of the world, in pixels.
     * 
     **/
    
    public World(int width, int height){
    this.width = width;
    this.height = height;
    
    Rectangle support = new Rectangle(SCENE_WIDTH, 10);
    background = new ImageView("img/background_play.jpg");
    
    root.getChildren().add(support);
    root.getChildren().add(background);
    
    }
    
    public Group getVisuals() {
        return root;
    }
    
    /**
     * Adds the given actor to the world, given that x and y are valid coordinates.
     * Returns true if the Actor was successfully added.
     *
     * @param actor The Actor to add to this world
     * @param x     The x-coordinate of where to add this Actor (must be valid)
     * @param y     The y-coordinate of where to add this Actor (must be valid)
     * @return True if the actor was added successfully, false otherwise.
     */
    public boolean addObstacle(Obstacle obstacle, int x, int y) {
        if (!isValid(x, y))
            return false;

        obstacles.add(obstacle);

        obstacle.getVisuals().setLayoutX(x * 10);
        obstacle.getVisuals().setLayoutY(586);

        root.getChildren().add(obstacle.getVisuals());
        //maybe scale size?

        return true;
    }

    /**
     * Removes the Actor from the list of current Actors. Also removes the corresponding visuals.
     *
     * @param actor The Actor to be removed.
     */
    public void removeObstacle(Obstacle obstacle) {
        root.getChildren().remove(obstacle.getVisuals());
        obstacles.remove(obstacle);
    }

    /**
     * Calls the act method of all Actors in this world at the time of the call.
     */
    public void produce() {
        //create a copy of current actors so that new actors created this turn don't get to act.
        //also the state of the list cannot change while this loop is executing.
        ArrayList<Obstacle> obstaclesClone = (ArrayList<Obstacle>) obstacles.clone();
        for (Obstacle a : obstaclesClone) {
            a.produce();
        }
    }

    /**
     * Returns true if there are no actors in the specified cell, false otherwise.
     *
     * @param x x-coordinate to check
     * @param y y-coordinate to check
     * @return True if there are no actors in the specified cell,
     */
    public boolean isEmpty(int x, int y) {
        for (Obstacle a : obstacles) {
            if (a.x == x && a.y == y)
                return false;
        }
        return true;
    }

    /**
     * Checks if the given coordinates are within this world.
     *
     * @param x x-coordinate of cell to check
     * @param y y-coordinate of cell to check
     * @return True if this cell is in the world
     */
    

    public boolean isValid(int x, int y) {
        return (x >= 130 && y >= 586 && y <= 600);
    }
    
    /**
     * Returns the first instance of a given class at a given location. Returns null if no object found.
     *
     * @param c Class of the Actor to retrieve.
     * @param x x-coordinate of the cell.
     * @param y y-coordinate of the cell.
     * @return The Actor at the given cell.
     */
    public Obstacle getObstacleAt(Class c, int x, int y) {
        for (Obstacle a : obstacles) {
            if (a.getClass() == c && a.x == x && a.y == y) {
                return a;
            }
        }
        return null;
    }

    /**
     * Returns a List of a all actors at a given location.
     *
     * @param x the x-coordinate of the cell to search
     * @param y the y-coordinate of the cell to search
     * @return A List of Actors. Could be empty.
     */
    public ArrayList<Obstacle> getAllObstacleAt(int x, int y) {
        ArrayList<Obstacle> results = new ArrayList<Obstacle>();
        for (Obstacle a : obstacles) {
            if (a.x == x && a.y == y) {
                results.add(a);
            }
        }
        return results;
    }

    /**
     * This method moves the actors visuals to the appropriate coordinates.
     *
     * @param actor Actor whose visuals are moving.
     * @param x     the new x-coordinate
     * @param y     the new y-coordinate
     */
    public void moveTo(Obstacle obstacle, int x, int y) {

        obstacle.getVisuals().setLayoutX(x * r.nextInt(0) + 10);
        obstacle.getVisuals().setLayoutY(586);
    }
    
}
