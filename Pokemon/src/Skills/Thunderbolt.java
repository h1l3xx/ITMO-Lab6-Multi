package Skills;

import ru.ifmo.se.pokemon.*;

public class Thunderbolt extends SpecialMove{
    public Thunderbolt(){
        super(Type.ELECTRIC,90,1);
    }
    @Override
    protected String describe(){
        return "решил использовать Thunderbolt";
    }
    @Override
    protected void applyOppEffects(Pokemon p){
        p.addEffect(new Effect().chance(0.1).condition(Status.PARALYZE));
    }
}
