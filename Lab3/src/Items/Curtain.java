package Items;

import Human.Size;

public class Curtain extends Item{


    public Curtain(String name, Size size) {
        super(name, size);
    }

    public String notPass(){
        return " не позволяла";
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
