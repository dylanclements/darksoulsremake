package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.*;

/**
 * Tougher enemy than undead.
 */
public class Skeleton extends Actor implements Soul, Aggressor, Resettable, ActorStatus {
    public static final int SKELETON_SOULS = 250;
    public static final int ATTACK_RANGE = 1;

    private Behaviour behaviour;
    private final Location spawnLocation;

    /**
     * Constructor.
     * @param name name of this skeleton instance
     * @param spawnLocation location which this skeleton is to be spawned on or reset to
     */
    public Skeleton(String name, Location spawnLocation) {
        super(name, 's', 100);
        this.spawnLocation = spawnLocation;
        this.behaviour = new WanderBehaviour();
        this.addItemToInventory(new BroadSword());
        this.registerInstance();
    }

    /**
     * Return AttackAction if other actor is in vicinity
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Skeleton will wander around the map but switch to AggroBehaviour if the Player is within its vicinity.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return action that the skeleton will undertake
     */
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

    /**
     * Change skeleton behaviour to aggressive. This will mean the skeleton will now follow and attack the target
     * @param target actor who will be targeted with aggroBehaviour
     */
    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    /**
     * @return Skeleton's current behaviour
     */
    @Override
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Skeleton can only attack adjacent actors
     * @param targetLocation location that the target exists in
     * @param map the game map instance
     * @return true if skeleton can attack else false
     */
    @Override
    public boolean isWithinRange(Location targetLocation, GameMap map) {
        return Utils.distance(map.locationOf(this), targetLocation) <= Skeleton.ATTACK_RANGE;
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

    /**
     * Transfer souls to another souls instance
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Skeleton.SKELETON_SOULS);
    }

    /**
     * Skeleton needs to know its spawn location at all times for reset
     * @return the skeleton's spawn location
     */
    public Location getSpawnLocation() {
        return spawnLocation;
    }

    /**
     * @return the skeleton's HP
     */
    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * @return the skeleton's max HP
     */
    @Override
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * @return the name of the skeleton's weapon
     */
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
