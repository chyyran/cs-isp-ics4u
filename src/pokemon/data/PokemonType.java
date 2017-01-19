package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;

public class PokemonType {
    private final String name;
    private final List<Lazy<PokemonType>> weaknesses;
    private final List<Lazy<PokemonType>> strengths;


    public PokemonType(String name, List<Lazy<PokemonType>> weaknesses, List<Lazy<PokemonType>> strengths) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
    }

    public String getName() {
        return this.name;
    }

    public List<PokemonType> getWeaknesses() {
        // this needs caching
        List<PokemonType> types = new ArrayList<>();
        for (Lazy<PokemonType> type : this.weaknesses) {
            types.add(type.getValue());
        }
        return types;
    }

    public List<PokemonType> getStrengths() {
        //also needs caching.
        List<PokemonType> types = new ArrayList<>();
        for (Lazy<PokemonType> type : this.strengths) {
            types.add(type.getValue());
        }
        return types;
    }
}
