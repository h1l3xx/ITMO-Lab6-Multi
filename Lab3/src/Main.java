import Human.*;
import Items.*;

public class Main {
    public static void main(String[] args) {
        Human kozlick = new Human("Козлик", Sex.MAN, Size.SHORT);
        Crowd all = new Crowd();
        Curtain curtain = new Curtain("занавеска", Size.HIGH);
        Game g = new Game("игра");
        Ball b = new Ball("мяч", Size.SMALL);


        System.out.print(Time.NOW + kozlick.getThrought() + Form.CR);
        System.out.print(" и,"+ kozlick.jumpUp()+ "," + kozlick.say());
        System.out.println(Form.ALL + all.laugh() + Where.AROUND);

        System.out.println(kozlick.persevere() + kozlick.payAttention() + all.laughing() + Form.SHRT + kozlick.hide() + Where.CURTAIN);
        System.out.println("и " + kozlick.pushThrought() + Where.HOLE + ".");

        System.out.println("" + Form.HE  + Time.AT_THE_MOMENT + kozlick.mksr() + ", что " + curtain.notPass() + How.HARD);
        System.out.println(kozlick.rejectHead()+ "и"+ kozlick.act() + Where.HERE + How.MAX +kozlick.nimbly());

        System.out.println(kozlick.noTime() + Form.HEL + kozlick.lookAround() + ", как " + g + g.Ga()+ "и" + How.MET + b.aptly() + b + Form.HIM +" по лбу.");


    }
}