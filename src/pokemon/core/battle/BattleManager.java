package pokemon.core.battle;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonSpecies;
import pokemon.data.PokemonTeam;
import serialization.srsf.Lazy;

import java.util.*;

/**
 * Manages state and damage for a battle.
 * @see pokemon.menu.BattleMenu
 */
public class BattleManager {

    private final static int LEVEL_RANGE = 1;

    /**
     * The current opponent's health (
     */
    private int targetHealth;
    /**
     * The current player's health
     */
    private int playerHealth;
    /**
     * The state of te battle
     */
    private BattleState state;
    /**
     * The first team of Pokemon
     */
    private final PokemonTeam teamOne;
    /**
     * The second team of Pokemon
     */
    private final PokemonTeam teamTwo;
    /**
     * A damage multiplier for types that are immune to the move type
     */
	private static final double IMMUNE = 0;
    /**
     * A damage multipler for types that are not effective to the move type
     */
	private static final double NOT_EFFECTIVE = 0.5;
    /**
     * A damage multipler for types that are super effective to the move type
     */
	private static final double SUPER_EFFECTIVE = 2;
    /**
     * The hardcoded name of a special, random damage move
     */
	private static final String SPECIAL_MOVE = "Pooh";
    /**
     * The damage modifier used in damage calculation
     */
    private static int DAMAGE_MODIFIER = 125;
    /**
     * The lower percentage bound of a Random percentage used in damage calculation.
     */
    private static int DAMAGE_BOUND = 85;
    /**
     * The amount of Pokemon a CPU team will have.
     */
    private static int CPU_POKEMON_AMOUNT = 3;

    /**
     * Instantiate a new Battle Manager
     * @param teamOne The player team
     * @param validSpecies Valid species to use for the CPU team
     * @param validMoves Valid moves to use for the CPU team
     */
    public BattleManager(PokemonTeam teamOne, List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves) {
        this(teamOne, BattleManager.getRandomTeam(validSpecies, validMoves, teamOne.getActivePokemon().getLevel() +
                new Random().nextInt(BattleManager.LEVEL_RANGE) + 1));
    }

