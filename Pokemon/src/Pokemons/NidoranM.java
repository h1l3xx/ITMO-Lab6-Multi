package Pokemons;

import Skills.Leer;
import Skills.PoisonJab;
import Skills.Rest;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class NidoranM extends Pokemon {
    public NidoranM(String name, int lvl){
        super(name, lvl);

        this.setStats(46, 57, 40, 50, 40,40);
        this.setType(Type.POISON);
        this.setMove(new Rest(), new PoisonJab());
    }
}
