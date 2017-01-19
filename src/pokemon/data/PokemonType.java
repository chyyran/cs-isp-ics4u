package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;

public class PokemonType {
    private final String name;
    private final List<Lazy<PokemonType>> weaknesses;
    private final List<Lazy<PokemonType>> strengths;
    private final List<Lazy<PokemonType>> immunities;

    public PokemonType(String name, List<Lazy<PokemonType>> weaknesses,
                       List<Lazy<PokemonType>> strengths, List<Lazy<PokemonType>> immunities) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
        this.immunities = immunities;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.getName();
    }
    public List<PokemonType> getWeaknesses() {
        // this needs caching
        List<PokemonType> types = Lazy.asList(this.weaknesses);
        return types;
    }

    public List<PokemonType> getStrengths() {
        //also needs caching.
        List<PokemonType> types = Lazy.asList(this.strengths);
        return types;
    }

    public List<PokemonType> getImmunities() {
        //also needs caching.
        List<PokemonType> types = Lazy.asList(this.immunities);
        return types;
    }

    public boolean isImmuneAgainst(PokemonType type) {
        return false; //todo: implement
    }

    public boolean isStrongAgainst(PokemonType type) {
        return false; //todo: implement
    }

    public boolean isWeakAgainst(PokemonType type) {
        return false; //todo: implement
    }
}
