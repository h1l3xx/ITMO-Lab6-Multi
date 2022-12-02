package Human;

public class Crowd extends Human {


    public Crowd() {}

    public String laughing(){
        return "смеющихся ";
    }
    @Override
    public String laugh(){
        return "засмеялись ";
    }



    @Override
    public String toString() {
        return this.name;
    }


    @Override
    public int hashCode() {
        return this.name.hashCode();
    }



}
