package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import game.enums.Abilities;
import game.interfaces.ActorStatus;
import game.interfaces.Consumable;
import game.interfaces.Resettable;

public class EstusFlask extends Item implements Resettable, Consumable{
    private float healingPercentage;
    private int charges;
    private int maxCharges;

    /***
     * Constructor.
     *
     */
    public EstusFlask() {
        super("Estus Flask", 'E', false);

        this.charges = 3;
        this.maxCharges = 3;
        this.healingPercentage = 0.4f;

        this.addCapability(Abilities.DRINK);
        this.allowableActions.add(new DrinkEstusFlaskAction(this));
        this.registerInstance();
    }

    @Override
    public int getCharges() {
        return charges;
    }

    @Override
    public int getMaxCharges() {
        return maxCharges;
    }

    public float getHealingPercentage() {
        return this.healingPercentage;
    }

    @Override
    public boolean reduceCharges() {
        //add checks if drops below 0, feedback to the user
        if (charges > 0){
            charges -= 1;
            return true;
        }
        return false;
    }

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

    @Override
    public void resetInstance(GameMap map) {
        this.charges = maxCharges;
    }

    @Override
    public boolean isExist() {
        return true;
    }
}
