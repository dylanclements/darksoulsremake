package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.DropsSoulToken;
import game.interfaces.Soul;

/**
 * Death handler
 */
public class DeathAction extends Action {
    private final Actor killer;

    /**
     * Constructor to create DeathAction instance where an actor has killed another actor
     * @param killer the actor who killed another actor
     */
    public DeathAction(Actor killer) {
        this.killer = killer;
    }

    /**
     * Constructor to create DeathAction instance for natural death. (e.g. falling down a valley or undead dying)
     */
    public DeathAction() {
        this.killer = null;
    }

    /**
     * Execute a death procedure. Handles all cases.
     * @param actorDying the actor who is dying
     * @param map The map the actor is on.
     * @return string that describes the death
     */
    @Override
    public String execute(Actor actorDying, GameMap map) {
        Actions dropActions = new Actions();
        if (this.getKiller() != null) {
            // drop items to allow player to pick it up
            for (Item item : actorDying.getInventory())  {
                DropItemAction dropAction = item.getDropAction(this.getKiller());
                if (dropAction != null) {
                    dropActions.add(item.getDropAction(this.getKiller()));
                }
            }
            for (Action drop : dropActions) {
                drop.execute(actorDying, map);
            }
        }

        // Drop a soul token if the actor is meant to.
        if (actorDying instanceof DropsSoulToken) {
            ((DropsSoulToken) actorDying).placeSoulToken(map.locationOf(actorDying));
        }

        if (actorDying instanceof Player) {
            // Player dies
            ResetManager resetter = ResetManager.getInstance();
            resetter.run(map);
            return Message.YOU_DIED;
        } else {
            // Another actor dies
            if (killer != null && killer instanceof Player && actorDying instanceof Soul) {
                // actor died at the hands of Player, therefore transfer souls
                Soul soullable = (Soul) actorDying;
                soullable.transferSouls((Soul) killer);
            }

            map.removeActor(actorDying);
            if (actorDying instanceof LordOfCinder) {
                return Message.LORD_OF_CINDER_FALLEN;
            } else if (killer != null) {
                return actorDying + " died at the hands of " + killer;
            } else {
                return actorDying + " died.";
            }
        }
    }

    /**
     * For whatever reason we need to kill an actor in the menu, this method is here. Otherwise, this is pretty useless.
     * @param actor The actor performing the action.
     * @return description of the menu option
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " is killed.";
    }

    /**
     * Return the actor that is killing another actor
     * @return Actor
     */
    public Actor getKiller() {
        return killer;
    }
}
