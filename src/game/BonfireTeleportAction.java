package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.interfaces.IBonfire;

/**
 * Action to teleport an actor from one Bonfire to another
 */
public class BonfireTeleportAction extends Action {
    private final IBonfire entryBonfire;
    private final IBonfire exitBonfire;

    /**
     * Constructor.
     * @param entryBonfire bonfire being teleported from
     * @param exitBonfire bonfire to teleport to
     */
    public BonfireTeleportAction(IBonfire entryBonfire, IBonfire exitBonfire) {
        this.entryBonfire = entryBonfire;
        this.exitBonfire = exitBonfire;
    }

    /**
     * Performs the teleport
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return string describing this action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        map.removeActor(actor);
        map.addActor(actor, exitBonfire.getBonfireLocation());
        return this.menuDescription(actor);
    }

    /**
     * Prompt for player's menu
     * @param actor The actor performing the action.
     * @return string describing what this action will do.
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " teleports from " + entryBonfire.getBonfireName() +
                " to " + exitBonfire.getBonfireName();
    }
}
