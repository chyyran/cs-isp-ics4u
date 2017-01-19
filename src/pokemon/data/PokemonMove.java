//PokemonMove class
package pokemon.data;

import serialization.srsf.Lazy;

public class PokemonMove {
    private String name;
    private Lazy<PokemonType> type;
    private double baseDamage;
    private double selfDamage;

    public PokemonMove(String name, Lazy<PokemonType> type, double damage, double selfDamage) {
        this.name = name;
        this.type = type;
        baseDamage = damage;
        this.selfDamage = selfDamage;
    }

    public String getName() {
        return name;
    }

    public int getDamage() {
        return (int) Math.round(baseDamage);
    }

    public int getSelfDamage() {

        return (int) Math.round(selfDamage);
    }

    public PokemonType getType() {
        return type.getValue();
    }

    public boolean equals(PokemonMove that) {
        return (that.getName().equals(getName()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[" + this.getType() + "] ");
        sb.append(this.name);
        return sb.toString();
    }
}