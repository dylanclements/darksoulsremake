package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.DropItemAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * A weapon that can attack from more than 1 square away
 */
public abstract class RangedWeapon extends WeaponItem {
    private int range;

    /**
     * Constructor
     * @param name name of the weapon
     * @param displayChar way to display the weapon on the ground
     * @param damage damage the weapon can do
     * @param verb describes the weapon's attacks
     * @param hitRate % chance to deal damage
     * @param range number of squares away the weapon can attack from
     */
    public RangedWeapon(String name, char displayChar, int damage, String verb, int hitRate, int range) {
        super(name, displayChar, damage, verb, hitRate);
        this.range = range;
    }

    /**
     * Disable dropping, always return null
     * @param actor an actor that will interact with this item
     * @return null
     */
    @Override
    public DropItemAction getDropAction(Actor actor) {
        return null;
    }

    /**
     * Instead of being able to pick up a weapon, only enable to swap current weapon for it
     * @param actor an actor that will interact with this item
     * @return SwapWeaponAction instance
     */
    @Override
    public PickUpItemAction getPickUpAction(Actor actor) {
        return new SwapWeaponAction(this);
    }

    /**
     * @return the weapon's range
     */
    public int getRange() {
        return range;
    }

    /**
     * Chance the weapon's range
     * @param range new range
     */
    public void setRange(int range) {
        this.range = range;
    }
}
