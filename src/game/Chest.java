package game;

import edu.monash.fit2099.engine.*;
import game.interfaces.*;

import java.util.LinkedList;
import java.util.Random;

public class Chest extends Ground implements Soul, Resettable, Unboxing, DropsSoulToken {
    public static final int CHEST_SOULS = 100;
    public static final int MIN_SOUL_TOKENS = 1;
    public static final int MAX_SOUL_TOKENS = 3;

    private int souls;
    private final Ground oldGround;
    private final Location chestLocation;

    private final Random r = new Random();

    /**
     * Constructor. Construct with the old ground only.
     * @param oldGround that the chest replaces.
     */
    public Chest(Ground oldGround, Location chestLocation) {
        super('?');
        this.souls = Chest.CHEST_SOULS;
        this.oldGround = oldGround;
        this.chestLocation = chestLocation;
        this.registerInstance();
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
        // TODO: How to add 100 souls to the token? We can't just transfer because chest isn't an actor
        // TODO: How to add Multiple soul tokens?? Replace the tiles next to it?
        // ^^^ The chest now implements DropsSoulToken. See below.
        this.placeSoulToken(chestLocation);
    }

    /**
     * Chest can place 1-3 soul tokens if it is opened (50% chance).
     * @param location location where the soul token is to be dropped (or where dropping algorithm is based off)
     */
    @Override
    public void placeSoulToken(Location location) {
        // get a random number of soul tokens to drop between min/max range. start a counter for the tokens dropped.
        int numberOfSoulTokens = Chest.MIN_SOUL_TOKENS + r.nextInt(Chest.MAX_SOUL_TOKENS + 1 - Chest.MIN_SOUL_TOKENS);
        int droppedTokens = 0;

        // define a queue, this queue will store locations. add the initial location
        LinkedList<Location> queue = new LinkedList<>();
        queue.add(location);

        // keep going until queue is empty OR we have dropped the number of soul tokens required
        while (queue.size() != 0 && droppedTokens != numberOfSoulTokens) {
            // this should cause error only when location input is null, but it's here if anything else happens.
            Location currentLocation = queue.poll();
            assert currentLocation != null;
            Ground currentGround = currentLocation.getGround();
            if (currentGround instanceof Chest) {
                currentGround = ((Chest) currentGround).oldGround;
            }

            // Create a soul token and drop it, then increment the counter
            SoulToken soulToken = new SoulToken(currentGround, Chest.CHEST_SOULS);
            currentLocation.setGround(soulToken);
            droppedTokens++;

            // Scan for viable exits (those that will not block thrown objects). add these exits to the queue.
            for (Exit exit : currentLocation.getExits()) {
                Location exitLocation = exit.getDestination();
                if (!exitLocation.getGround().blocksThrownObjects()) {
                    queue.add(exitLocation);
                }
            }
        }
    }

    /**
     * Allow OpenChestAction when an actor is adjacent to Chest.
     * @param actor the Actor acting
     * @param location the current Location
     * @param direction the direction of the Ground from the Actor
     * @return actions list containing OpenChestAction
     */
    @Override
    public Actions allowableActions(Actor actor, Location location, String direction) {
        Actions actions = new Actions();
        actions.add(new OpenChestAction(this));
        return actions;
    }

    /**
     * Spawn a Mimic and the location of the chest
     */
    @Override
    public void spawnMimic() {
        this.chestLocation.setGround(this.oldGround);
        chestLocation.addActor(new Mimic());
    }

    @Override
    public void resetInstance(GameMap map) {
        this.chestLocation.setGround(this);
    }

    /**
     * Chest will always exist in the game.
     * @return true
     */
    @Override
    public boolean isExist() {
        return true;
    }
}
