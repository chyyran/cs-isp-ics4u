//Pokemon Team Class
package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;

public class PokemonTeam {
    private ArrayList<Lazy<Pokemon>> pokemon;

    public PokemonTeam(ArrayList<Lazy<Pokemon>> pokemon) {
        this.pokemon = pokemon;
    }

    public void switchPosition(int pos1, int pos2) {
        Lazy<Pokemon> x = pokemon.get(pos1);
        Lazy<Pokemon> y = pokemon.get(pos2);
        pokemon.set(pos2, x);
        pokemon.set(pos1, y);
    }

    public List<Pokemon> getPokemon() {
        ArrayList<Pokemon> list = new ArrayList<Pokemon>();
        for (Lazy<Pokemon> p : pokemon) {
            //add p.getValue() to a new ArrayList
            list.add(p.getValue());
        }
        //return muh array list
        return list;
    }

    public Pokemon getActivePokemon() {
        return pokemon.get(0).getValue();
    }

    public void setActivePokemon(int pos) {
        Lazy<Pokemon> x = pokemon.get(pos);
        Lazy<Pokemon> y = pokemon.get(0);
        pokemon.set(0, x);
        pokemon.set(pos, y);
    }
}