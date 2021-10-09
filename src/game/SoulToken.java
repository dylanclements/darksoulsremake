package game;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;
import game.interfaces.Soul;

/**
 * Token that gets dropped on the ground when the Player dies
 */
public class SoulToken extends Ground implements Soul {
    private int souls;
    private final Ground oldGround;

    /**
     * Constructor. Construct with the old ground only.
     * @param oldGround ground that the SoulToken replaces.
     */
    public SoulToken(Ground oldGround) {
        super('$');
        this.souls = 0;
        this.oldGround = oldGround;
    }

    /**
     * Constructor. Construct with the old ground and a number of souls.
     * @param oldGround ground that the SoulToken replaces.
     * @param souls integer that represents the amount of souls the token will contain.
     */
    public SoulToken(Ground oldGround, int souls) {
        super('$');
        this.souls = souls;
        this.oldGround = oldGround;
    }

    /**
     * @return number of souls in the token
     */
    public int getSouls() {
        return this.souls;
    }

    /**
     * Always return an instance of PickUpSoulsAction
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return actions list containing PickUpSoulsAction
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new PickUpSoulsAction(location, this.oldGround));
        return actions;
    }

    /**
     * Transfer souls to another soul instance
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        int tokenSouls = getSouls();
        this.subtractSouls(tokenSouls);
        soulObject.addSouls(tokenSouls);
    }

    /**
     * Subtract souls from this souls instance
     * @param souls number souls to be deducted
     * @return true if the operation was successful else false
     */
    @Override
    public boolean subtractSouls(int souls) {
        if (souls > this.getSouls()) {
            return false;
        }
        this.souls -= souls;
        return true;
    }

    /**
     * Add souls to this souls instance
     * @param souls number of souls to be incremented.
     * @return true if the operation was successful else false
     */
    @Override
    public boolean addSouls(int souls) {
        this.souls += souls;
        return true;
    }

    /**
     * SoulTokens should block thrown objects
     * @return true
     */
    @Override
    public boolean blocksThrownObjects() {
        return true;
    }
}
