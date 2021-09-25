package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * Action to trigger bonfire soft reset
 */
public class BonfireRestAction extends Action {

    /**
     * Initiates soft reset when Player resets at the Bonfire.
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return description of this action
     */
    public String execute(Actor actor, GameMap map) {
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