    /**
     * Instantiate a Battle Manager with the given teams
     * @param teamOne The player team
     * @param teamTwo The CPU team
     */
    public BattleManager(PokemonTeam teamOne, PokemonTeam teamTwo) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.state = BattleState.PLAYER_ONE_MOVE;
    }

    /**
     * Gets a random CPU team
     * @param validSpecies The valid species the CPU can use
     * @param validMoves The valid moves the CPU can use
     * @param max The maximum level of the CPU's pokemon
     * @return A random team with the given parameters
     */
    private static PokemonTeam getRandomTeam(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves, int max) {
        return new PokemonTeam(getCpuPokemon(validSpecies, validMoves, max, CPU_POKEMON_AMOUNT));
    }

    /**
     * Gets four random moves
     * @param validMoves The valid moves that can be picked from
     * @return Four random moves
     */
    private static List<Lazy<PokemonMove>> getFourMoves(List<PokemonMove> validMoves) {
        List<PokemonMove> copy = new LinkedList<>(validMoves);
        Collections.shuffle(copy);
        return Lazy.asLazyList(copy.subList(0, 4));
    }

    /**
     * @param validSpecies The valid species the CPU can use
     * @param validMoves The valid moves the CPU can use
     * @param maxLevel The maximum level of the CPU's pokemon
     * @param maxAmount The maximum amount of Pokemon in the CPU's team
     * @return
     */
    private static ArrayList<Lazy<Pokemon>> getCpuPokemon(List<PokemonSpecies> validSpecies, List<PokemonMove> validMoves,
                                                          int maxLevel, int maxAmount) {
        List<PokemonSpecies> copy = new LinkedList<>(validSpecies);
        Collections.shuffle(copy);
        ArrayList<Lazy<Pokemon>> pokemon = new ArrayList<>(6);
        for (PokemonSpecies species : copy.subList(0, maxAmount)) {
            pokemon.add(Lazy.asLazy(new Pokemon(UUID.randomUUID().toString(),
                    Lazy.asLazy(species), getFourMoves(validMoves),
                    species.getName(), new Random().nextInt(maxLevel) + 1)));
        }
        return pokemon;
    }


    /**
     * Gets the health of the current target, before the last move was applied.
     * Changes as the state of the battle moves between the two players
     * @return The health of the current target
     */
    public int getTargetHealth() {
        return targetHealth;
    }

    /**
     * Gets the health of the currently moving player, before the last move was applied.
     * Changes as the state of the battle moves between the two players
     * @return The health of the current moving player.
     */
    public int getPlayerHealth() {
        return playerHealth;
    }


    /**
     * Gets the first player's (User)'s team
     * @return
     */
    public PokemonTeam getTeamOne() {
        return this.teamOne;
    }

    /**
     * Gets the second player's (CPU's team)
     * @return
     */
    public PokemonTeam getTeamTwo() {
        return this.teamTwo;
    }

    /**
     * Checks that teamOne has usable pokemon
     * @return Whether or not teamOne has  usable Pokemon
     */
    public boolean teamOneHasUsable() {
        boolean hasUsable = false;
        for(Pokemon p : this.getTeamOne().getPokemon()) {
            if(p == null) continue;
            if(!p.isFainted()){
                hasUsable = true;
                break;
            }
        }
        return hasUsable;
    }

    /**
     * Checks that teamOne has usable pokemon
     * @return Whether or not teamTwo has usable Pokemon
     */
    public boolean teamTwoHasUsable() {
        boolean hasUsable = false;
        for(Pokemon p : this.getTeamTwo().getPokemon()) {
            if(p == null) continue;
            if(!p.isFainted()){
                hasUsable = true;
                break;
            }
        }
        return hasUsable;
    }
    /**
     * Gets the current battle state
     * @return The current battle state
     */
    public BattleState getState() {
        return state;
    }

    /**
     * Sets anew battle state
     * @param state The new battle state
     */
    public void setState(BattleState state) {
        this.state = state;
    }

    /**
     * Applies the effects of a move to the initiating and target Pokemon
     * @param move The move to apply
     * @param moveInitiator The Pokemon that initiated the move
     * @param moveTarget THe target Pokemon
     */
    public void applyMove(PokemonMove move, Pokemon moveInitiator, Pokemon moveTarget) {

        this.targetHealth = moveTarget.getCurrentHp();
        this.playerHealth = moveInitiator.getCurrentHp();

        double multiplier = 1;
        //determine the multipler
        Random r = new Random();
        if (moveTarget.getSpecies().getPrimaryType().isImmuneAgainst(move.getType())) multiplier = IMMUNE;
        if (moveTarget.getSpecies().getPrimaryType().isStrongAgainst(move.getType())) multiplier = NOT_EFFECTIVE;
        if (moveTarget.getSpecies().getPrimaryType().isWeakAgainst(move.getType())) multiplier = SUPER_EFFECTIVE;

        //gets the move damage for target and self damage
        int targetDamage = getMoveDamage(move.getDamage(), moveInitiator.getLevel());
        int selfDamage = getMoveDamage(move.getSelfDamage(), moveInitiator.getLevel());

        //use random damage, if the move name is special
        if(move.getName().equalsIgnoreCase(SPECIAL_MOVE)) {
            targetDamage = getMoveDamage(new Random().nextInt(moveInitiator.getMaxHp() + 101),
				DAMAGE_MODIFIER);
			selfDamage = getMoveDamage(new Random().nextInt(moveInitiator.getMaxHp() + 101),
				DAMAGE_MODIFIER);
        }

        //apply the new HP values after damage calculation
        moveTarget.setHp(moveTarget.getCurrentHp() - (int) Math.round(targetDamage * multiplier));
        moveInitiator.setHp(moveInitiator.getCurrentHp() - selfDamage);
    }

    /**
     * Gets the move damage, given the base damage of the move, and the level of the move user
     * @param baseDamage The base damage of the move
     * @param level The level of the move
     * @return The damage the move will do
     */
    private static int getMoveDamage(int baseDamage, int level) {
        Random r = new Random();
        if(baseDamage == 0) return 0; //do not apply any effects if there is no base damage.
        return (int)(level + ((0.25 * DAMAGE_MODIFIER) / (double)DAMAGE_MODIFIER)
                * baseDamage * (r.nextInt(DAMAGE_BOUND) * 0.01));
    }
}
