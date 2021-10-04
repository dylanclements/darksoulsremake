package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;


/**
 * Moves the player from one fog door location to the other
 */
public class EnterFogDoorAction extends Action {
    private final Location exit;

    /**
     * Constructor
     * @param exit the location that the player will be placed in after this action
     */
    public EnterFogDoorAction(Location exit) {
        this.exit = exit;
    }

    /**
     * Remove the player from the map, and place the player at the exit.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string describing this action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        map.addActor(actor, this.getExit());
        return actor.toString() + " moves through the fog door";
    }

    /**
     * Menu prompt to execute this action
     * @param actor The actor performing the action.
     * @return string that describes this action
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " enters fog door";
    }

    /**
     * @return get the location the player will be after this action.
     */
    public Location getExit() {
        return exit;
    }
}
