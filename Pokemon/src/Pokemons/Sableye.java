package Pokemons;

import Skills.Confide;
import Skills.DreamEater;
import Skills.NastyPlot;
import Skills.NightShade;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;
public class Sableye extends Pokemon {
    public Sableye(String name, int lvl){
        super(name, lvl);

        this.setStats(50, 75, 75, 50, 65,65);
        this.setType(Type.DARK, Type.GHOST);
        this.setMove(new NightShade(), new Confide(), new DreamEater(), new NastyPlot());
    }
}
