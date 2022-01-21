package block.run;


import javafx.scene.Group;

/**
 * This class implements the methods that would be common to all Actors. It is designed to be extended by specific
 * subclasses. Since it is abstract, you cannot create an Actor object directly.
 */
public abstract class Obstacle {
    protected World world;
    protected int x;
    protected int y;

    Group root = new Group();

    /**
     * This creates a new Actor and adds it to the given world.
     *
     * @param x
     * @param y
     * @param world
     */
    public Obstacle(int x, int y, World world){
        this.x = x;
        this.y = y;
        this.world = world;

        world.addObstacle(this, x, y);
    }

    /**
     * Returns the group that holds all visual components of this Actor.
     *
     * @return The visual container.
     */
    public Group getVisuals(){
        return root;
    }

    /**
     * This method should be implemented by each subclass.
     */
    public abstract void produce();

    /**
     * This method checks if the given coordinate is in the world, and if so, moves there
     *
     * @param x target x-coordinate
     * @param y target y-coordinate
     */
    public void moveTo(int x, int y){
        if(world.isValid(x, y)) { //don't move off the world.
            //have world update visuals based on world size.
            world.moveTo(this, x, y);

            //update this actor's internal coordinates.
            this.x = x;
            this.y = y;
        }
    }

    /**
     * Returns this Actor's x-coordinate
     * @return the x-coordinate of this Actor
     */
    public int getX() {
        return x;
    }

    /**
     * Changes this Actor's x coordinate
     * @param x the new x-coordinate
     */
    public void setX(int x) {
        moveTo(x, y);
    }

    /**
     * Returns this Actor's y-coordinate
     * @return the y-coordinate of this Actor
     */
    public int getY() {
        return y;
    }

    /**
     * Changes this Actor's y coordinate
     * @param y the new y-coordinate
     */
    public void setY(int y) {
        moveTo(x, y);
    }

    /**
     * Returns this Actor's World.
     * @return This Actor's World.
     */
    public World getWorld() {
        return world;
    }
}
