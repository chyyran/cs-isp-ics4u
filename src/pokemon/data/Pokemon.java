// Pokemon Class

package pokemon.data;

import serialization.srsf.Lazy;

import java.util.ArrayList;
import java.util.List;

public class Pokemon {
    private Lazy<PokemonSpecies> species;
    private List<Lazy<PokemonMove>> moves;
    private String name;
    private int level;
    private String id;
    private int maxHp;
    private int currentHp;
    private static int MULTIPLIER = 400;

    public Pokemon(String id, Lazy<PokemonSpecies> species, List<Lazy<PokemonMove>> moves, String name, int level) {
        this(id, species, moves, name, level, level * MULTIPLIER);
    }
    public Pokemon(String id, Lazy<PokemonSpecies> species, List<Lazy<PokemonMove>> moves, String name, int level, int hp) {
        this.id = id;
        this.species = species;
        this.moves = moves;
        this.name = name;
        this.level = level;
        this.id = id;
        maxHp = level * MULTIPLIER;
        currentHp = hp;
    }

    public PokemonSpecies getSpecies() {
        return species.getValue();
    }

    public List<PokemonMove> getMoves() {
        List<PokemonMove> temp = new ArrayList<PokemonMove>();
        for (Lazy<PokemonMove> m : this.moves) {
            temp.add(m.getValue());
        }
        return temp;
    }

    public String getNickname() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int num) {
        level = num;
        maxHp = level * MULTIPLIER;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public void setHp(int num) {
        if (num < maxHp) {
            currentHp = num;
        } else {
            currentHp = maxHp;
        }
        if(currentHp < 0) {
            currentHp = 0;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LVL");
        sb.append(this.getLevel());
        sb.append(" " + this.getNickname());
        sb.append(" " + this.getCurrentHp() + "/" + this.getMaxHp());
        if(this.isFainted()) {
            sb.append(" [FNT]");
        }
        return sb.toString();
    }

    public boolean isFainted() {
        return currentHp == 0;
    }

    public String getId() {
        return this.id;
    }
}