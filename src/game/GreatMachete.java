package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Abilities;
import game.interfaces.WeaponSpecial;

public class GreatMachete extends MeleeWeapon implements WeaponSpecial {
    public static final int DAMAGE = 95;
    public static final int HIT_RATE = 60;

    /**
     * Constructor.
     *
     */
    public GreatMachete() {
        super("Yhorm’s Great Machete", 'G', GreatMachete.DAMAGE, "Wacks", GreatMachete.HIT_RATE);
        this.allowableActions.add(new EmberFormAction(this)); //is this correct??
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
    public boolean rageMode(Actor lordOfCinder) {
        if (lordOfCinder.hasCapability(Abilities.EMBER_FORM)){
            boostChanceToHit(30);
            return true;
        }

        return false;
    }
}
