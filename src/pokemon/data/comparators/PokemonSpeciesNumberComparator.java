package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

public class PokemonSpeciesNumberComparator implements Comparator<PokemonSpecies> {
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return Integer.compare(speciesOne.getNumber(), speciesTwo.getNumber());
    }
}
