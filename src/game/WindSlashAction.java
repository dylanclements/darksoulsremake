package game;

import edu.monash.fit2099.engine.*;
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

    /**
     * Returns Yhorm instance if he is adjacent to the actor
     * @param actor The Player
     * @param map the game map
     * @return Yhorm instance else null
     */
    private LordOfCinder findYhorm(Actor actor, GameMap map) {
        for (Exit exit : map.locationOf(actor).getExits()) {
            Location location = exit.getDestination();
            if (location.getActor() instanceof LordOfCinder) {
                return (LordOfCinder) location.getActor();
            }
        }
        return null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        LordOfCinder yhorm = this.findYhorm(actor, map);
        if (yhorm == null) {
            return "You must be next to Yhorm to execute Wind Slash";
        }
        if (stormRulerWindSlash.windSlash(yhorm)){
            return actor + " Unleashed Wind Slash.";
        }
        return actor + " Storm Ruler has to be charged first";
    }

    @Override
    public String menuDescription(Actor actor) {
        //ActiveSkill skill = this.stormRulerWindSlash;
        return String.format("%s is ready to use Wind Slash", actor);
    }
}
