package Items;

import Human.Size;

public class Ball extends Item {

    public Ball(String name, Size size) {
        super(name, size);
    }

    @Override
    public String toString(){
        return this.name + " огрел ";
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    public String aptly() {
        return "брошенный ";
    }

    public String otherForm(){
        return "резиновыми ";
    }

    public String hit(){
        return "угодил ему прямо в ";
    }

    public interface FormBalls{
        String returnBalls1();
        String returnBalls2();
        String returnBalls3();
    }

    public FormBalls getFormBalls() {
        class MoreBalls implements FormBalls {
            @Override
            public String returnBalls1() {
                return "мячами. ";
            }

            @Override
            public String returnBalls2() {
                return "мячом ";
            }

            @Override
            public String returnBalls3() {
                return "мячей ";
            }
        }
        return new MoreBalls();
    }
}
