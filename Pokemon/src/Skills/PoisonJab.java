package Skills;

import ru.ifmo.se.pokemon.*;

public class PoisonJab extends PhysicalMove {
    public PoisonJab() {
        super(Type.POISON, 80, 1);
    }

    @Override
    protected String describe() {
        return "наносит ужасной силы Poison Jab";
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if(Math.random() <= 0.3)
            Effect.poison(p);
    }
}
