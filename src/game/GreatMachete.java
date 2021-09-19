package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.WeaponAction;
import game.enums.Abilities;
import game.interfaces.WeaponSpecial;

public class GreatMachete extends MeleeWeapon implements WeaponSpecial {

    /**
     * Constructor.
     *
     */
    public GreatMachete() {
        super("Yhorm’s Great Machete", 'G', 95, "Wacks", 60);
        this.allowableActions.add(new EmberFormAction(this)); //is this correct??
        //need to addCapability to Yhorm
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
