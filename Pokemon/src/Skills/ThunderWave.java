package Skills;

import ru.ifmo.se.pokemon.*;

public class ThunderWave extends StatusMove {
    public ThunderWave() {
        super(Type.ELECTRIC, 0, 0.9);
    }

    @Override
    protected String describe() {
        return "парализует противника с помощью Thunder Wave";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        p.addEffect(new Effect().condition(Status.PARALYZE));
        p.addEffect(new Effect().chance(0.25).stat(Stat.ACCURACY, 0));
        p.addEffect(new Effect().stat(Stat.SPEED, (int)p.getStat(Stat.SPEED) / 2));
    }
}
