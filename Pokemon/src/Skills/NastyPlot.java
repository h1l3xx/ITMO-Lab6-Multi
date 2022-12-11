package Skills;

import ru.ifmo.se.pokemon.*;

public class NastyPlot extends StatusMove{
    public NastyPlot(){
        super(Type.DARK,0,0);
    }

    @Override
    protected String describe(){
        return "решил использовать Nasty Plot";
    }

    @Override
    protected void applySelfEffects(Pokemon p){
        p.setMod(Stat.SPECIAL_ATTACK, 1);
    }
}
