package pokemon.menu;

import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.core.battle.BattleManager;
import pokemon.core.battle.BattleState;
import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonTeam;

import java.util.*;

/**
 * The Battle Menu
 */
public class BattleMenu extends MenuOption {

    private final PokemonTeam team;
    private final List<PokemonSpecies> validSpecies;
    private final List<PokemonMove> validMoves;

    public BattleMenu(PokemonTeam team, List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        super("Battle");
        this.team = team;
        this.validMoves = validMoves;
        this.validSpecies = validSpecies;
    }

    public void run() {
        if(team.getActivePokemon() == null) {
            System.out.println("Please make a team first!");
            return;
        }
        BattleManager manager = new BattleManager(team, validSpecies, validMoves);
        Scanner sc = new Scanner(System.in);
        boolean gameEnd = false;
        do {
            switch (manager.getState()) {
                case PLAYER_ONE_MOVE:
                    //can not switch during battle
                    Pokemon activePokemon = manager.getTeamOne().getActivePokemon();
					Pokemon targetPokemon = manager.getTeamTwo().getActivePokemon();
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
                    manager.applyMove(move, manager.getTeamOne().getActivePokemon(), manager.getTeamTwo().getActivePokemon());
					System.out.println(activePokemon.getNickname() + " used " + move.getName() + "!");
					System.out.println(activePokemon.getNickname() + " dealt " + (manager.getTargetHealth() - targetPokemon.getCurrentHp()) + " damage! \n");
					
					if (manager.getPlayerHealth() < activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " healed for" + 
							Math.abs(manager.getPlayerHealth() - activePokemon.getCurrentHp()) + " damage! \n");
					}else if (manager.getPlayerHealth() > activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " dealt " + 
							(manager.getPlayerHealth() - activePokemon.getCurrentHp()) + " to itself! \n");
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
                    System.out.println("Congratulations, you've won!");
                    for(Pokemon p : team.getPokemon()) {
                        if(p == null) continue;
                        p.setHp(p.getMaxHp());
                        p.setLevel(p.getLevel() + 1);
                    }
                    gameEnd = true;
                    break;
                case PLAYER_TWO_FAINTED:
                    List<Pokemon> cpuPoke = manager.getTeamTwo().getPokemon();
                    for(int i = 0; i < cpuPoke.size(); i++) {
                        if(!cpuPoke.get(i).isFainted()) {
                            manager.getTeamTwo().setActivePokemon(i);
                        }
                        if(manager.getTeamTwo().getActivePokemon().isFainted()) {
                            manager.setState(BattleState.PLAYER_ONE_VICTORY);
                        } else {
                            manager.setState(BattleState.PLAYER_TWO_MOVE);
                        }
                    }
                    //switch with a random non fainted in the cpu
                    break;
                case PLAYER_TWO_MOVE:
                    Pokemon cpuActivePokemon = manager.getTeamTwo().getActivePokemon();
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

                    List<PokemonMove> cpuMoves = cpuActivePokemon.getMoves();
                    PokemonMove cpuMove = cpuMoves.get(new Random().nextInt(cpuMoves.size()));
                    manager.applyMove(cpuMove, cpuActivePokemon, playerTargetPokemon);
					
                    System.out.println(cpuActivePokemon.getNickname() + " used " + cpuMove.getName() + "!");
					System.out.println(cpuActivePokemon.getNickname() + " dealt " + (manager.getTargetHealth() - playerTargetPokemon.getCurrentHp()) + " damage! \n");
					
					if (manager.getPlayerHealth() < cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " healed for" + 
							Math.abs(manager.getPlayerHealth() - cpuActivePokemon.getCurrentHp()) + " damage! \n");
					}
					else if (manager.getPlayerHealth() > cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " dealt " + 
							(manager.getPlayerHealth() - cpuActivePokemon.getCurrentHp()) + " to itself! \n");
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
