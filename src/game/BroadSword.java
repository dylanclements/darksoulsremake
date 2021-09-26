package game;

public class BroadSword extends Sword{
    public static final int DAMAGE = 30;
    public static final int HIT_RATE = 80;

    /**
     * Constructor.
     */
    public BroadSword() {
        super("BroadSword", 'R', BroadSword.DAMAGE, "Yeets", BroadSword.HIT_RATE);
    }

}
