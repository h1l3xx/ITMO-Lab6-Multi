package Skills;

import ru.ifmo.se.pokemon.*;

public class Confide  extends StatusMove{
    public Confide(){
        super(Type.NORMAL, 0, 1);
    }

    @Override
    protected String describe() {
        return "решает использовать Confide";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.setMod(Stat.SPECIAL_ATTACK, -1);
    }
}
