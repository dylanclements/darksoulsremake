package game;

import edu.monash.fit2099.engine.*;
import game.enums.Abilities;
import game.enums.Status;
import game.exceptions.MissingWeaponException;
import game.interfaces.ActorStatus;
import game.interfaces.IWindSlash;

public class WindSlashAction extends AttackAction {
    public static final int REQUIRED_CHARGES = 3;

    private IWindSlash windSlashWeapon;

    /**
     * Constructor.
     *
     * @param attacker actor who is wind slashing
     * @param target actor who is getting wind slashed
     * @param direction which direction this wind slash is going
     * @throws MissingWeaponException if the attacker does not have weapon that can wind slash
     */
    public WindSlashAction(Actor attacker, Actor target, String direction) throws MissingWeaponException {
        super(target, direction);
        if (!(this.setWindSlashWeapon(attacker))) {
            throw new MissingWeaponException(attacker + " must be wielding StormRuler to Wind Slash");
        }
    }

    /**
     * See if Attacker is wielding a wind slash capable weapon.
     * @param attacker actor that may be holding a wind slash weapon
     * @return True if setting was successful else false;
     */
    private boolean setWindSlashWeapon(Actor attacker) {
        for (Item item : attacker.getInventory()) {
            if (item instanceof IWindSlash && ((IWindSlash) item).getCharges() == WindSlashAction.REQUIRED_CHARGES) {
                // Wind Slash only valid when item is capable of it + item has required charges
                this.windSlashWeapon = (IWindSlash) item;
                return true;
            }
        }
        return false;
    }

    /**
     * 1. Prepare weapon for wind slash
     * 2. Execute the attack
     * 3. Stun the target
     * 4. Debuff the weapon
     * @param actor The actor performing the action.
     * @param map The map the actor is on.
     * @return description of the attack
     */
    @Override
    public String execute(Actor actor, GameMap map) {
        if (actor.hasCapability(Abilities.WIND_SLASH)) {
            this.windSlashWeapon.prepareWindSlash(actor);
            String attackMessage = super.execute(actor, map);
            this.target.addCapability(Status.STUNNED);
            this.windSlashWeapon.finishWindSlash(actor);
            return attackMessage;
        }
        return actor + " is not able to perform wind slash";
    }

    /**
     * Gets the message to prompt the player to execute a wind slash
     * @param actor The actor performing the action.
     * @return player prompt to wind slash an actor
     */
    @Override
    public String menuDescription(Actor actor) {
        if (target instanceof ActorStatus) {
            int targetHitPoints = ((ActorStatus) target).getHitPoints();
            int targetMaxHitPoints = ((ActorStatus) target).getMaxHitPoints();
            String weaponName = ((ActorStatus) target).getWeaponName();
            return actor + " wind slashes " + target +
                    String.format(" (%d/%d)", targetHitPoints, targetMaxHitPoints) + " who holds " + weaponName;
        }
        return super.menuDescription(actor);
    }
}
