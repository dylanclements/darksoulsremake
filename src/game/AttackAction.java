package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.interfaces.ActorStatus;
import game.interfaces.Aggressor;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;

	/**
	 * The direction of incoming attack.
	 */
	protected String direction;

	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * @param target actor who is getting attacked
	 * @param direction the direction this attack is occuring in.
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	/**
	 * Procedure for attacking another actor.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return message that describes the attack action
	 */
	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		if (this.target instanceof Aggressor && ((Aggressor) this.target).getBehaviour() instanceof WanderBehaviour) {
			// if target can be provoked, switch to aggro behaviour if current behaviour is not Aggro
			((Aggressor) this.target).switchAggroBehaviour(actor);
		}

		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";
		target.hurt(damage);

		if (!target.isConscious()) {
			// getting rid of crappy death procedure, and replacing with DeathAction
			DeathAction death = new DeathAction(actor);
			result += System.lineSeparator() + death.execute(target, map);
		}
		return result;
	}

	/**
	 * If target implements ActorStatus, report its health and weapon
	 * Else report a plain message
	 *
	 * @param actor The actor performing the action.
	 * @return Prompt to attack another actor.
	 */
	@Override
	public String menuDescription(Actor actor) {
		if (target instanceof ActorStatus) {
			int targetHitPoints = ((ActorStatus) target).getHitPoints();
			int targetMaxHitPoints = ((ActorStatus) target).getMaxHitPoints();
			String weaponName = ((ActorStatus) target).getWeaponName();
			return actor + " attacks " + target +
					String.format(" (%d/%d)", targetHitPoints, targetMaxHitPoints) + " who holds " + weaponName;
		}
		return actor + " attacks " + target + " at " + direction;
	}
}
