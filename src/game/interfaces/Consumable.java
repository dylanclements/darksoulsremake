package game.interfaces;

import edu.monash.fit2099.engine.Actor;

/**
 * Interface for items which it's uses are limited by consumable charges.
 */
public interface Consumable {

	/**
	 * Decrements the charges on the item
	 * @return true if successful else false
	 */
	boolean reduceCharges();

	/**
	 * The actor consumes the item
	 * @param actor actor who is consuming something
	 * @return true if successful else false
	 */
	boolean consumedBy(Actor actor);

	/**
	 * Get the number of charges
	 * @return number of charges
	 */
	int getCharges();

	/**
	 * Get the capacity of charges
	 * @return maximum number of charges
	 */
	int getMaxCharges();
}
