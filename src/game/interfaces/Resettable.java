package game.interfaces;

import edu.monash.fit2099.engine.GameMap;
import game.ResetManager;

/**
 * Interface for game objects that should be reset
 */
public interface Resettable {
    /**
     * Allows any classes that use this interface to reset abilities, attributes, and items.
     * @param map the game map.
     */
    void resetInstance(GameMap map);

    /**
     * A useful method to clean up the list of instances in the ResetManager class
     * @return the existence of the instance in the game.
     * for example, true to keep it permanent, or false if instance needs to be removed from the reset list.
     */
    boolean isExist();

    /**
     * a default interface method that register current instance to the Singleton manager.
     */
    default void registerInstance(){
        ResetManager.getInstance().appendResetInstance(this);
    }
}
