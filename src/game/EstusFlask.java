package game;

import edu.monash.fit2099.engine.Item;
import game.enums.Abilities;
import game.interfaces.Consumable;
import game.interfaces.Resettable;

public class EstusFlask extends Item implements Resettable, Consumable{
    private double healingPercentage;
    private int charges;
    private int MaxCharges;
    private int healing = 40;

    /***
     * Constructor.
     *
     */
    public EstusFlask() {
        super("Estus Flask", 'E', false);
        this.MaxCharges = 3;
        this.charges = MaxCharges;
        this.addCapability(Abilities.DRINK);
        this.allowableActions.add(new DrinkEstusFlaskAction(this));
    }

    public int getCharges() {
        return charges;
    }

    @Override
    public void reduceCharges() {
        //add checks if drops below 0, feedback to the user
        if (charges > 0){
            charges -= 1;
        }
    }

    @Override
    public void consumedBy(Player player) {
        if (player.hasCapability(Abilities.DRINK)){
            player.heal(healing);
            reduceCharges();
        }
    }

    @Override
    public void resetInstance() {

    }

    @Override
    public boolean isExist() {
        return false;
    }
}
