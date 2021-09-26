package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.interfaces.ActorStatus;
import game.interfaces.Resettable;
import game.interfaces.Soul;


/**
 * Class representing the Player.
 */
public class Player extends Actor implements Soul, Resettable, ActorStatus {
	private final Menu menu = new Menu();
	private Location previousLocation;
	private Location currentLocation;
	private int souls;

	/**
	 * Constructor.
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
		this.addCapability(Status.HOSTILE_TO_ENEMY);
		this.addCapability(Abilities.REST);
		this.addItemToInventory(new BroadSword());
		this.addItemToInventory(new EstusFlask());
		this.registerInstance();
		this.souls = 0;
		this.hitPoints = hitPoints;
		this.maxHitPoints = hitPoints;
	}

	/**
	 * Remove the attack actions from player during playTurn
	 * @param actions list of actions
	 */
	private Actions removeAttackActions(Actions actions) {
		Actions keepActions = new Actions();
		for (Action action : actions) {
			if (!(action instanceof AttackAction)) {
				keepActions.add(action);
			}
		}
		return keepActions;
	}

	/**
	 * What player must do during playTurn:
	 * 1. Update currentLocation and previousLocation if the player has moved
	 * 2. Remove attack actions if the player has their attack disabled.
	 * 3. Die if located in a valley
	 * 4. Execute the last action if an action is multi-turn
	 * 5. Display status
	 * 6. If nothing else, prompt the user with an option to choose an action
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the action that the player takes
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		if (map.locationOf(this) != this.previousLocation) {
			// player has moved. update previous location and then current location
			this.previousLocation = this.currentLocation;
			this.currentLocation = map.locationOf(this);
		}

		if (this.hasCapability(Status.ATTACK_DISABLED)) {
			actions = this.removeAttackActions(actions);
		}

		// die if player is in a valley
		if (map.locationOf(this).getGround() instanceof Valley) {
			return new DeathAction();
		} else {
			// Handle multi-turn Actions
			if (lastAction.getNextAction() != null)
				return lastAction.getNextAction();

			this.displayStatus(display);

			// return/print the console menu
			return menu.showMenu(this, actions, display);
		}
	}

	/**
	 *  Displays the player's status. Hitpoints/MaxHitpoints, current weapon and souls
	 */
	private void displayStatus(Display display) {
		// TODO: print the player's weapon and the player's souls
		String s = String.format("Player (%d/%d), holding %s, souls: %d",
				this.getHitPoints(), this.getMaxHitPoints(), this.getWeaponName(), this.getSouls());
		display.println(s);
	}

	/**
	 * Transfer souls to a soullable object
	 * @param soulObject a target souls.
	 */
	@Override
	public void transferSouls(Soul soulObject) {
		int playerSouls = getSouls();
		this.subtractSouls(playerSouls);
		soulObject.addSouls(playerSouls);
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

	/**
	 * Player must return to max health on reset and returned to firelink shrine
	 * @param map instance of the game's map
	 */
	@Override
	public void resetInstance(GameMap map) {
		this.hitPoints = this.maxHitPoints;
		map.removeActor(this);
		map.at(38, 12).addActor(this);
	}

	/**
	 * @return will always be true, player will never be removed from the game
	 */
	@Override
	public boolean isExist() {
		return true;
	}

	/**
	 * @return number of souls the player has
	 */
	public int getSouls() {
		return this.souls;
	}

	/**
	 * @return the location the player was on the last turn
	 */
	public Location getPreviousLocation() {
		return this.previousLocation;
	}

	/**
	 * @return player's hit points
	 */
	@Override
	public int getHitPoints() {
		return this.hitPoints;
	}

	/**
	 * @return player's max hit points
	 */
	@Override
	public int getMaxHitPoints() {
		return this.maxHitPoints;
	}

	/**
	 * @return name of the weapon the player is holding
	 */
	@Override
	public String getWeaponName() {
		for (Item item : inventory) {
			if (item.asWeapon() != null) {
				return item.toString();
			}
		}
		return "Intrinsic Weapon";
	}
}
