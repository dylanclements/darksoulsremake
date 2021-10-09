package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.interfaces.*;

public class Aldrich extends LordOfCinder implements ActorStatus, Aggressor, Resettable, Soul {
    public static final int ATTACK_RANGE = 3;
    public static final int MAX_HEALTH = 350;
    public static final int ALDRICH_SOULS = 5000;

    private Behaviour behaviour;
    private final Location spawnLocation;

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
        DarkmoonLongbow darkmoonLongbow = this.getDarkmoonLongbow();
        assert darkmoonLongbow != null;
        return darkmoonLongbow.toString();
    }

    private DarkmoonLongbow getDarkmoonLongbow() {
        for (Item item : this.inventory) {
            if (item instanceof DarkmoonLongbow) {
                return (DarkmoonLongbow) item;
            }
        }
        return null;
    }

    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    @Override
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    @Override
    public boolean isWithinRange(Location targetLocation, GameMap map) {
        DarkmoonLongbow darkmoonLongbow = this.getDarkmoonLongbow();
        assert darkmoonLongbow != null;
        return Utils.distance(map.locationOf(this), targetLocation) <= darkmoonLongbow.getRange();
    }

    @Override
    public void resetInstance(GameMap map) {
        if (this.isConscious()) {
            map.removeActor(this);
            map.addActor(this, this.getSpawnLocation());
            this.behaviour = new DoNothingBehaviour();
            this.hitPoints = this.maxHitPoints;
        }
    }

    @Override
    public boolean isExist() {
        return false;
    }

    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Aldrich.ALDRICH_SOULS);
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
