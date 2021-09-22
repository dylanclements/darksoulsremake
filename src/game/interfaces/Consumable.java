package game.interfaces;

import edu.monash.fit2099.engine.Actor;

public interface Consumable {
	
	boolean reduceCharges();

	boolean consumedBy(Actor actor);

	int getCharges();

	int getMaxCharges();
}
