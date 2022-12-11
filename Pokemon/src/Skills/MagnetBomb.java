package Skills;

import ru.ifmo.se.pokemon.*;

public class MagnetBomb extends PhysicalMove{
    public MagnetBomb(){
        super(Type.STEEL, 60, 1);
    }

    @Override
    protected String describe(){
        return "решил использовать Magnet Bomb";
    }
    @Override
    protected boolean checkAccuracy(Pokemon p1, Pokemon p2) {return true;}
}
