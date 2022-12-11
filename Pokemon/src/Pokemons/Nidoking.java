package Pokemons;

import Skills.Flamethrower;
import Skills.Leer;
import Skills.PoisonJab;
import Skills.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Nidoking extends Pokemon{
    public Nidoking(String name, int lvl){
        super(name, lvl);

        this.setStats(81,102,77,85,85,75);
        this.setType(Type.POISON, Type.GROUND);
        this.setMove(new Rest(), new PoisonJab(), new Leer(), new Flamethrower());
    }
}
