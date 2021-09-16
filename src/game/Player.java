package game;

import edu.monash.fit2099.engine.*;
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
		this.hitPoints = hitPoints;
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (map.locationOf(this).getGround() instanceof Valley) {
			return new DeathAction();
		} else {
			// Handle multi-turn Actions
			if (lastAction.getNextAction() != null)
				return lastAction.getNextAction();

			displayStatus(display);

			// return/print the console menu
			return menu.showMenu(this, actions, display);
		}
	}

	/**
	 *  Displays the player's status. Hitpoints/MaxHitpoints, current weapon and souls
	 */
	private void displayStatus(Display display) {
		// TODO: print the player's weapon and the player's souls
		String s = String.format("Player (%d/%d), holding weapon, souls: %d", this.hitPoints, this.maxHitPoints, this.souls);
		ResetManager resetter = ResetManager.getInstance();
		resetter.printResettable();
		display.println(s);
	}

	@Override
	public void transferSouls(Soul soulObject) {
		if (!isConscious()) {
			int playerSouls = getSouls();
			this.subtractSouls(playerSouls);
			soulObject.addSouls(playerSouls);
		}
	}

	/**
	 * Add a number of souls to Player
	 * @param souls number of souls to be incremented.
	 * @return true
	 */
	public boolean addSouls(int souls) {
		this.souls += souls;
		return true;
	}

	/**
	 * Decrement number of souls.
	 * @param souls number souls to be deducted
	 * @return true if souls have been decremented. false if souls exceeds player's souls
	 */
	public boolean subtractSouls(int souls) {
		if (souls > getSouls()) {
			return false;
		}
		this.souls -= souls;
		return true;
	}

	@Override
	public void resetInstance(GameMap map) {
		this.hitPoints = this.maxHitPoints;
		map.removeActor(this);
		map.at(38, 12).addActor(this);
	}

	@Override
	public boolean isExist() {
		return true;
	}

	public int getSouls() {
		return this.souls;
	}

	@Override
	public void heal(int percentageHealth) {
		int i = percentageHealth * maxHitPoints;
		super.heal(i);
	}

	/**
	 * If item picked up is SoulToken, add to player's souls. Else just add item to inventory as usual.
	 * @param item The Item to add.
	 */
	@Override
	public void addItemToInventory(Item item) {
		if (item instanceof SoulToken) {
			this.addSouls(((SoulToken) item).getSouls());
		}
		else {
			this.inventory.add(item);
		}
	}
}
