package pokemon.menu;

import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.core.battle.BattleManager;
import pokemon.core.battle.BattleState;
import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonTeam;
import serialization.srsf.Lazy;
import serialization.srsf.LazyResolver;

import java.util.*;

public class BattleMenu extends MenuOption {


    private final MenuBuilder menuBuilder;
    private final PokemonTeam team;

    public BattleMenu(PokemonTeam team) {
        super("Battle");
        this.menuBuilder = new MenuBuilder();
        this.team = team;
    }

    private static PokemonTeam getRandomTeam(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        return new PokemonTeam(getSixPokemon(validSpecies, validMoves));
    }

    private static List<Lazy<PokemonMove>> getFourMoves(List<PokemonMove> validMoves) {
        List<PokemonMove> copy = new LinkedList<>(validMoves);
        Collections.shuffle(copy);
        return Lazy.asLazyList(copy.subList(0, 4));
    }

    private static ArrayList<Lazy<Pokemon>> getSixPokemon(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        List<PokemonSpecies> copy = new LinkedList<>(validSpecies);
        Collections.shuffle(copy);
        ArrayList<Lazy<Pokemon>> pokemon = new ArrayList<>();
        for (PokemonSpecies species : copy.subList(0, 6)) {
            pokemon.add(Lazy.asLazy(new Pokemon(UUID.randomUUID().toString(),
                    Lazy.asLazy(species), getFourMoves(validMoves),
                    species.getName(), new Random().nextInt(1))));
        }
        return pokemon;
    }

    public void run() {
        PokemonTeam cpuTeam = getRandomTeam(null, null); //todo: I am broken.
        BattleManager manager = new BattleManager(team, cpuTeam);
        do {
            //there is some pseudocode, deal with accepting input here.
        } while (!(manager.getState() == BattleState.PLAYER_ONE_VICTORY)
                || !(manager.getState() == BattleState.PLAYER_TWO_VICTORY));
    }
}
