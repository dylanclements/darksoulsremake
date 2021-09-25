package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Soul;

/**
 * A class that defines an action for the player to pick up a soul token
 */
public class PickUpSoulsAction extends Action {
    private final Location location;
    private final Ground oldGround;

    /**
     * Construct this action with the location of the SoulToken and the ground it replaced
     * @param location the location of the SoulToken
     * @param oldGround the ground that the SoulToken replaced before it was dropped
     */
    public PickUpSoulsAction(Location location, Ground oldGround) {
        this.location = location;
        this.oldGround = oldGround;
    }

    /**
     * 1. Check if actor can pick up souls.
     * 2. If actor can pick up souls, transfer the souls from SoulToken to the actor
     * 3. Set the ground back to the old ground
     * 4. print SOULS RETRIEVED
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message that describes the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        // Only let player do this for now
        if (actor instanceof Soul) {
            SoulToken soulToken = (SoulToken) this.location.getGround();
            soulToken.transferSouls((Soul) actor);
            this.location.setGround(this.oldGround);
            return Message.SOULS_RETRIEVED;
        }
        return actor + " cannot pick up souls";
    }

    /**
     * Player menu prompt for this action
     * @param actor The actor performing the action.
     * @return string that describes what the action will do
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " picks up soul token";
    }
}
