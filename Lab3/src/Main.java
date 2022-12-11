import Human.*;
import Items.*;

public class Main {
    public static void main(String[] args) {

        Human neznaika = new Human("Незнайка", Sex.MAN,Size.SHORT);
        Human owner = new Human("Хозяин балаганчика", Sex.MAN, Size.SHORT);
        Human shorty = new Human("коротышке ", Sex.MAN,Size.SHORT);
        Human one = new Human(" один из зрителей", Sex.MAN, Size.SHORT);
        Crowd evrerybody = new Crowd("Каждый");
        Basket basket = new Basket("корзина", Size.HIGH);
        Ball balls = new Ball("мячи", Size.SMALL);
        Human.Face.Eye eye = new Human.Face.Eye();


        Human kozlick = new Human("Козлик", Sex.MAN, Size.SHORT);
        Crowd all = new Crowd();
        Curtain curtain = new Curtain("занавеска", Size.HIGH);
        Game g = new Game("игра");
        Ball b = new Ball("мяч", Size.SMALL);
        Ball moreBalls = new Ball("мячи", Size.SMALL);

        Ball.FormBalls form = moreBalls.getFormBalls();



        System.out.println(""+ Time.HERE + neznaika + neznaika.look() + owner.otherForm());
        System.out.println(Form.HE + owner.stay() + Where.NEAR + basket + How.FULL + balls.otherForm()+ form.returnBalls1() + evrerybody + Form.WHO);
        System.out.println(evrerybody.wanted() + evrerybody.throwBall() + form.returnBalls2() + "в " + Form.SHORTY + all.pay() + Time.LIKE_AT_THIS_MOMENT + one + one.wantingToAmuse());
        System.out.println(Form.YOURSELF_AND_OTHERS + one.payed() + " и " + one.beganToThrow() + Form.THEM + "в " + one.face() + one.Shortyman());
        System.out.println(Form.FROM + "" + Numeral.FOUR + form.returnBalls3() + shorty + shorty.dodge() + How.BUT + Numeral.FIVE + b.hit() + shorty.eye()+ ", да " + How.HOWPOWER + shorty.stringEyelid());
        System.out.println(Time.INSTANTLY + shorty.eyelidswollen() + shorty.eyeL() + Form.UNLUCKYMAN + eye + one.scaryman() + one.say()+ ", что " + Time.TODAY);
        System.out.println(Form.HEL + "уже " + one.heCant() + "и" + one.goToHome()+ owner + owner.notConfused() + "и," + owner.climbingToPomost() + owner.yelled());


        System.out.println("");

        System.out.print(Time.NOW + kozlick.getThrought() + Form.CR);
        System.out.print(" и,"+ kozlick.jumpUp()+ "," + kozlick.say() + ":");
        System.out.println(Form.ALL + all.laugh() + Where.AROUND);

        System.out.println(kozlick.persevere() + kozlick.payAttention() + all.laughing() + Form.SHORTYS + kozlick.hide() + Where.CURTAIN);
        System.out.println("и " + kozlick.pushThrought() + Where.HOLE + ".");

        System.out.println("" + Form.HE  + Time.AT_THE_MOMENT + kozlick.makeSure() + ", что " + curtain.notPass() + How.HARD);
        System.out.println(kozlick.rejectHead()+ "и"+ kozlick.act() + Where.HERE + How.MAX +kozlick.nimbly());

        System.out.println(kozlick.noTime() + Form.HEL + kozlick.lookAround() + ", как " + g + g.Ga()+ "и" + How.MET + b.aptly() + b + Form.HIM +" по лбу.");


    }
}