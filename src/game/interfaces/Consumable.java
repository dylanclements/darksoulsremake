package game.interfaces;

import game.Player;

public interface Consumable {
	
	boolean reduceCharges();

	boolean consumedBy(Player player);

	int getCharges();

	int getMaxCharges();
}
