package game;


import edu.monash.fit2099.engine.Actor;

/**
 * The boss of Design o' Souls
 */
public abstract class LordOfCinder extends Actor {

    /**
     * Lord of Cinder constructor.
     * @param name name of the lord of cinder
     * @param displayChar how this lord of cinder is represented on the game map
     * @param hitPoints lord of cinder's health
     */
    public LordOfCinder(String name, char displayChar, int hitPoints) {
        super(name, displayChar, hitPoints);
    }

    // TODO: Cinders of a lord abstract method here (optional though)
}
