package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.*;

public class Skeleton extends Actor implements Soul, Provocative, Resettable, ActorStatus {
    public final static int skeletonSouls = 250;

    private Behaviour behaviour;
    private final Location spawnLocation;

    public Skeleton(String name, Location spawnLocation) {
        super(name, 's', 100);
        this.spawnLocation = spawnLocation;
        this.behaviour = new WanderBehaviour();
        this.addItemToInventory(new BroadSword());
        this.registerInstance();
    }

    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (behaviour instanceof WanderBehaviour) {
            Location currentLocation = map.locationOf(this);
            for (Exit exit : currentLocation.getExits()) {
                // scan exits
                Location location = exit.getDestination();
                if (location.containsAnActor()) {
                    Actor actor = map.getActorAt(location);
                    if (actor instanceof Player) {
                        // if player is adjacent to skeleton, switch to aggro
                        this.switchAggroBehaviour(actor);
                        break;
                    }
                }
            }
        }
        return behaviour.getAction(this, map);
    }

    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    /**
     * 1. Remove the skeleton from the map
     * 2. Refill its health
     * 3. Set its behaviour back to wandering
     * 4. Add the skeleton back to its spawn location
     * @param map the game map object:
     */
    @Override
    public void resetInstance(GameMap map) {
        map.removeActor(this);
        this.hitPoints = this.maxHitPoints;
        this.behaviour = new WanderBehaviour();
        map.addActor(this, this.getSpawnLocation());
    }

    /**
     * Set to true because skeleton will not need to be cleaned up during reset
     * @return always true
     */
    @Override
    public boolean isExist() {
        return true;
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Skeleton.skeletonSouls);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }

    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    @Override
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    @Override
    public String getWeaponName() {
        for (Item item : inventory) {
            if (item.asWeapon() != null) {
                return item.toString();
            }
        }
        return "Intrinsic Weapon";
    }
}
