package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Soul;

public class DeathAction extends Action {
    private final Actor killer;

    public DeathAction(Actor killer) {
        this.killer = killer;
    }

    public DeathAction() {
        this.killer = null;
    }

    @Override
    public String execute(Actor actor, GameMap map) {
        if (isPlayerDying(actor)) {
            Player player = (Player) actor;
            ResetManager resetter = ResetManager.getInstance();
            placeSoulToken(map.locationOf(actor), player);
            resetter.run(map);
            return "YOU DIED";
        } else {
            if (killer != null && killer instanceof Player) {
                // actor died at the hands of Player
                Soul soullable = (Soul) actor;
                soullable.transferSouls((Soul) killer);
            }
            // TODO: Situation where actor kills another actor. not in the game yet but might be
            map.removeActor(actor);
            return actor.toString() + " died";
        }
    }

    /**
     * Drop soul token on the ground when player dies.
     * Handles situation where player dies in valley.
     * @param deathLocation location on the map that player dies in
     * @param player the player
     */
    private boolean placeSoulToken(Location deathLocation, Player player) {
        SoulToken soulToken = new SoulToken();
        player.transferSouls(soulToken);

        if (deathLocation.getGround() instanceof Valley) {
            for (Exit exit : deathLocation.getExits()) {
                Location adjacentLocation = exit.getDestination();
                if (!(adjacentLocation.getGround() instanceof Valley)) {
                    adjacentLocation.addItem(soulToken);
                    return true;
                }
            }
            // if for some reason all exits are valleys, return false
            return false;
        }
        deathLocation.addItem(soulToken);
        return true;
    }

    /**
     * For whatever reason we need to kill an actor in the menu, this method is here. Otherwise, this is pretty useless.
     * @param actor The actor performing the action.
     * @return description of the menu option
     */
    @Override
    public String menuDescription(Actor actor) {
        return actor.toString() + " dies";
    }

    /**
     * Check if actor is instance of player, so we know when to handle Player death.
     * @param actor Actor that may or may not be a Player
     * @return true if the actor is of type Player else false
     */
    public boolean isPlayerDying(Actor actor) {
        return actor instanceof Player;
    }

    /**
     * Return the actor that is killing another actor
     * @return Actor
     */
    public Actor getKiller() {
        return killer;
    }
}
