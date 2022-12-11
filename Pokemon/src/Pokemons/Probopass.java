package Pokemons;

import Skills.MagnetBomb;
import Skills.Thunder;
import Skills.ThunderWave;
import Skills.Thunderbolt;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Probopass extends Pokemon {
    public Probopass(String name, int lvl){
        super(name, lvl);

        this.setStats(60, 55, 145, 40, 75,150);
        this.setType(Type.ROCK, Type.STEEL);
        this.setMove(new Thunderbolt(), new Thunder(), new ThunderWave(), new MagnetBomb());
    }
}
