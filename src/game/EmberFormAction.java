package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.WeaponSpecial;

public class EmberFormAction extends Action {


    private WeaponSpecial greatMachete;

    public EmberFormAction(WeaponSpecial greatMachete) {
        this.greatMachete = greatMachete;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        //we want ember form to interact with great machete only without creating circular dependency
        if (greatMachete.rageMode(actor)){
            //or should I call chance to hit instead??
            return actor + " Ember Form Activated";
        }
        return null;
    }

    @Override
    public String menuDescription(Actor actor) {
        return null;
    }
}
