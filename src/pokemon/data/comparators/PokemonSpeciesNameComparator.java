package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

public class PokemonSpeciesNameComparator implements Comparator<PokemonSpecies> {
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return speciesOne.getName().compareTo(speciesTwo.getName());
    }
}
