package game.interfaces;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import game.Player;

public interface Consumable {
	
	void reduceCharges();
	//consumed by /actor
	void consumedBy(Player player);
}
