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

    private final static int LEVEL_RANGE = 2;
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

    private static PokemonTeam getRandomTeam(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves, int max) {
        return new PokemonTeam(getSixPokemon(validSpecies, validMoves, max));
    }

    private static List<Lazy<PokemonMove>> getFourMoves(List<PokemonMove> validMoves) {
        List<PokemonMove> copy = new LinkedList<>(validMoves);
        Collections.shuffle(copy);
        return Lazy.asLazyList(copy.subList(0, 4));
    }

    private static ArrayList<Lazy<Pokemon>> getSixPokemon(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves, int max) {
        List<PokemonSpecies> copy = new LinkedList<>(validSpecies);
        Collections.shuffle(copy);
        ArrayList<Lazy<Pokemon>> pokemon = new ArrayList<>();
        for (PokemonSpecies species : copy.subList(0, 6)) {
            pokemon.add(Lazy.asLazy(new Pokemon(UUID.randomUUID().toString(),
                    Lazy.asLazy(species), getFourMoves(validMoves),
                    species.getName(), new Random().nextInt(max) + 1)));
        }
        return pokemon;
    }

    public void run() {
        if(team.getActivePokemon() == null) {
            System.out.println("Please make a team first!");
            return;
        }
        PokemonTeam cpuTeam = getRandomTeam(validSpecies, validMoves, team.getActivePokemon().getLevel() +
                new Random().nextInt(LEVEL_RANGE) + 1); //todo: I am broken.
        BattleManager manager = new BattleManager(team, cpuTeam);
        Scanner sc = new Scanner(System.in);
        boolean gameEnd = false;
        do {
            switch (manager.getState()) {
                case PLAYER_ONE_MOVE:
                    //can not switch during battle
                    Pokemon activePokemon = team.getActivePokemon();
                    Pokemon targetPokemon = cpuTeam.getActivePokemon();
                    System.out.println("You: " + activePokemon);
                    System.out.println("Opponent: " + targetPokemon);
                    List<PokemonMove> moves = activePokemon.getMoves();
                    for(int i = 0; i < moves.size(); i++) {
                        System.out.println("[" + (i+1) + "] " + moves.get(i));
                    }
                    int moveSelect = 0;
                    do {
                        try {
                            System.out.println("Please choose a move.");
                            moveSelect = Integer.parseInt(sc.nextLine());
                        }catch(NumberFormatException e) {
                            continue;
                        }
                    }while(moveSelect <= 0 || moveSelect > moves.size());
                    //input, choose move
                    PokemonMove move = moves.get(moveSelect - 1);
                    manager.applyMove(move, team.getActivePokemon(), cpuTeam.getActivePokemon());
                    if(targetPokemon.isFainted()) {
                        manager.setState(BattleState.PLAYER_TWO_FAINTED);
                        break;
                    }
                    if(activePokemon.isFainted()) {
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }
                    manager.setState(BattleState.PLAYER_TWO_MOVE);
                    break;
                case PLAYER_ONE_FAINTED:
                    List<Pokemon> pokemon = team.getPokemon();
                    boolean hasUsable = false;
                    for(Pokemon p : pokemon) {
                        if(p == null) continue;
                        if(!p.isFainted()){
                            hasUsable = true;
                            break;
                        }
                    }
                    if(!hasUsable) {
                        manager.setState(BattleState.PLAYER_TWO_VICTORY);
                        break;
                    }
                    System.out.println("Your Pokemon has fainted. Please choose a new one.");
                    System.out.print(team);
                    try {
                        int newPoke = Integer.parseInt(sc.nextLine());
                        if(newPoke > pokemon.size()) {
                            System.out.println("Invalid Pokemon.");
                            break;
                        }
                        if(pokemon.get(newPoke - 1) == null) {
                            System.out.println("Invalid Pokemon.");
                            break;
                        }
                        if(pokemon.get(newPoke - 1).isFainted()) {
                            System.out.println(pokemon.get(newPoke - 1).getNickname() + " is fainted and can not battle!");
                            break;
                        }
                        team.setActivePokemon(newPoke - 1);
                        manager.setState(BattleState.PLAYER_ONE_MOVE);
                    }catch(NumberFormatException e) {
                        break;
                    }
                    break;
                case PLAYER_ONE_VICTORY:
                    System.out.println("you're winner!");
                    for(Pokemon p : team.getPokemon()) {
                        if(p == null) continue;
                        p.setHp(p.getMaxHp());
                        p.setLevel(p.getLevel() + 1);
                    }
                    gameEnd = true;
                    break;
                case PLAYER_TWO_FAINTED:
                    List<Pokemon> cpuPoke = cpuTeam.getPokemon();
                    for(int i = 0; i < cpuPoke.size(); i++) {
                        if(!cpuPoke.get(i).isFainted()) {
                            cpuTeam.setActivePokemon(i);
                        }
                        if(cpuTeam.getActivePokemon().isFainted()) {
                            manager.setState(BattleState.PLAYER_ONE_VICTORY);
                        } else {
                            manager.setState(BattleState.PLAYER_TWO_MOVE);
                        }
                    }
                    //switch with a random non fainted in the cpu
                    break;
                case PLAYER_TWO_MOVE:
                    Pokemon cpuActivePokemon = cpuTeam.getActivePokemon();
                    Pokemon playerTargetPokemon = team.getActivePokemon();
                    List<PokemonMove> cpuMoves = cpuActivePokemon.getMoves();
                    PokemonMove cpuMove = cpuMoves.get(new Random().nextInt(cpuMoves.size()));
                    manager.applyMove(cpuMove, cpuActivePokemon, playerTargetPokemon);

                    if(playerTargetPokemon.isFainted()) {
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }
                    if(cpuActivePokemon.isFainted()) {
                        manager.setState(BattleState.PLAYER_TWO_FAINTED);
                        break;
                    }
                    manager.setState(BattleState.PLAYER_ONE_MOVE);
                    break;
                case PLAYER_TWO_VICTORY:
                    System.out.println("You lost! Try again next time!");
                    for(Pokemon p : team.getPokemon()) {
                        if(p == null) continue;
                        p.setHp(p.getMaxHp());
                    }
                    gameEnd = true;
                    break;
            }

        } while (!gameEnd);
    }
}
