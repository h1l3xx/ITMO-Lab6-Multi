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
}
