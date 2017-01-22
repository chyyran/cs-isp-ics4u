//Pokemon Species Class
package pokemon.data;

import serialization.srsf.Lazy;

public class PokemonSpecies {
    private int number;
    private String name;
    private Lazy<PokemonType> primaryType;
    private Lazy<PokemonType> secondaryType;
    private double weight;
    private Lazy<PokemonSpecies> evolution;
    private Lazy<PokemonSpecies> preEvolution;

    public PokemonSpecies(String name, int num, Lazy<PokemonType> type1, Lazy<PokemonType> type2,
                          double weight, Lazy<PokemonSpecies> evolvesTo, Lazy<PokemonSpecies> evolvesFrom) {
        number = num;
        this.name = name;
        primaryType = type1;
        secondaryType = type2;
        this.weight = weight;
        evolution = evolvesTo;
        preEvolution = evolvesFrom;
    }

    public String getName() {
        return name;
    }

    public PokemonType getPrimaryType() {
        return primaryType.getValue();
    }

    public PokemonType getSecondaryType() {
        return secondaryType.getValue();
    }

    public double getWeight() {
        return weight;
    }

    public int getNumber() {
        return number;
    }

    public PokemonSpecies getNextEvolution() {
        return evolution.getValue();
    }

    public PokemonSpecies getPreviousEvolution() {
        return preEvolution.getValue();
    }

    public boolean equals(PokemonSpecies that) {
        return (that != null && that.getNumber() == getNumber());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        PokemonSpecies evolvesTo = this.getNextEvolution();
        PokemonSpecies evolvesFrom = this.getPreviousEvolution();
        sb.append("No. ");
        sb.append(this.getNumber());
        sb.append(": ");
        sb.append(this.getName());
        sb.append("\n");
        sb.append("Primary Type: ");
        sb.append(this.getPrimaryType());
        sb.append("\n");
        sb.append("Secondary Type: ");
        sb.append(this.getSecondaryType() == null ? "Does not exist" : this.getSecondaryType());
        sb.append("\n");
        sb.append("Weight: ");
        sb.append(this.getWeight());
        if(evolvesTo != null) {
            sb.append("\n");
            sb.append("Evolves to: No. ");
            sb.append(evolvesTo.getNumber());
            sb.append(": ");
            sb.append(evolvesTo.getName());
        }
        if(evolvesFrom != null) {
            sb.append("\n");
            sb.append("Evolves from: No. ");
            sb.append(this.getPreviousEvolution().getNumber());
            sb.append(": ");
            sb.append(this.getPreviousEvolution().getName());
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PokemonSpecies) return this.equals((PokemonSpecies) o);
        return false;
    }
}
