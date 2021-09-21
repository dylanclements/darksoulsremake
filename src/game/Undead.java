package game;


import edu.monash.fit2099.engine.*;
import game.enums.Status;
import game.interfaces.*;

import java.util.Random;

/**
 * An undead minion.
 */
public class Undead extends Actor implements Resettable, Soul, Provocative, ActorStatus {
	// Will need to change this to a collection if Undeads gets additional Behaviours.
	private Behaviour behaviour = new WanderBehaviour();
	private final Random random = new Random();

	public static int undeadSouls = 50;

	/** 
	 * Constructor.
	 * All Undeads are represented by an 'u' and have 30 hit points.
	 * @param name the name of this Undead
	 */
	public Undead(String name) {
		super(name, 'u', 50);
		this.registerInstance();
	}

	/**
	 * At the moment, we only make it can be attacked by enemy that has HOSTILE capability
	 * You can do something else with this method.
	 * @param otherActor the Actor that might be performing attack
	 * @param direction  String representing the direction of the other Actor
	 * @param map        current GameMap
	 * @return list of actions
	 * @see Status#HOSTILE_TO_ENEMY
	 */
	@Override
	public Actions getAllowableActions(Actor otherActor, String direction, GameMap map) {
		Actions actions = new Actions();
		// it can be attacked only by the HOSTILE opponent, and this action will not attack the HOSTILE enemy back.
		if(otherActor.hasCapability(Status.HOSTILE_TO_ENEMY)) {
			actions.add(new AttackAction(this,direction));
		}
		return actions;
	}

	/**
	 * Figure out what to do next.
	 * @see edu.monash.fit2099.engine.Actor#playTurn(Actions, Action, GameMap, Display)
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// loop through all behaviours
		if (behaviour instanceof WanderBehaviour) {
			// 10% chance to die every turn if wandering
			float deathChance = random.nextFloat();
			if (deathChance < 0.10f) {
				return new DeathAction();
			}
		}
		return behaviour.getAction(this, map);
	}

	@Override
	public void resetInstance(GameMap map) {
		map.removeActor(this);
	}

	@Override
	public boolean isExist() {
		return false;
	}

	@Override
	public void transferSouls(Soul soulObject) {
		soulObject.addSouls(Undead.undeadSouls);
	}

	/**
	 * Get undead to change behaviour to Aggro.
	 * @param target the actor that the undead will attack
	 */
	@Override
	public void switchAggroBehaviour(Actor target) {
		this.behaviour = new AggroBehaviour(target);
	}

	@Override
	public Behaviour getBehaviour() {
		return this.behaviour;
	}

	@Override
	public int getHitPoints() {
		return this.hitPoints;
	}

	@Override
	public int getMaxHitPoints() {
		return this.maxHitPoints;
	}

	@Override
	public String getWeaponName() {
		return "Intrinsic Weapon";
	}
}
