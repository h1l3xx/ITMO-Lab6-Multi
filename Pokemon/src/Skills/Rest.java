package Skills;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 1);
    }

    static Effect e = new Effect().condition(Status.SLEEP);

//    @Override
    protected void applySelfEffects(Pokemon p) {
        p.addEffect(e);
        p.setMod(Stat.HP, -(int) (p.getStat(Stat.HP)));
    }

//    @Override
    protected String describe() {
        return "решил отдохнуть";
    }
}
