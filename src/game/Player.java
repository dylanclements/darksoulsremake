package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Menu;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.Resettable;
import game.interfaces.Soul;

/**
 * Class representing the Player.
 */
public class Player extends Actor implements Soul, Resettable {

	private final Menu menu = new Menu();

	private int souls;

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
		// Register player instance on instantiation
		this.registerInstance();
		this.souls = 0;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (map.locationOf(this).getGround() instanceof Valley) {
			display.println("YOU DIED");
			// TODO: Implement player death here!
		}

		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();

		displayStatus(display);

		// return/print the console menu
		return menu.showMenu(this, actions, display);
	}

	/**
	 *  Displays the player's status. Hitpoints/MaxHitpoints, current weapon and souls
	 */
	private void displayStatus(Display display) {
		// TODO: print the player's weapon and the player's souls
		String s = String.format("Player (%d/%d), holding weapon, souls: %d", this.hitPoints, this.maxHitPoints, this.souls);
		display.println(s);
	}

	@Override
	public void transferSouls(Soul soulObject) {
		// TODO: transfer Player's souls to another Soul's instance.
		// TODO: Transfer souls to SoulToken on death
	}

	@Override
	public void resetInstance(GameMap map) {
		// TODO: Refill estus flask on reset
		// TODO: Move player to firelink shrine on reset
		this.hitPoints = this.maxHitPoints;
		map.removeActor(this);
		map.at(38, 12).addActor(this);
	}

	@Override
	public boolean isExist() {
		return true;
	}

	public int getSouls() {
		return souls;
	}
}
