package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.interfaces.BonfireSpawn;

/**
 * Action to trigger bonfire soft reset
 */
public class BonfireRestAction extends Action {
    private final Location bonfireLocation;

    /**
     * Constructor. Note the bonfire's location which the actor is resting at
     * @param bonfireLocation the bonfire's location
     */
    public BonfireRestAction(Location bonfireLocation) {
        this.bonfireLocation = bonfireLocation;
    }

    /**
     * Changes the actors spawn location to bonfire's location if the actor implements BonfireSpawn
     * Initiates soft reset when Player resets at the Bonfire.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return description of this action
     */
    public String execute(Actor actor, GameMap map) {
        if (actor instanceof BonfireSpawn) {
            BonfireSpawn bonfireActor = (BonfireSpawn) actor;
            bonfireActor.setBonfireSpawn(this.bonfireLocation);
        }

        ResetManager resetter = ResetManager.getInstance();
        resetter.run(map);
        return menuDescription(actor);
    }

    /**
     * Describes BonfireRestAction
     * @param actor The actor performing the action.
     * @return string that describes this action.
     */
    public String menuDescription(Actor actor) {
        return actor.toString() + " rests at Bonfire.";
    }
}
