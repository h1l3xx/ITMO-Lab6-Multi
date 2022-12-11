package Skills;

import ru.ifmo.se.pokemon.*;

public class DreamEater extends SpecialMove{
    public DreamEater(){
        super(Type.PSYCHIC, 100, 1);
    }

    @Override
    protected String describe() {return "решает использовать Dream Eater";}

    @Override
    protected double calcBaseDamage(Pokemon p1, Pokemon p2) {
        if (p2.getCondition() == Status.SLEEP) {
            return super.calcBaseDamage(p1, p2);
        }
        return 0;
    }
    @Override
    protected void applySelfDamage(Pokemon p1, double p2) {
        p1.setMod(Stat.HP, (int) Math.ceil(p2 / 2));
    }
}
