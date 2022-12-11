package Skills;

import ru.ifmo.se.pokemon.*;

public class NightShade extends SpecialMove{
    public NightShade(){
        super(Type.GHOST, 1, 1);
    }
    @Override
    protected String describe() {
        return "решает использовать Night Shade";
    }
    @Override
    protected double calcBaseDamage(Pokemon p, Pokemon p1) {

        return p.getLevel();
    }
}
