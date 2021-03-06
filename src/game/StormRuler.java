package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.enums.Abilities;
import game.interfaces.IWindSlash;
import game.interfaces.Resettable;

/**
 * Special weapon used for defeating Yhorm.
 */
public class StormRuler extends Sword implements IWindSlash, Resettable {
    private static final int DAMAGE = 70;
    private static final int ACCURACY = 60;
    private static final int FULL_ACCURACY = 100;
    private static final int DAMAGE_MULTIPLIER = 2;

    private final ChargeAction chargeAction;
    private int charges;
    private final int maxCharges;

    /**
     *  Constructor.
     */
    public StormRuler() {
        super("Storm Ruler", '7', StormRuler.DAMAGE, "WAaaaBANG", StormRuler.ACCURACY);
        this.maxCharges = WindSlashAction.REQUIRED_CHARGES;
        this.charges = 0;
        this.chargeAction = new ChargeAction(this);
        this.registerInstance();
    }

    /**
     * Set the number of charges on this weapon
     * Update the name of Storm ruler to reflect these changes
     * @param charges charges
     */
    private void setCharges(int charges) {
        this.charges = charges;
        if (this.charges < this.maxCharges) {
            this.name = String.format("Storm Ruler (%d/%d)", this.charges, this.maxCharges);
        } else {
            this.name = "Storm Ruler (CHARGED)";
        }
    }

    /**
     * 1. Increment charges
     * 2. Checks if weapon is fully charges
     * 3. if fully charges, remove charge capability and add wind slash capability
     * @param actor actor that is charging this weapon
     * @return true if storm ruler is fully charged
     */
    @Override
    public boolean charge(Actor actor) {
        if (this.charges < this.maxCharges){
            this.setCharges(this.charges + 1);
        }
        if (this.charges == this.maxCharges) {

            // charges are full now, remove the charge actions
            this.removeChargeAction();

            // give the actor the wind slash capability
            actor.addCapability(Abilities.WIND_SLASH);
            return true;
        }
        return false;
    }

    /**
     * Set the storm ruler's specs
     * @param damage damage
     * @param accuracy accuracy
     */
    private void setAttributes(int damage, int accuracy){
        this.damage = damage;
        this.hitRate = accuracy;
    }

    @Override
    public int getCharges() {
        return charges;
    }

    @Override
    public int getMaxCharges() {
        return maxCharges;
    }

    /**
     * Check if attacker has wind slash capability.
     * If true, remove the charge action + capability and buff storm ruler damage
     * @param attacker actor who is about to perform wind slash
     */
    @Override
    public void prepareWindSlash(Actor attacker) {
        if (attacker.hasCapability(Abilities.WIND_SLASH)){
            this.removeChargeAction();
            attacker.removeCapability(Abilities.CHARGE);
            this.setAttributes(this.damage * StormRuler.DAMAGE_MULTIPLIER, StormRuler.FULL_ACCURACY);
        }
    }

    /**
     * 1. Set damage back to default
     * 2. Set charges to zero
     * 3. remove wind slash capability from attacker
     * 4. add the charge action back to allowable actions
     * @param attacker actor who is wind slashing
     */
    @Override
    public void finishWindSlash(Actor attacker) {
        if (attacker.hasCapability(Abilities.WIND_SLASH)) {
            this.setAttributes(StormRuler.DAMAGE, StormRuler.ACCURACY);
            this.setCharges(0);
            attacker.removeCapability(Abilities.WIND_SLASH);
            this.allowChargeAction();
        }
    }

    /**
     * Instead return our pick-up storm ruler class instead of swap weapon action
     * @param actor an actor that will interact with this item
     * @return PickUpStormRuler action
     */
    @Override
    public PickUpStormRulerAction getPickUpAction(Actor actor) {
        return new PickUpStormRulerAction(this);
    }

    /**
     * Add the charge action associated with this weapon to allowable actions
     */
    protected void allowChargeAction() {
        this.allowableActions.add(this.chargeAction);
    }

    /**
     * Remove the charge action associated with this weapon from the allowable actions
     */
    protected void removeChargeAction() {
        this.allowableActions.remove(this.chargeAction);
    }

    /**
     * On reset:
     * 1. set the charges to zero
     * 2. Set specs back to default
     * 4. Add charge actions back if it doesn't exist
     *
     * @param map instance of the game map
     */
    @Override
    public void resetInstance(GameMap map) {
        this.setCharges(0);
        this.setAttributes(StormRuler.DAMAGE, StormRuler.ACCURACY);
        if (!(this.getAllowableActions().contains(this.chargeAction))) {
            this.allowChargeAction();
        }
    }

    /**
     * Storm ruler is permanent
     * @return always true
     */
    @Override
    public boolean isExist() {
        return true;
    }
}
