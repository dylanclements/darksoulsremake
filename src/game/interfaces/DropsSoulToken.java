package game.interfaces;

import edu.monash.fit2099.engine.Location;

/**
 * Interface for actors that are capable of dropping one/more soul tokens.
 */
public interface DropsSoulToken {
    /**
     * Drop soul tokens at a location.
     * @param location location where the soul token is to be dropped (or where dropping algorithm is based off)
     */
    void placeSoulToken(Location location);
}
