package game.interfaces;

import edu.monash.fit2099.engine.Actor;

/**
 * Interface for Actors that have the ability to be provoked by the Player, therefore switching behaviours
 */
public interface Provocative {
    /**
     * Method that will switch an actor's current behaviour to AggroBehaviour
     * @param target actor who will be targeted with aggroBehaviour
     */
    void switchAggroBehaviour(Actor target);

    /**
     * Grab the behaviour, default method.
     * @return null, but override in implementation
     */
    default Behaviour getBehaviour() {
        return null;
    }
}
