package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponAction;
import edu.monash.fit2099.engine.WeaponItem;
import game.interfaces.ActiveSkill;

public class WindSlashAction extends WeaponAction {
    private ActiveSkill stormRulerWindSlash;
    /**
     * Constructor
     *
     * @param weaponItem the weapon item that has capabilities
     */
    public WindSlashAction(ActiveSkill weaponItem) {
        super((WeaponItem) weaponItem);
        this.stormRulerWindSlash = weaponItem;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (stormRulerWindSlash.windSlash(actor)){
            return actor + " Unleashed Wind Slash.";
        }
        return actor + " Storm Ruller has to be charged first";
    }

    @Override
    public String menuDescription(Actor actor) {
        //ActiveSkill skill = this.stormRulerWindSlash;
        return String.format("%s is ready to use Wind Slash", actor);
    }
}
