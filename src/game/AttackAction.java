package game;

import java.util.Random;

import edu.monash.fit2099.engine.*;
import game.exceptions.MissingWeaponException;
import game.interfaces.ActorStatus;
import game.interfaces.Provocative;

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
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target, String direction) {
		this.target = target;
		this.direction = direction;
	}

	@Override
	public String execute(Actor actor, GameMap map) {

		Weapon weapon = actor.getWeapon();

		if (!(rand.nextInt(100) <= weapon.chanceToHit())) {
			return actor + " misses " + target + ".";
		}

		if (this.target instanceof Provocative && ((Provocative) this.target).getBehaviour() instanceof WanderBehaviour) {
			// if target can be provoked, switch to aggro behaviour if current behaviour is not Aggro
			((Provocative) this.target).switchAggroBehaviour(actor);
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

	@Override
	public String menuDescription(Actor actor) {
		if (target instanceof ActorStatus) {
			int targetHitPoints = ((ActorStatus) target).getHitPoints();
			int targetMaxHitPoints = ((ActorStatus) target).getMaxHitPoints();
			String weaponName;
			try {
				weaponName = ((ActorStatus) target).getWeaponName();
			} catch (MissingWeaponException e) {
				weaponName = "Error";
			}
			return actor + " attacks " + target +
					String.format(" (%d/%d)", targetHitPoints, targetMaxHitPoints) + " who holds " + weaponName;
		}
		return actor + " attacks " + target + " at " + direction;
	}
}
