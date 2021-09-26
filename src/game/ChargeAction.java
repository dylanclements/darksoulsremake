package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.enums.Status;
import game.interfaces.IWindSlash;

/**
 * Action that charges a wind slash
 */
public class ChargeAction extends WeaponAction {
    private final IWindSlash stormRulerCharge;
    /**
     * Constructor
     *
     * @param weaponItem the weapon item that has capabilities
     */
    public ChargeAction(IWindSlash weaponItem) {
        super((WeaponItem) weaponItem);
        this.stormRulerCharge = weaponItem;

    }

    /**
     * Manages the execution of a charging action.
     * @param actor actor who is charging
     * @param map the game map
     * @return string describing the action
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (stormRulerCharge.charge(actor)){
            // charge returns true => storm ruler is fully charged
            if (actor.hasCapability(Status.ATTACK_DISABLED)) {
                actor.removeCapability(Status.ATTACK_DISABLED);
            }
            return actor + " Storm Ruler is charged";
        } else {
            // charge returns false => storm ruler is not fully charged
            if (!(actor.hasCapability(Status.ATTACK_DISABLED))) {
                actor.addCapability(Status.ATTACK_DISABLED);
            }
            return actor + " charging Storm Ruler...";
        }
    }

    /**
     * Prompt to execute this action
     * @param actor the actor who will be charging
     * @return string prompt that shows up on the menu
     */
    @Override
    public String menuDescription(Actor actor) {
        IWindSlash skill = this.stormRulerCharge;
        return String.format("%s charges Storm Ruler (%d/%d)", actor, skill.getCharges(), skill.getMaxCharges());
    }
}
