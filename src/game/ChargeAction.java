package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.interfaces.ActiveSkill;
import game.interfaces.Consumable;

public class ChargeAction extends WeaponAction {
    private ActiveSkill stormRulerCharge;
    /**
     * Constructor
     *
     * @param weaponItem the weapon item that has capabilities
     */
    public ChargeAction(ActiveSkill weaponItem) {
        super((WeaponItem) weaponItem);
        this.stormRulerCharge = weaponItem;
        //needs to be an ActiveSkill but casts to WeaponItem??
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (stormRulerCharge.charge(actor)){
            return actor + " charging... ";
        }
        return actor + " Storm Ruller is charged";
    }

    @Override
    public String menuDescription(Actor actor) {
        ActiveSkill skill = this.stormRulerCharge;
        return String.format("%s charges Storm Ruler (%d/%d)", actor, skill.getCharges(), skill.getMaxCharges());
    }
}
