package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * A class that represents the floor inside a building.
 */
public class Floor extends Ground {

	public Floor() {
		super('_');
	}

	/**
	 * Only let player enter floor
	 * @param actor the Actor to check
	 * @return true if actor is player else false
	 */
	@Override
	public boolean canActorEnter(Actor actor) {
		return actor instanceof Player;
	}
}
