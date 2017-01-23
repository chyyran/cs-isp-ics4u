package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

/**
 * Compares PokemonSpecies by their name lexicographically
 */
public class PokemonSpeciesNameComparator implements Comparator<PokemonSpecies> {

    /**
     * Compares PokemonSpecies by their names
     * @param speciesOne The first species
     * @param speciesTwo The second species
     * @return A positive integer if the first Pokemon's name follows the second's lexicographcally,
     *         a negative integer if the second Pokemon's name follows the first's lexicographically,
     *         and 0 if both names are equal.
     */
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return speciesOne.getName().compareTo(speciesTwo.getName());
    }
}
