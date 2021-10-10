package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.*;

/**
 * Second boss in the game.
 */
public class Aldrich extends LordOfCinder implements ActorStatus, Aggressor, Resettable, Soul {
    public static final int MAX_HEALTH = 350;
    public static final int ALDRICH_SOULS = 5000;

    private Behaviour behaviour;
    private final Location spawnLocation;

    /**
     * Aldrich constructor
     * @param spawnLocation location Aldrich will be spawned in
     */
    public Aldrich(Location spawnLocation) {
        super("Alrdich the Devourer", 'A', Aldrich.MAX_HEALTH);
        this.addCapability(Abilities.EMBER_FORM);
        this.behaviour = new DoNothingBehaviour();
        this.addItemToInventory(new DarkmoonLongbow());
        this.spawnLocation = spawnLocation;
        this.registerInstance();
    }

    /**
     * Continually scan for actor who is adjacent. Then switch to aggro if there is an actor.
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return the action from Aldrich's behaviour.
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (behaviour instanceof DoNothingBehaviour) {
            Location currentLocation = map.locationOf(this);
            for (Exit exit : currentLocation.getExits()) {
                // scan exits
                Location location = exit.getDestination();
                if (location.containsAnActor()) {
                    Actor actor = map.getActorAt(location);
                    if (actor instanceof Player) {
                        // if player is adjacent to Yhorm, switch to aggro
                        this.switchAggroBehaviour(actor);
                        break;
                    }
                }
            }
        }
        return behaviour.getAction(this, map);
    }

    /**
     * @return Aldrich's HP
     */
    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * @return Aldrich's max HP
     */
    @Override
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * @return Name of Darkmoon Longbow
     */
    @Override
    public String getWeaponName() {
        DarkmoonLongbow darkmoonLongbow = this.getDarkmoonLongbow();
        assert darkmoonLongbow != null;
        return darkmoonLongbow.toString();
    }

    /**
     * @return Alrdich's bow. Else null
     */
    private DarkmoonLongbow getDarkmoonLongbow() {
        for (Item item : this.inventory) {
            if (item instanceof DarkmoonLongbow) {
                return (DarkmoonLongbow) item;
            }
        }
        return null;
    }

    /**
     * Change Aldrich's behaviour to aggressive
     * @param target actor who will be targeted with aggroBehaviour
     */
    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    /**
     * @return Aldrich's current behaviour
     */
    @Override
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Check if another location is within Aldrich's bow's range of attack.
     * @param targetLocation location that the target exists in
     * @param map the game map instance
     * @return true if within range else false
     */
    @Override
    public boolean isWithinRange(Location targetLocation, GameMap map) {
        DarkmoonLongbow darkmoonLongbow = this.getDarkmoonLongbow();
        assert darkmoonLongbow != null;
        return Utils.distance(map.locationOf(this), targetLocation) <= darkmoonLongbow.getRange();
    }

    /**
     * Allow an attack if other actor is hostile
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    @Override
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            actions.add(new AttackAction(this, direction));
        }
        return actions;
    }

    /**
     * Only reset Aldrich if he is alive. Place him back in his spawn location, reset HP and behaviour.
     * @param map the game map.
     */
    @Override
    public void resetInstance(GameMap map) {
        if (this.isConscious()) {
            map.removeActor(this);
            map.addActor(this, this.getSpawnLocation());
            this.behaviour = new DoNothingBehaviour();
            this.hitPoints = this.maxHitPoints;
        }
    }

    /**
     * Don't remove aldrich from resettable list after reset.
     * @return true
     */
    @Override
    public boolean isExist() {
        return true;
    }

    /**
     * Award souls to another object.
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Aldrich.ALDRICH_SOULS);
    }

    /**
     * @return get location Aldrich was spawned in.
     */
    public Location getSpawnLocation() {
        return this.spawnLocation;
    }
}
