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
    private final List<PokemonSpecies> validSpecies;
    private final List<PokemonMove> validMoves;

    public BattleMenu(PokemonTeam team, List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        super("Battle");
        this.menuBuilder = new MenuBuilder();
        this.team = team;
        this.validMoves = validMoves;
        this.validSpecies = validSpecies;
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
        PokemonTeam cpuTeam = getRandomTeam(validSpecies, validMoves); //todo: I am broken.
        BattleManager manager = new BattleManager(team, cpuTeam);
        do {
            switch (manager.getState()) {
                case PLAYER_ONE_MOVE:
                    //can not switch during battle
                    for (PokemonMove move : team.getActivePokemon().getMoves()) {
                        System.out.println(move);
                    }
                    //input, choose move
                    PokemonMove move = null;
                    manager.applyMove(move, team.getActivePokemon(), cpuTeam.getActivePokemon());
                    break;
                case PLAYER_ONE_FAINTED:
                    //make them switch here
                    break;
                case PLAYER_ONE_VICTORY:
                    System.out.println("you're winner!");
                    break;
                case PLAYER_TWO_FAINTED:
                    //switch with a random non fainted in the cpu
                    break;
                case PLAYER_TWO_MOVE:
                    //literally rngesus a move and apply it thats it.
                    break;
                case PLAYER_TWO_VICTORY:
                    System.out.println("you got beat by rngesus");
                    break;
            }

        } while (!(manager.getState() == BattleState.PLAYER_ONE_VICTORY)
                || !(manager.getState() == BattleState.PLAYER_TWO_VICTORY));
    }
}
