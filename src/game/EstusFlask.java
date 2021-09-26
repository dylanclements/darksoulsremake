package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.enums.Abilities;
import game.interfaces.ActorStatus;
import game.interfaces.Consumable;
import game.interfaces.Resettable;


/**
 * Item that allows the player to refill health. Has a finite number of uses.
 */
public class EstusFlask extends Item implements Resettable, Consumable {
    private static final int DEFAULT_MAX_CHARGES = 3;
    private static final float DEFAULT_HEAL_PERCENT = 0.4f;

    private float healingPercentage;
    private int charges;
    private int maxCharges;

    /**
     * Constructor.
     */
    public EstusFlask() {
        super("Estus Flask", 'E', false);

        this.charges = EstusFlask.DEFAULT_MAX_CHARGES;
        this.maxCharges = EstusFlask.DEFAULT_MAX_CHARGES;
        this.healingPercentage = EstusFlask.DEFAULT_HEAL_PERCENT;

        this.addCapability(Abilities.DRINK);
        this.allowableActions.add(new DrinkEstusFlaskAction(this));
        this.registerInstance();
    }

    /**
     * @return current number of charges
     */
    @Override
    public int getCharges() {
        return this.charges;
    }

    /**
     * @return max charges
     */
    @Override
    public int getMaxCharges() {
        return this.maxCharges;
    }

    /**
     * @return healing percentage
     */
    public float getHealingPercentage() {
        return this.healingPercentage;
    }

    /**
     * Change the charges.
     * @param charges the new charges
     */
    private void setCharges(int charges) {
        this.charges = charges;
    }

    /**
     * Change the maximum charges.
     * @param maxCharges new max charges
     */
    private void setMaxCharges(int maxCharges) {
        this.maxCharges = maxCharges;
    }

    /**
     * Change the healing percentage.
     * @param healingPercentage new healing percentage
     */
    private void setHealingPercentage(float healingPercentage) {
        this.healingPercentage = healingPercentage;
    }

    /**
     * Decrement the charges
     * @return true if successful else false
     */
    @Override
    public boolean reduceCharges() {
        //add checks if drops below 0, feedback to the user
        if (charges > 0){
            this.setCharges(this.charges - 1);
            return true;
        }
        return false;
    }

    /**
     * Consume the estus flask.
     * @param actor actor who is consuming the estus flask
     * @return true if successful else false
     */
    @Override
    public boolean consumedBy(Actor actor) {
        if (actor.hasCapability(Abilities.DRINK) && this.reduceCharges()){
            ActorStatus actorStatusObject = (ActorStatus) actor;
            int actorMaxHitpoints = actorStatusObject.getMaxHitPoints();
            int healAmount = Math.round((float) actorMaxHitpoints * this.getHealingPercentage());
            actor.heal(healAmount);
            return true;
        }
        return false;
    }

    /**
     * Reset the estus flask on soft-reset. Just need to refill charges.
     * @param map the game map
     */
    @Override
    public void resetInstance(GameMap map) {
        this.charges = maxCharges;
    }

    /**
     * Estus flask always exists in the game.
     * @return true
     */
    @Override
    public boolean isExist() {
        return true;
    }
}
