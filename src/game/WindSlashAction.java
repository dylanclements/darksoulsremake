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

    /**
     * Method which checks if Yhorm is around the player and save his location
     * and the destination of WindSlush execution. Then executes WindSlashAction
     * @param actor
     * @param map
     * @return
     */
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

    /**
     * Provides a menu option description for the player when Wind Slash is ready to be used
     * @param actor
     * @return
     */
    @Override
    public String menuDescription(Actor actor) {
        return String.format("%s is ready to use Wind Slash", actor);
    }
}
