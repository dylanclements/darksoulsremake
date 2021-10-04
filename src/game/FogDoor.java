package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class FogDoor extends Ground {
    private final Location front;
    private final Location back;

    /**
     * Constructor.
     * @param front location that allows player to enter the fog door
     * @param back location the allows player to enter the fog door
     */
    public FogDoor(Location front, Location back) {
        super('=');
        this.front = front;
        this.back = back;
    }

    /**
     * Actors cannot enter the location that fog door is placed in.
     * @param actor the Actor to check
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * Allow EnterFogDoorAction only.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return list of actions
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        if (actor instanceof Player) {
            if (location == this.getFront()) {
                // Player enters the front and exits through the back
                actions.add(new EnterFogDoorAction(this.getBack()));
            } else if (location == this.getBack()) {
                // Player enters the back and exits through the front
                actions.add(new EnterFogDoorAction(this.getFront()));
            }
            // Do nothing unless player is standing in front or back location
        }
        return actions;
    }

    /**
     * Blocks thrown objects.
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }

    /**
     * @return the front location
     */
    public Location getFront() {
        return front;
    }

    /**
     * @return the back location
     */
    public Location getBack() {
        return back;
    }
}
