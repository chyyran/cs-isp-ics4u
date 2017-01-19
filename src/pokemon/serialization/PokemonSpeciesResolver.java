package pokemon.serialization;

import pokemon.data.PokemonSpecies;
import serialization.srsf.LazyResolver;
import serialization.srsf.SerializationContext;

public class PokemonSpeciesResolver implements LazyResolver<PokemonSpecies> {
    private final SerializationContext context;
    private final int number;

    public PokemonSpeciesResolver(SerializationContext context, int number) {
        this.context = context;
        this.number = number;
    }

    @Override
    public PokemonSpecies resolve() {
        for (PokemonSpecies species : this.context.getCollection(PokemonSpecies.class)) {
            if (species.getNumber() == this.number) return species;
        }
        return null;
    }
}
