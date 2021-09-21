package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.exceptions.MissingWeaponException;
import game.interfaces.ActorStatus;
import game.interfaces.Behaviour;
import game.interfaces.Provocative;
import game.interfaces.Soul;

public class Yhorm extends LordOfCinder implements Soul, Provocative, ActorStatus {
    private static final int YHORM_SOULS = 5000;

    private Behaviour behaviour;

    public Yhorm() {
        super("Yhorm the Giant", 'Y', 500);
        this.addCapability(Abilities.EMBER_FORM);
        this.behaviour = new DoNothingBehaviour();
        this.addItemToInventory(new GreatMachete());
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
            // TODO: confirm if Yhorm will just be stunned for one turn
            this.removeCapability(Status.STUNNED);
            return new DoNothingAction();
        }
        if (this.hitPoints < this.maxHitPoints / 2) {
            // trigger rage mode at half health
            for (Item item : this.inventory) {
                if(item.asWeapon() != null && item instanceof GreatMachete && this.hasCapability(Abilities.EMBER_FORM)) {
                    GreatMachete greatMachete = (GreatMachete) item;
                    greatMachete.rageMode(this);
                }
            }
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
     * @throws MissingWeaponException if great machete cannot be found in yhorm's inventory
     */
    @Override
    public String getWeaponName() throws MissingWeaponException {
        for (Item item : inventory) {
            if (item.asWeapon() != null && item instanceof GreatMachete) {
                return item.toString();
            }
        }
        throw new MissingWeaponException("Yhorm must hold a Great Machete");
    }
}
