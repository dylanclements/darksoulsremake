package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import game.interfaces.BonfireSpawn;
import game.interfaces.IBonfire;

/**
 * Action to trigger bonfire soft reset
 */
public class BonfireRestAction extends Action {
    private final IBonfire bonfire;

    /**
     * Constructor.
     * @param bonfire the bonfire.
     */
    public BonfireRestAction(IBonfire bonfire) {
        this.bonfire = bonfire;
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
            bonfireActor.setBonfireSpawn(this.bonfire.getBonfireLocation());
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
        return actor.toString() + " rests at the " + this.bonfire.getBonfireName() + " Bonfire.";
    }
}
