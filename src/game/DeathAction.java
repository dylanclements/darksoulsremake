package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.Soul;

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

        if (actorDying instanceof Player) {
            // Player dies
            ResetManager resetter = ResetManager.getInstance();
            this.placeSoulToken(map.locationOf(actorDying), (Player) actorDying);
            resetter.run(map);
            return "YOU DIED";
        } else {
            // Another actor dies
            if (killer != null && killer instanceof Player) {
                // actor died at the hands of Player, therefore transfer souls
                Soul soullable = (Soul) actorDying;
                soullable.transferSouls((Soul) killer);
            }
            map.removeActor(actorDying);
            return this.menuDescription(actorDying);
        }
    }

    /**
     * Drop soul token on the ground when player dies.
     * Uses player's previous location to drop the token if player dies in valley
     * @param deathLocation location on the map that player dies in
     * @param player the player
     */
    private void placeSoulToken(Location deathLocation, Player player) {
        if (deathLocation.getGround() instanceof Valley) {
            // Grab the player's previous location and the ground at this location
            Location playerPreviousLocation = player.getPreviousLocation();
            Ground oldGround = playerPreviousLocation.getGround();

            // create a soul token and have it remember the ground it replaced
            SoulToken soulToken = new SoulToken(oldGround);

            // transfer the soul token and set the ground to the soul token
            player.transferSouls(soulToken);
            playerPreviousLocation.setGround(soulToken);
        } else {
            // remember the ground the soul token will replace. then create it
            Ground oldGround = deathLocation.getGround();
            SoulToken soulToken = new SoulToken(oldGround);

            // transfer souls to the soul token and set the ground to the soul token
            player.transferSouls(soulToken);
            deathLocation.setGround(soulToken);
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
