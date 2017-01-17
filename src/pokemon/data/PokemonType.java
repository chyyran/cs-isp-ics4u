package serialization.srsf;

import java.util.List;

/**
 * Created by Ronny on 2017-01-12.
 */
public class PokemonType
{
    private final String name;
    private final Lazy<List<PokemonType>> weaknesses;
    private final Lazy<List<PokemonType>> strengths;
    public PokemonType(String name, Lazy<List<PokemonType>> weaknesses, Lazy<List<PokemonType>> strengths) {
        this.name = name;
        this.weaknesses = weaknesses;
        this.strengths = strengths;
    }

    public String getName() {
        return this.name;
    }

    public List<PokemonType> getWeaknesses() {
        return this.weaknesses.getValue();
    }

    public List<PokemonType> getStrengths() {
        return this.strengths.getValue();
    }
}
