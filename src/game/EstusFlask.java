package game;

import edu.monash.fit2099.engine.Item;
import game.enums.Abilities;
import game.interfaces.Consumable;
import game.interfaces.Resettable;

public class EstusFlask extends Item implements Resettable, Consumable{
    private double healingPercentage;
    private int charges;
    private int maxCharges;
    private int healing = 40;

    /***
     * Constructor.
     *
     */
    public EstusFlask() {
        super("Estus Flask", 'E', false);
        this.maxCharges = 3;
        this.charges = maxCharges;
        this.addCapability(Abilities.DRINK);
        this.allowableActions.add(new DrinkEstusFlaskAction(this));
    }

    @Override
    public int getCharges() {
        return charges;
    }

    @Override
    public int getMaxCharges() {
        return maxCharges;
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
    public boolean consumedBy(Player player) {
        if (player.hasCapability(Abilities.DRINK)){
            player.heal(healing);
            reduceCharges();
            return true;
        }
        return false;
    }

    @Override
    public void resetInstance() {

    }

    @Override
    public boolean isExist() {
        return false;
    }
}
