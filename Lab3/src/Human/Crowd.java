package Human;

public class Crowd extends Human {


    public Crowd() {}

    public Crowd(String name){
        this.name = name;
    }

    public String laughing(){
        return "смеющихся ";
    }
    @Override
    public String laugh(){
        return "засмеялись ";
    }

    public String wanted(){
        return "хотел ";
    }

    public String throwBall(){
        return "швырнуть ";
    }

    public String pay(){
        return ", платили сантик. ";
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
