package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.ActiveSkill;

public class StormRuler extends Sword implements ActiveSkill {
    private int charges;
    private final int maxCharges;

    private static final int ACCURACY = 60;
    private static final int FULL_ACCURACY = 100;
    private static final int DAMAGE_MULTIPLIER = 2;

    private ChargeAction chargeActionCopy;
    private WindSlashAction windSlashActionCopy;

    /**
     *  Constructor
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
            if (charges <= 2)
                this.name = String.format("Storm Ruler (%d/%d)", this.getCharges(), this.getMaxCharges());
            if (charges == 3) {
                this.name = "Storm Ruler (CHARGED)";
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
    public String windSlash(Actor actor, GameMap map, LordOfCinder yhorm, String direction) {
        if (this.hasCapability(Abilities.WIND_SLASH)){
            // buff damage
            setAttributes(this.damage * StormRuler.DAMAGE_MULTIPLIER, StormRuler.FULL_ACCURACY);

            // execute the attack
            AttackAction attack = new AttackAction(yhorm, direction);
            String attackMessage = attack.execute(actor, map);

            // set back to normal damage
            setAttributes(this.damage / StormRuler.DAMAGE_MULTIPLIER, StormRuler.ACCURACY);

            // stun yhorm for one turn
            // TODO: in yhorm class, handle switching back to un-stunned capability
            yhorm.addCapability(Status.STUNNED);

            // remove wind slash as an action
            this.removeCapability(Abilities.WIND_SLASH);
            this.allowableActions.remove(this.windSlashActionCopy);

            // reset charges
            this.charges = 0;

            // add charge actions back
            this.addCapability(Abilities.CHARGE);
            this.addActiveSkill(this.chargeActionCopy);
            return attackMessage;
        }
        return actor.toString() + " cannot do wind slash at the moment";
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
     * Instead return our pick-up storm ruler class instead of swap weapon action
     * @param actor an actor that will interact with this item
     * @return PickUpStormRuler action
     */
    @Override
    public PickUpStormRulerAction getPickUpAction(Actor actor) {
        return new PickUpStormRulerAction(this);
    }
}
