package game.interfaces;


/**
 * Interface for actors that should be reporting their health and their weapons
 */
public interface ActorStatus {
    /**
     * Grab the actor's hitpoints
     * @return integer that represents the actors hitpoints
     */
    int getHitPoints();

    /**
     * Grab the actor's maximum hitpoints
     * @return integer the represents the actors maximum hitpoints
     */
    int getMaxHitPoints();

    /**
     * Grab the actor's weapon name
     * @return name that describes the weapon
     */
    String getWeaponName();
}
