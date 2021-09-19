package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;
import game.interfaces.ActiveSkill;


public class PickUpStormRulerAction extends PickUpItemAction {
    private final ActiveSkill stormRuler;

    /**
     * Construct actions with Storm Ruler instance
     * @param stormRuler special weapon for fighting yhorm
     */
    public PickUpStormRulerAction(ActiveSkill stormRuler) {
        super((Item) stormRuler);
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
        this.stormRuler.addActiveSkill(new ChargeAction(this.stormRuler));
        return pickUpMessage;
    }
}
