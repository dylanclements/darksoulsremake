package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;


/**
 * Special rules for storm ruler, we want to add charge action only when player picks it up.
 */
public class PickUpStormRulerAction extends SwapWeaponAction {
    private final StormRuler stormRuler;

    /**
     * Construct actions with Storm Ruler instance
     * @param stormRuler special weapon for fighting yhorm
     */
    public PickUpStormRulerAction(StormRuler stormRuler) {
        super(stormRuler);
        this.stormRuler = stormRuler;
    }

    /**
     * Execute PickUpItemAction procedure, but also add capabilities to storm ruler
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return message from PickUpItemAction
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        String pickUpMessage = super.execute(actor, map);
        this.stormRuler.allowChargeAction();
        return pickUpMessage;
    }
}
