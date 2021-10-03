package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.exceptions.MissingWeaponException;
import game.interfaces.*;

/**
 * Lord of cinder. Weak to storm ruler.
 */
public class Yhorm extends LordOfCinder implements Soul, Aggressor, ActorStatus, Resettable, EmberForm {
    public static final int YHORM_SOULS = 5000;
    public static final int ATTACK_RANGE = 1;

    private Behaviour behaviour;
    private final Location spawnLocation;

    /**
     * Constructor
     * @param spawnLocation location that Yhorm spawned in, for resetting
     */
    public Yhorm(Location spawnLocation) {
        super("Yhorm the Giant", 'Y', 500);
        this.addCapability(Abilities.EMBER_FORM);
        this.behaviour = new DoNothingBehaviour();
        this.addItemToInventory(new GreatMachete());
        this.spawnLocation = spawnLocation;
        this.registerInstance();
    }

    /**
     * Yhorm will do nothing until the player is within Yhorm's radius. Then Yhorm will switch to aggro
     * @param actions    collection of possible Actions for this Actor
     * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
     * @param map        the map containing the Actor
     * @param display    the I/O object to which messages may be written
     * @return an action from yhorm's behaviour
     */
    @Override
    public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
        if (this.hasCapability(Status.STUNNED)) {
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }
        if (!(this.hasCapability(Status.EMBER_FORM)) && this.hitPoints < this.maxHitPoints / 2) {
            // if Yhorm is not ember form and is below half health, trigger ember form
            this.emberForm(display);
        }
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
     * Change behaviour to aggressive
     * @param target the actor that yhorm will attack
     */
    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    /**
     * @return yhorm's current behaviour
     */
    @Override
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Yhorm can only attack target in adjacent locations
     * @param targetLocation location that the target exists in
     * @param map the game map instance
     * @return true if yhorm can attack else false
     */
    @Override
    public boolean isWithinRange(Location targetLocation, GameMap map) {
        return Utils.distance(map.locationOf(this), targetLocation) <= Yhorm.ATTACK_RANGE;
    }

    /**
     * Allow AttackAction if other actor is in vicinity
     * Allow WindSlashAction if other actor has the ability to do so
     * @param otherActor the Actor that might be performing attack
     * @param direction  String representing the direction of the other Actor
     * @param map        current GameMap
     * @return list of actions
     */
    public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
        Actions actions = new Actions();
        if (otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
            if (otherActor.hasCapability(Abilities.WIND_SLASH)) {
                try {
                    actions.add(new WindSlashAction(otherActor, this, direction));
                } catch (MissingWeaponException e) {
                    // Wind slash capability shouldn't be here, remove it
                    otherActor.removeCapability(Abilities.WIND_SLASH);
                }
            } else {
                actions.add(new AttackAction(this, direction));
            }
        }
        return actions;
    }

    /**
     * Transfer souls to another souls instance
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Yhorm.YHORM_SOULS);
    }

    /**
     * Grab Yhorm's hitpoints
     * @return integer that describes yhorm's hitpoints
     */
    @Override
    public int getHitPoints() {
        return this.hitPoints;
    }

    /**
     * Grab Yhorm's max hitpoints
     * @return integer that describes yhorm's max hitpoints
     */
    @Override
    public int getMaxHitPoints() {
        return this.maxHitPoints;
    }

    /**
     * Get the great machete's name attribute
     * @return string describing yhorm's great machete
     */
    @Override
    public String getWeaponName() {
        GreatMachete greatMachete = this.getGreatMachete();
        assert greatMachete != null;
        return greatMachete.toString();
    }

    /**
     *
     * @return yhorm's great machete
     */
    private GreatMachete getGreatMachete() {
        for (Item item : this.inventory) {
            if (item instanceof GreatMachete) {
                return (GreatMachete) item;
            }
        }
        return null;
    }

    /**
     * To reset Yhorm if he has not been defeated.
     * 1. Set behaviour back to DoNothingBehaviour
     * 2. Heal back to full health
     * 3. Set great machete hit rate back to default
     * 4. Remove ember form if it applies
     * @param map game map
     */
    @Override
    public void resetInstance(GameMap map) {
        if (this.isConscious()) {
            map.removeActor(this);
            map.addActor(this, this.getSpawnLocation());
            this.behaviour = new DoNothingBehaviour();
            this.hitPoints = this.maxHitPoints;
            GreatMachete greatMachete = this.getGreatMachete();
            assert greatMachete != null;
            greatMachete.setHitRate(GreatMachete.HIT_RATE);
            if (this.hasCapability(Status.EMBER_FORM)) {
                this.removeCapability(Status.EMBER_FORM);
            }
        }
    }

    /**
     * Return yhorm's spawn location for resetting
     * @return location
     */
    private Location getSpawnLocation() {
        return this.spawnLocation;
    }

    /**
     * Yhorm is not permanent.
     * @return false
     */
    @Override
    public boolean isExist() {
        return false;
    }

    /**
     * Yhorm's second phase. Boost weapon accuracy
     * @param display object to display things to the console
     */
    @Override
    public void emberForm(Display display) {
        if (this.hasCapability(Abilities.EMBER_FORM)) {
            this.addCapability(Status.EMBER_FORM);
            GreatMachete greatMachete = this.getGreatMachete();
            assert greatMachete != null;
            greatMachete.rageMode(this);
            display.println("Raargh....");
        } else {
            display.println("Yhorm is not capable of ember form at the moment");
        }
    }
}
