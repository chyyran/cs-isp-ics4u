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
	private static int tHealth, sHealth;

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
                new Random().nextInt(LEVEL_RANGE) + 1);
        BattleManager manager = new BattleManager(team, cpuTeam);
        Scanner sc = new Scanner(System.in);
        boolean gameEnd = false;
        do {
            switch (manager.getState()) {
                case PLAYER_ONE_MOVE:
                    //can not switch during battle
                    Pokemon activePokemon = team.getActivePokemon();
					Pokemon targetPokemon = cpuTeam.getActivePokemon();
					if(targetPokemon.isFainted()) {
						System.out.println (targetPokemon.getNickname() + " has fainted. \n");
                        manager.setState(BattleState.PLAYER_TWO_FAINTED);
                        break;
                    }
                    if(activePokemon.isFainted()) {
						System.out.println ("Your " + activePokemon.getNickname() + " has fainted. \n");
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }
                    
					tHealth = targetPokemon.getCurrentHp();
					sHealth = activePokemon.getCurrentHp();
					
					System.out.println("You: " + activePokemon);
                    System.out.println("Opponent: " + targetPokemon);
                    List<PokemonMove> moves = activePokemon.getMoves();
                    for(int i = 0; i < moves.size(); i++) {
                        System.out.println("[" + (i+1) + "] " + moves.get(i));
                    }
                    System.out.println("[" + (moves.size() + 1) + "] Switch Pokemon.");
                    int moveSelect = 0;
                    do {
                        try {
                            System.out.println("Please choose a move.");
                            moveSelect = Integer.parseInt(sc.nextLine());
                        }catch(NumberFormatException e) {
                            continue;
                        }
                    }while(moveSelect <= 0 || moveSelect > moves.size() + 1);
                    if(moveSelect == moves.size() + 1) {
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }

                    PokemonMove move = moves.get(moveSelect - 1);
                    manager.applyMove(move, team.getActivePokemon(), cpuTeam.getActivePokemon());
					System.out.println(activePokemon.getNickname() + " used " + move.getName() + "!");
					System.out.println(activePokemon.getNickname() + " dealt " + (tHealth - targetPokemon.getCurrentHp()) + " damage! \n");
					
					if (sHealth < activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " healed for" + 
							Math.abs(sHealth - activePokemon.getCurrentHp()) + " damage! \n");
					}else if (sHealth > activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " dealt " + 
							(sHealth - activePokemon.getCurrentHp()) + " to itself! \n");
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
                    System.out.println("Please choose a new Pokemon.");
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
                            System.out.println(pokemon.get(newPoke - 1).getNickname() + " has fainted and can not battle!");
                            break;
                        }
                        team.setActivePokemon(newPoke - 1);
                        manager.setState(BattleState.PLAYER_ONE_MOVE);
                    }catch(NumberFormatException e) {
                        break;
                    }
                    break;
                case PLAYER_ONE_VICTORY:
<<<<<<< HEAD
                    System.out.println("you're the winner!");
=======
                    System.out.println("Congratulations, you've won!");
>>>>>>> origin/master
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
					if(cpuActivePokemon.isFainted()) {
						System.out.println (cpuActivePokemon.getNickname() + " has fainted. \n");
                        manager.setState(BattleState.PLAYER_TWO_FAINTED);
                        break;
                    }
                    if(playerTargetPokemon.isFainted()) {
						System.out.println ("Your " + playerTargetPokemon.getNickname() + " has fainted. \n");
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }
					
					tHealth = playerTargetPokemon.getCurrentHp();
					sHealth = cpuActivePokemon.getCurrentHp();
            
                    List<PokemonMove> cpuMoves = cpuActivePokemon.getMoves();
                    PokemonMove cpuMove = cpuMoves.get(new Random().nextInt(cpuMoves.size()));
                    manager.applyMove(cpuMove, cpuActivePokemon, playerTargetPokemon);
					
                    System.out.println(cpuActivePokemon.getNickname() + " used " + cpuMove.getName() + "!");
					System.out.println(cpuActivePokemon.getNickname() + " dealt " + (tHealth - playerTargetPokemon.getCurrentHp()) + " damage! \n");
					
					if (sHealth < cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " healed for" + 
							Math.abs(sHealth - cpuActivePokemon.getCurrentHp()) + " damage! \n");
					}
					else if (sHealth > cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " dealt " + 
							(sHealth - cpuActivePokemon.getCurrentHp()) + " to itself! \n");
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
