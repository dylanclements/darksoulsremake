package game;

import java.util.Random;

public class DarkmoonLongbow extends RangedWeapon {
    public static final int DAMAGE = 70;
    public static final int HIT_RATE = 80;
    public static final float CRITICAL_CHANCE = 0.15f;
    public static final int CRITICAL_BOOST = 2;
    public static final int RANGE = 3;

    private final Random r = new Random();

    /**
     * Constructor.
     */
    public DarkmoonLongbow() {
        super("Darkmoon Longbow", 'D', DarkmoonLongbow.DAMAGE, "Shoots",
                DarkmoonLongbow.HIT_RATE, DarkmoonLongbow.RANGE);
    }

    /**
     * Set chance to do double damage.
     * @return damage the bow will do.
     */
    @Override
    public int damage() {
        float chanceToCriticalHit = r.nextFloat();
        if (chanceToCriticalHit <= DarkmoonLongbow.CRITICAL_CHANCE) {
            return super.damage() * DarkmoonLongbow.CRITICAL_BOOST;
        } else {
            return super.damage();
        }
    }
}
