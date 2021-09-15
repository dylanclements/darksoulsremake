package game;

import game.interfaces.Soul;

public class SoulToken extends PortableItem implements Soul {
    private final int souls;

    public SoulToken() {
        super("Soul Token", '$');
        this.souls = 0;
    }

    public SoulToken(int souls) {
        super("Soul Token", '$');
        this.souls = souls;
    }

    public int getSouls() {
        return this.souls;
    }

    @Override
    public void transferSouls(Soul soulObject) {
        int tokenSouls = getSouls();
        this.subtractSouls(tokenSouls);
        soulObject.addSouls(tokenSouls);
    }
}
