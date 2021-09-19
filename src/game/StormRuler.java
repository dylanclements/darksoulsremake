package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DoNothingAction;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.ActiveSkill;

public class StormRuler extends Sword implements ActiveSkill {
    private int charges;
    private int maxCharges;

    /**
     * Constructor.
     *
     */
    public StormRuler() {
        super("Storm Ruler", '7', 70, "WAaaaBANG", 60);
        this.maxCharges = 3;
        this.charges = 1;
        this.addCapability(Abilities.CHARGE);
        this.allowableActions.add(new ChargeAction(this));

    }

    @Override
    public boolean addCharge() {
        if (charges < maxCharges){
            charges += 1;
            return true;
        }
        return false;
    }

    @Override
    public boolean charge(Actor actor) {
        setAttributes(70,60);
        //should be player? but it throws an error
        if (charges == 3 && actor instanceof Player){
                actor.addCapability(Status.HOSTILE_TO_ENEMY);
                this.removeCapability(Abilities.CHARGE);
                this.addCapability(Abilities.WIND_SLASH);
                this.allowableActions.add(new WindSlashAction(this));
                this.allowableActions.remove(new ChargeAction(this));
        } else {
            this.addCharge();
            if (actor instanceof Player){
                actor.removeCapability(Status.HOSTILE_TO_ENEMY);
            }
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
    public boolean windSlash(Actor actor) {

        if (this.hasCapability(Abilities.WIND_SLASH)){
            setAttributes(140,100);
            if (actor instanceof LordOfCinder){
                actor.addCapability(Status.STUNNED);
            }
            this.removeCapability(Abilities.WIND_SLASH);
            this.allowableActions.remove(new WindSlashAction(this));
            this.addCapability(Abilities.CHARGE);
            this.allowableActions.add(new ChargeAction(this));
            return true;
        }
        return false;
    }
}
