package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.*;

public class Chest extends Ground implements Soul, Resettable, Unboxing {
    private int souls;
    private final Ground oldGround;
    private final Location chestLocation;
    /**
     * Constructor. Construct with the old ground only.
     *
     * @param oldGround that the chest replaces.
     */
    public Chest(Ground oldGround, Location chestLocation) {
        super('?');
        this.souls = 100;
        this.oldGround = oldGround;
        this.chestLocation = chestLocation;
    }
    /**
     * Actors cannot enter/step on the location where the chest in placed.
     * @param actor the Actor to check
     * @return false
     */
    @Override
    public boolean canActorEnter(Actor actor) {
        return false;
    }

    /**
     * @return number of souls in the token
     */
    public int getSouls() {
        return this.souls;
    }

    /**
     * Transfer souls to another soul instance
     * @param soulObject a target souls.
     */
    @Override
    public void transferSouls(Soul soulObject) {
        int chestSouls = getSouls();
        this.subtractSouls(chestSouls);
        soulObject.addSouls(chestSouls);
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
     * Spawn 1-3 soul tokens at the location of the chest
     *
     */
    @Override
    public void spawnSoulToken() {
        Ground oldGround = chestLocation.getGround();
        SoulToken soulToken = new SoulToken(oldGround);
        this.transferSouls(soulToken);
        chestLocation.setGround(soulToken);
        //TODO: How to add 100 souls to the token? We can't just transfer because chest isn't an actor
        //TODO: How to add Multiple soul tokens?? Replace the tiles next to it?
    }

    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        //actions.add(new OpenChestAction());
        return actions;
    }

    /**
     * Spawn a Mimic and the location of the chest
     *
     */
    @Override
    public void spawnMimic() {
        this.chestLocation.setGround(this.oldGround);
        chestLocation.addActor(new Mimic());
    }

    @Override
    public void resetInstance(GameMap map) {

    }

    @Override
    public boolean isExist() {
        return false;
    }
}
