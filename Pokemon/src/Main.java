import Pokemons.*;
import ru.ifmo.se.pokemon.*;

public class Main {
    public static void main(String[] args){
        Battle b = new Battle();

        b.addAlly(new Sableye("Мяу", 1));
        b.addAlly(new Probopass("Пётр",1));
        b.addAlly(new Nidorino("Маша", 1));
        b.addFoe(new Nosepass("Гав", 1));
        b.addFoe(new NidoranM("Василий", 1));
        b.addFoe(new Nidoking("Саша", 1));

        b.go();
    }

}