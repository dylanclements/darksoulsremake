package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.IBonfire;

/**
 * Action to activate a bonfire
 */
public class BonfireLightAction extends Action {
    private final IBonfire bonfire;

    /**
     * Constructor.
     * @param bonfire a bonfire that implements the bonfire interface
     */
    public BonfireLightAction(IBonfire bonfire) {
        this.bonfire = bonfire;
    }

    /**
     * Lights the bonfire and prints the result
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string describing this action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (this.bonfire.lightBonfire()) {
            // Add this bonfire to the network after it's lit.
            BonfireNetwork bonfireNetwork = BonfireNetwork.getInstance();
            bonfireNetwork.addBonfire(this.bonfire);
            return actor.toString() + " lit the " + bonfire.getBonfireName() + " bonfire\n" + Message.BONFIRE_MESSAGE;
        } else {
            return bonfire.getBonfireName() + " is already lit";
        }
    }

    /**
     * Menu prompt for this action
     * @param actor The actor performing the action.
     * @return a string that describes what this action will do.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " lights the " + bonfire.getBonfireName() + " bonfire";
    }

    /**
     * Get the bonfire associated with this action
     * @return bonfire
     */
    public IBonfire getBonfire() {
        return bonfire;
    }
}
