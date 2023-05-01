import Human.*;
import Items.*;

public class Main {
    public static void main(String[] args) throws NoNameException {



        Human neznaika, owner, shorty, one, kozlick, ponchik;


        try {
            ponchik = new Human("Пончик", Sex.MAN, Size.SHORT);
            neznaika = new Human("Незнайка", Sex.NONE, Size.SHORT);
            owner = new Human("Хозяин балаганчика", Sex.MAN, Size.SHORT);
            shorty = new Human("коротышке ", Sex.MAN,Size.SHORT);
            one = new Human(" один из зрителей", Sex.MAN, Size.SHORT);
            kozlick = new Human("Козлик", Sex.MAN, Size.SHORT);
        } catch (NoNameException e) {
            throw new NoNameException();
        }

        Crowd everybody = new Crowd("Каждый");
        Basket basket = new Basket("корзина", Size.HIGH);
        Ball balls = new Ball("мячи", Size.SMALL);


        Crowd all = new Crowd();
        Curtain curtain = new Curtain("занавеска", Size.HIGH);
        Game g = new Game("игра");
        Ball b = new Ball("мяч", Size.SMALL);

        Ball.FormBalls form = b.getFormBalls();


        //Дополнение

        System.out.println(ponchik + ponchik.standUp() + Where.UP + ", и ");



        System.out.println(Time.HERE.toString() + neznaika + neznaika.look() + owner.otherForm());
        System.out.println(Form.HE + owner.stay() + Where.NEAR + basket + How.FULL + balls.otherForm()+ form.ballPluralCreativeCase() + everybody + Form.WHO);
        System.out.println(everybody.wanted() + everybody.throwBall() + form.ballSingularCreativeCase() + "в " + Form.SHORTY + all.pay() + Time.LIKE_AT_THIS_MOMENT + one + one.wantingToAmuse());
        System.out.println(Form.YOURSELF_AND_OTHERS + one.payed() + " и " + one.beganToThrow() + Form.THEM + "в " + one.charactersFace() + one.shortyMan());
        System.out.println(Form.FROM + "" + Numeral.FOUR + form.ballPluralGenitiveCase() + shorty + shorty.dodge() + How.BUT + Numeral.FIVE + b.hit() + shorty.eye()+ ", да " + How.HOWPOWER + shorty.stringEyelid());
        try {
        System.out.println(Time.INSTANTLY + shorty.eyelidSwollen() + shorty.eye() + Form.UNLUCKY_MAN + shorty.eyeDontSee() );
        shorty.checkHealth();}
        catch (PoorHealthException e){
        System.out.println(one.getScary()+ one.say()+ ", что " + Time.TODAY + Form.HE + "уже " + one.heCant() + "и" + one.goToHome());}
        System.out.println("" + owner+ How.HOWEVER + owner.notConfused() + "и," + owner.climbingToPomost() + owner.yelled());


        System.out.println();

        System.out.print(Time.NOW + kozlick.getThrought() + Form.CR);
        System.out.print(" и,"+ kozlick.jumpUp()+ "," + kozlick.say() + ":");
        System.out.println(Form.ALL + all.laugh() + Where.AROUND);

        System.out.println(kozlick.persevere() + kozlick.payAttention() + all.laughing() + Form.SHORTYS + kozlick.hide() + Where.CURTAIN);
        System.out.println("и " + kozlick.pushThrought() + Where.HOLE + ".");

        System.out.println("" + Form.HE  + Time.AT_THE_MOMENT + kozlick.makeSure() + ", что " + curtain.notPass() + How.HARD);
        System.out.println(kozlick.rejectHead()+ "и"+ kozlick.act() + Where.HERE + How.MAX +kozlick.nimbly());

        System.out.println(kozlick.noTime() + Form.HE + kozlick.lookAround() + ", как " + g + g.startGame() + "и" + How.MET + b.aptly() + b + Form.HIM +" по лбу.");


    }
}