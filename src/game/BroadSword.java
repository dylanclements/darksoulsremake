package game;

/**
 * A basic sword weapon.
 */
public class BroadSword extends Sword{
    public static final int DAMAGE = 70;
    public static final int HIT_RATE = 80;

    /**
     * Constructor.
     */
    public BroadSword() {
        super("BroadSword", 'R', BroadSword.DAMAGE, "Yeets", BroadSword.HIT_RATE);
    }

}
