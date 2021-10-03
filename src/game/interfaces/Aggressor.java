package game.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Interface for Actors that have the ability to become aggressive
 */
public interface Aggressor {
    /**
     * Method that will switch an actor's current behaviour to AggroBehaviour
     * @param target actor who will be targeted with aggroBehaviour
     */
    void switchAggroBehaviour(Actor target);

    /**
     * Grab the behaviour, default method.
     * @return null, but override in implementation
     */
    Behaviour getBehaviour();

    /**
     * Find out whether the target is within the Aggressor's location
     * @param targetLocation location that the target exists in
     * @param map the game map instance
     * @return true if the target is within the aggressor's location else false
     */
    boolean isWithinRange(Location targetLocation, GameMap map);
}
