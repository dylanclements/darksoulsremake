package game.interfaces;

import edu.monash.fit2099.engine.Actor;

/**
 * Interface for managing wind slash action
 */
public interface IWindSlash {
    /**
     * Charge storm ruler
     * @param actor the actor who is performing the charge
     * @return true if the charge was successful else false
     */
    boolean charge(Actor actor);

    /**
     * @return the number of charges
     */
    int getCharges();

    /**
     * @return the maximum number of charges
     */
    int getMaxCharges();

    /**
     * Routine to prepare a weapon for wind slash
     * @param actor the actor performing the wind slash preparation
     */
    void prepareWindSlash(Actor actor);

    /**
     * Routine to finish a wind slash
     * @param actor the actor concluding the wind slash
     */
    void finishWindSlash(Actor actor);
}