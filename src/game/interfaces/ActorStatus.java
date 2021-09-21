package game.interfaces;


import game.exceptions.MissingWeaponException;

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
     * @throws MissingWeaponException if an actor should be holding a weapon and it's not
     */
    String getWeaponName() throws MissingWeaponException;
}
