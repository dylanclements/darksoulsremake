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
     * Constructor.
     *
     * @param name        name of the item
     * @param displayChar character to use for display when item is on the ground
     * @param damage      amount of damage this weapon does
     * @param verb        verb to use for this weapon, e.g. "hits", "zaps"
     * @param hitRate     the probability/chance to hit the target.
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

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
