package game.interfaces;

import edu.monash.fit2099.engine.Location;

/**
 * Interface for actors that contain location attributes used to track them.
 */
public interface LocationTracker {
    /**
     * Get this actor's last recorded location
     * @return a location
     */
    Location getCurrentLocation();

    /**
     * Get the location recorded before the last recorded location
     * @return a location
     */
    Location getPreviousLocation();
}
