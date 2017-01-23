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
 * The Battle Menu runs the main battle loop.
 */
public class BattleMenu extends MenuOption {

    /**
     * The team the player will use
     */
    private final PokemonTeam team;
    /**
     * A list of valid species for the CPU to use
     */
    private final List<PokemonSpecies> validSpecies;
    /**
     * A list of valid moves that the CPU's pokemon can use
     */
    private final List<PokemonMove> validMoves;

    /**
     * Instantiates a new BattleMenu
     * @param team The team the player will use
     * @param validSpecies A list of valid species that the CPU will use
     * @param validMoves A list of valid moves that the CPU will use
     */
    public BattleMenu(PokemonTeam team, List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        super("Battle");
        this.team = team;
        this.validMoves = validMoves;
        this.validSpecies = validSpecies;
    }

    /**
     * Runs the main battle loop
     */
    public void run() {
        //check that the team is able to battle
        if(team.getActivePokemon() == null) {
            System.out.println("Please make a team first!");
            return;
        }

        //setup the battle
        BattleManager manager = new BattleManager(team, validSpecies, validMoves);
        Scanner sc = new Scanner(System.in);
        boolean gameEnd = false;


        do {
            switch (manager.getState()) {
                case PLAYER_ONE_MOVE: //When the player can make a move
                    Pokemon activePokemon = manager.getTeamOne().getActivePokemon();
					Pokemon targetPokemon = manager.getTeamTwo().getActivePokemon();

                    //Check that both Player and CPU's pokemon are usable
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

					//Display the current status
					System.out.println("You: " + activePokemon);
                    System.out.println("Opponent: " + targetPokemon);

                    //List out possible moves
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
                        //The last option allows the player to switch pokemon
                        manager.setState(BattleState.PLAYER_ONE_FAINTED);
                        break;
                    }

                    //Get and apply the move
                    PokemonMove move = moves.get(moveSelect - 1);
                    manager.applyMove(move, manager.getTeamOne().getActivePokemon(), manager.getTeamTwo().getActivePokemon());

                    //Display messages to the user
                    System.out.println(activePokemon.getNickname() + " used " + move.getName() + "!");
					System.out.println(activePokemon.getNickname() + " dealt " + (manager.getTargetHealth() - targetPokemon.getCurrentHp()) + " damage! \n");
					
					if (manager.getPlayerHealth() < activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " healed for " +
							Math.abs(manager.getPlayerHealth() - activePokemon.getCurrentHp()) + " damage! \n");
					}else if (manager.getPlayerHealth() > activePokemon.getCurrentHp()){
						System.out.println(activePokemon.getNickname() + " dealt " + 
							(manager.getPlayerHealth() - activePokemon.getCurrentHp()) + " to itself! \n");
					}

					//Change the state
					manager.setState(BattleState.PLAYER_TWO_MOVE);
                    break;
                case PLAYER_ONE_FAINTED: //When the user must switch Pokemon
                    List<Pokemon> pokemon = team.getPokemon();

                    if(!manager.teamOneHasUsable()) {
                        // if there is no usable Pokemon, Player two wins (CPU)
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
                            //The user can not choose a fainted Pokemon
                            System.out.println(pokemon.get(newPoke - 1).getNickname() + " has fainted and can not battle!");
                            break;
                        }
                        team.setActivePokemon(newPoke - 1);
                        manager.setState(BattleState.PLAYER_ONE_MOVE);
                    }catch(NumberFormatException e) {
                        break;
                    }
                    break;
                case PLAYER_ONE_VICTORY: //when playe rone has won

                    System.out.println("Congratulations, you've won!");

                    //level up all the pokemon
                    for(Pokemon p : team.getPokemon()) {
                        if(p == null) continue;
                        p.setHp(p.getMaxHp());
                        p.setLevel(p.getLevel() + 1);
                    }
                    gameEnd = true; //signal the end of the loop
                    break;
                case PLAYER_TWO_FAINTED: //when the user has defeated a cpu pokemon
                    List<Pokemon> cpuPoke = manager.getTeamTwo().getPokemon();
                    for(int i = 0; i < cpuPoke.size(); i++) {
                        if(!cpuPoke.get(i).isFainted()) {
                            //try to swap with the first usable Pokemon
                            manager.getTeamTwo().setActivePokemon(i);
                        }
                   }
                   //If team two has no usable Pokemon, then Player one wins
                    if(!manager.teamTwoHasUsable()) {
                        manager.setState(BattleState.PLAYER_ONE_VICTORY);
                    } else {
                        //otherwise, player two has swapped with a usable pokemon, and now it is his turn to mvoe.
                        manager.setState(BattleState.PLAYER_TWO_MOVE);
                    }
                    //switch with a random non fainted in the cpu
                    break;
                case PLAYER_TWO_MOVE: //when the cpu is to make a move
                    Pokemon cpuActivePokemon = manager.getTeamTwo().getActivePokemon();
					Pokemon playerTargetPokemon = manager.getTeamOne().getActivePokemon();

                    //check both pokemon are able to make moves and take damage.
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

                    //get a random move and apply it
                    List<PokemonMove> cpuMoves = cpuActivePokemon.getMoves();
                    PokemonMove cpuMove = cpuMoves.get(new Random().nextInt(cpuMoves.size()));
                    manager.applyMove(cpuMove, cpuActivePokemon, playerTargetPokemon);

                    //Display details to the user
                    System.out.println(cpuActivePokemon.getNickname() + " used " + cpuMove.getName() + "!");
					System.out.println(cpuActivePokemon.getNickname() + " dealt " + (manager.getTargetHealth() - playerTargetPokemon.getCurrentHp()) + " damage! \n");
					
					if (manager.getPlayerHealth() < cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " healed for " +
							Math.abs(manager.getPlayerHealth() - cpuActivePokemon.getCurrentHp()) + " damage! \n");
					}
					else if (manager.getPlayerHealth() > cpuActivePokemon.getCurrentHp()){
						System.out.println(cpuActivePokemon.getNickname() + " dealt " + 
							(manager.getPlayerHealth() - cpuActivePokemon.getCurrentHp()) + " to itself! \n");
					}
                    manager.setState(BattleState.PLAYER_ONE_MOVE);
                    break;
                case PLAYER_TWO_VICTORY: //when player two wins
                    System.out.println("You lost! Try again next time!");
                    for(Pokemon p : manager.getTeamOne().getPokemon()) {
                        //heal all the user's Pokemon.
                        if(p == null) continue;
                        p.setHp(p.getMaxHp());
                    }
                    gameEnd = true;
                    break;
            }

        } while (!gameEnd);
    }
}
