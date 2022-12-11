package Items;

import Human.Size;

public class Basket extends Item{

    public Basket(String name, Size size) {
        super(name, size);
    }
    public String toString(){
        return "большой белой корзины,";
    }
}
