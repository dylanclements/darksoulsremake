package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.ActiveSkill;

public class WindSlashAction extends WeaponAction {
    private final ActiveSkill stormRulerWindSlash;
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
        weapon.addCapability(Status.USING_WEAPON);
        LordOfCinder yhorm = null;
        String direction = null;
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location location = exit.getDestination();
            if (location.getActor() instanceof LordOfCinder) {
                yhorm = (LordOfCinder) location.getActor();
                direction = exit.getName();
            }
        }
        if (yhorm == null || direction == null) {
            return "You must be next to Yhorm to execute Wind Slash";
        }
        return stormRulerWindSlash.windSlash(actor, map, yhorm, direction);
    }

    @Override
    public String menuDescription(Actor actor) {
        //ActiveSkill skill = this.stormRulerWindSlash;
        return String.format("%s is ready to use Wind Slash", actor);
    }
}
