package Skills;

import ru.ifmo.se.pokemon.*;

public class Flamethrower extends SpecialMove {
    public Flamethrower(){
        super(Type.FIRE, 90,1);
    }
    @Override
    protected String describe(){
        return "использует всесжигающий Flamethrower";
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.addEffect(new Effect().chance(0.1).condition(Status.BURN));
    }
}
