//Pokemon Team Class
package pokemon.data;

import serialization.srsf.Lazy;
import serialization.srsf.LazyResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * A team of up to 6 Pokemon
 */
public class PokemonTeam {
    /**
     * The message to display for an empty slot.
     */
    private static final String STR_EMPTY_SLOT = "EMPTY SLOT";
    /**
     * The array list to store the Pokemon
     */
    private ArrayList<Lazy<Pokemon>> pokemon;

    /**
     * Initialize with an empty team
     */
    public PokemonTeam() {
        this(new ArrayList<Lazy<Pokemon>>(6));
    }

    /**
     * Initialize with the given Pokemon
     * @param pokemon The array of Pokemon
     */
    @SuppressWarnings("unchecked")
    public PokemonTeam(ArrayList<Lazy<Pokemon>> pokemon) {
        this.pokemon = pokemon;
        for (int i = 0; i < 6; i++) {
            if (!(i < this.getPokemon().size())) {
                this.pokemon.add(i, new Lazy<>(LazyResolver.NULL_RESOLVER)); //Resolve null for empty spaces in the first 6 pokemon.
            }
        }
    }

    /**
     * Switch the positions of two Pokemon
     * @param pos1 The first position
     * @param pos2 The second position
     */
    public void switchPosition(int pos1, int pos2) {
        Lazy<Pokemon> x = pokemon.get(pos1);
        Lazy<Pokemon> y = pokemon.get(pos2);
        pokemon.set(pos2, x);
        pokemon.set(pos1, y);
    }

    /**
     * Returns an immutable shallow copy of the list of Pokemon in the team, with order preserved
     * @return The list of Pokemon int he team
     */
    public List<Pokemon> getPokemon() {
        return Lazy.asList(pokemon);
    }

    /**
     * Sets the Pokemon in the given position
     * @param pos The position of the Pokemon
     * @param pokemon The Pokemon to put in this slot
     */
    public void setPokemon(int pos, final Pokemon pokemon) {
        this.pokemon.set(pos, new Lazy<>(new LazyResolver<Pokemon>() {
            @Override
            public Pokemon resolve() {
                return pokemon;
            }
        }));
    }

    /**
     * Gets the active, or first Pokemon
     * @return The active Pokemon
     */
    public Pokemon getActivePokemon() {
        return pokemon.get(0).getValue();
    }

    /**
     * Sets the active Pokemon to the Pokemon at the given position
     * @param pos The Pokemon to become the active Pokemon
     */
    public void setActivePokemon(int pos) {
        this.switchPosition(0, pos);
    }

    /**
     * Returns a string representation of the team
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String newline = System.getProperty("line.separator");
        for (int i = 0; i < 6; i++) {
            Pokemon pokemon = this.getPokemon().get(i);
            sb.append("["+(i+1)+"] ");
            if (pokemon != null) {
              sb.append(pokemon);
              sb.append(newline);
            } else {

                sb.append(STR_EMPTY_SLOT);
                sb.append(System.getProperty(newline));
            }
        }
        return sb.toString();
    }
}