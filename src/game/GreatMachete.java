package game;

import game.enums.Abilities;

public class GreatMachete extends MeleeWeapon {
    public static final int DAMAGE = 95;
    public static final int HIT_RATE = 60;
    public static final int HIT_RATE_BOOST = 30;

    /**
     * Constructor.
     *
     */
    public GreatMachete() {
        super("Yhorm’s Great Machete", 'G', GreatMachete.DAMAGE, "Wacks", GreatMachete.HIT_RATE);
    }

    protected void setHitRate(int hitRate) {
        this.hitRate = hitRate;
    }

    /**
     * For Yhorm's ember form, boost the Great Machete accuracy
     * @param yhorm lord of cinder
     */
    protected void rageMode(Yhorm yhorm) {
        if (yhorm.hasCapability(Abilities.EMBER_FORM)){
            this.setHitRate(this.hitRate + GreatMachete.HIT_RATE_BOOST);
        }
    }
}
