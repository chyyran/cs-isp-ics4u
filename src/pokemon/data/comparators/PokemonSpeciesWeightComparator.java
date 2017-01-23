package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

/**
 * Compares two pokemon species by their weight
 */
public class PokemonSpeciesWeightComparator implements Comparator<PokemonSpecies> {
    /**
     * Compares two pokemon species by their weight
     * @param speciesOne The first species
     * @param speciesTwo The second species
     * @return A positive number if speciesOne weighs more than speciesTwo number, a negative number if otherwise, and 0 if
     *         both species have the same weight.
     */
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return Double.compare(speciesOne.getWeight(), speciesTwo.getWeight());
    }
}
