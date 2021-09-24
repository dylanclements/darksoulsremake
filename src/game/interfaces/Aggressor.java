package game.interfaces;

import edu.monash.fit2099.engine.Actor;

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
}
