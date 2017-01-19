package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

public class PokemonSpeciesWeightComparator implements Comparator<PokemonSpecies> {
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return Double.compare(speciesOne.getWeight(), speciesTwo.getWeight());
    }
}
