package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.ActiveSkill;

public class StormRuler extends Sword implements ActiveSkill {
    private int charges;
    private final int maxCharges;

    private ChargeAction chargeActionCopy;
    private WindSlashAction windSlashActionCopy;

    /**
     * Constructor.
     *
     */
    public StormRuler() {
        super("Storm Ruler", '7', 70, "WAaaaBANG", 60);
        this.maxCharges = 3;
        this.charges = 0;
        this.addCapability(Abilities.CHARGE);
    }

    @Override
    public boolean charge(Actor actor) {
        if (charges < maxCharges){
            charges += 1;
            if (charges == 3) {
                this.removeCapability(Abilities.CHARGE);
                this.addCapability(Abilities.WIND_SLASH);
                this.addActiveSkill(new WindSlashAction(this));
                this.allowableActions.remove(this.chargeActionCopy);
            }
            return true;
        }
        return false;
    }

    public void setAttributes(int attack, int accuracy){
        this.damage = attack;
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

    @Override
    public boolean windSlash(LordOfCinder yhorm) {
        if (this.hasCapability(Abilities.WIND_SLASH)){
            setAttributes(140,100);

            yhorm.addCapability(Status.STUNNED);

            this.removeCapability(Abilities.WIND_SLASH);
            this.allowableActions.remove(this.windSlashActionCopy);

            this.charges = 0;

            this.addCapability(Abilities.CHARGE);
            this.addActiveSkill(this.chargeActionCopy);
            return true;
        }
        return false;
    }

    @Override
    public void addActiveSkill(Action action) {
        if (action instanceof ChargeAction) {
            this.chargeActionCopy = (ChargeAction) action;
        }
        else if (action instanceof WindSlashAction) {
            this.windSlashActionCopy = (WindSlashAction) action;
        }
        this.allowableActions.add(action);
    }

    /**
     * Instead of PickUpItemAction, return PickUpStormRulerAction
     * @param actor an actor that will interact with this item
     * @return action to pick up the storm ruler
     */
    @Override
    public PickUpStormRulerAction getPickUpAction(Actor actor) {
        if (portable)
            return new PickUpStormRulerAction(this);
        return null;
    }

}
