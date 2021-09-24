package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Abilities;
import game.interfaces.IGreatMachete;

public class GreatMachete extends MeleeWeapon implements IGreatMachete {
    public static final int DAMAGE = 95;
    public static final int HIT_RATE = 60;

    /**
     * Constructor.
     *
     */
    public GreatMachete() {
        super("Yhorm’s Great Machete", 'G', GreatMachete.DAMAGE, "Wacks", GreatMachete.HIT_RATE);
    }

    protected void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    @Override
    public WeaponAction getActiveSkill(Actor target, String direction) {
        return super.getActiveSkill(target, direction);
    }

    public void boostChanceToHit(int boost){
        this.hitRate +=boost;
    }

    @Override
    public String rageMode(Yhorm yhorm) {
        if (yhorm.hasCapability(Abilities.EMBER_FORM)){
            this.boostChanceToHit(30);
            return "Raaargh...";
        }
        return "Ember Form required to active rage mode";
    }
}
