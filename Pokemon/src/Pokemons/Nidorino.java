package Pokemons;

import Skills.Leer;
import Skills.PoisonJab;
import Skills.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Nidorino extends Pokemon{
    public Nidorino(String name, int lvl){
        super(name, lvl);

        this.setStats(61, 72, 57, 65, 55, 55);
        this.setType(Type.POISON);
        this.setMove(new Rest(), new PoisonJab(), new Leer());
    }
}
