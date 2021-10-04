package game;

import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.Aggressor;
import game.interfaces.Behaviour;
import game.interfaces.Resettable;
import game.interfaces.Soul;

public class Mimic extends Actor implements Soul, Aggressor, Resettable {
    public static final int MIMIC_SOULS = 100;
    public static final int ATTACK_RANGE = 1;
    private Behaviour behaviour;

    //TODO: find a way to drop multiple soul tokens
    /**
     * Constructor.
     *
     */
    public Mimic() {
        super("Mimic", 'M', 100);
        this.behaviour = new WanderBehaviour();
    }

    /**
     * Return AttackAction if other actor is in vicinity
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
     * Mimic will wander around the map but switch to AggroBehaviour if the Player is within its vicinity.
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
     * Change mimic behaviour to aggressive. This will mean mimic will now follow and attack the target
     * @param target actor who will be targeted with aggroBehaviour
     */
    @Override
    public void switchAggroBehaviour(Actor target) {
        this.behaviour = new AggroBehaviour(target);
    }

    /**
     * @return Mimic's current behaviour
     */
    @Override
    public Behaviour getBehaviour() {
        return this.behaviour;
    }

    /**
     * Mimic can only attack adjacent actors
     * @param targetLocation location that the target exists in
     * @param map the game map instance
     * @return true if skeleton can attack else false
     */
    @Override
    public boolean isWithinRange(Location targetLocation, GameMap map) {
        return Utils.distance(map.locationOf(this), targetLocation) <= Mimic.ATTACK_RANGE;
    }

    /**
     * Transfer souls to another souls instance
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        soulObject.addSouls(Mimic.MIMIC_SOULS);
    }

    /**
     * Remove the Mimic from the map on reset
     * @param map the game map.
     */
    @Override
    public void resetInstance(GameMap map) {
        map.removeActor(this);
    }

    /**
     * Does not exist unless spawned therefore needs to be deleted with reset
     * @return false
     */
    @Override
    public boolean isExist() {
        return false;
    }
}
