package pokemon.data.comparators;

import pokemon.data.PokemonSpecies;

import java.util.Comparator;

/**
 * Compares two Pokemon species by their Pokemon number
 */
public class PokemonSpeciesNumberComparator implements Comparator<PokemonSpecies> {

    /**
     * Compares two pokemon species by their Pokemon number
     * @param speciesOne The first species
     * @param speciesTwo The second species
     * @return A positive number if speciesOne comes after speciesTwo's number, a negative number if otherwise, and 0 if
     *         both species have the same number.
     */
    @Override
    public int compare(PokemonSpecies speciesOne, PokemonSpecies speciesTwo) {
        return Integer.compare(speciesOne.getNumber(), speciesTwo.getNumber());
    }
}
