package game.interfaces;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

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
     * Find out whether the target is within the Aggressor's attack range
     * @param target the actor that the Aggressor may attack
     * @return true if the target is within the Aggressor's range else false
     */
    boolean isWithinRange(Actor target, GameMap map);
}
