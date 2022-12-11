package Pokemons;

import Skills.Thunder;
import Skills.ThunderWave;
import Skills.Thunderbolt;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Nosepass extends Pokemon {
    public Nosepass(String name, int lvl) {
        super(name, lvl);

        this.setStats(30, 45, 135, 30, 45, 90);
        this.setType(Type.ROCK);
        this.setMove(new Thunderbolt(),new Thunder(), new ThunderWave());
    }
}
