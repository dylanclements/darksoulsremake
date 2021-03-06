package game;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Ground;

/**
 * The gorge or endless gap that is dangerous for the Player.
 */
public class Valley extends Ground {

	public Valley() {
		super('+');
	}

	/**
	 * Player can enter Valley, other Actors cannot
	 * @param actor the Actor to check
	 * @return false or actor cannot enter.
	 */
	@Override
	public boolean canActorEnter(Actor actor){
		return actor instanceof Player;
	}

	/**
	 * Items should not be thrown on a valley.
	 * @return true
	 */
	@Override
	public boolean blocksThrownObjects() {
		return true;
	}
}
