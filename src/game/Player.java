package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Soul;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Soul {

	private final Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Abilities.REST);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		displayStatus();

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 *  Displays the player's status. Hitpoints/MaxHitpoints, current weapon and souls
	 */
	private void displayStatus() {
		// TODO: print the player's weapon and the player's souls
		Display display = new Display();
		String s = String.format("Player (%d/%d), holding weapon, souls: X", this.hitPoints, this.maxHitPoints);
		display.println(s);
	}

	@Override
	public void transferSouls(Soul soulObject) {
		//TODO: transfer Player's souls to another Soul's instance.
	}
}
