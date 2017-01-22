package pokemon.core.battle;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonTeam;

import java.util.Random;

public class BattleManager {

    private BattleState state;
    private final PokemonTeam teamOne;
    private final PokemonTeam teamTwo;
	private static final double IMMUNE = 0;
	private static final double NOTEFFECTIVE = 0.5;
	private static final double SUPEREFFECTIVE = 2;
	private static final String SPECIAL_MOVE = "Pooh";
    private static int DAMAGE_MODIFIER = 125;
    private static int DAMAGE_BOUND = 85;

    public BattleManager(PokemonTeam teamOne, PokemonTeam teamTwo) {
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.state = BattleState.PLAYER_ONE_MOVE;
    }

    public BattleState getState() {
        return state;
    }

    public void setState(BattleState state) {
        this.state = state;
    }
    public void applyMove(PokemonMove move, Pokemon moveInitiator, Pokemon moveTarget) {
        double multiplier = 1;
		
		Random r = new Random();
        if (moveTarget.getSpecies().getPrimaryType().isImmuneAgainst(move.getType())) multiplier = IMMUNE;
        if (moveTarget.getSpecies().getPrimaryType().isStrongAgainst(move.getType())) multiplier = NOTEFFECTIVE;
        if (moveTarget.getSpecies().getPrimaryType().isWeakAgainst(move.getType())) multiplier = SUPEREFFECTIVE;
		

        int realDamage = getMoveDamage(move.getDamage(), moveInitiator.getLevel());
        int selfDamage = getMoveDamage(move.getSelfDamage(), moveInitiator.getLevel());
        if(move.getName().equalsIgnoreCase(SPECIAL_MOVE)) {
            realDamage = getMoveDamage(new Random().nextInt(moveInitiator.getMaxHp() + 101),
				DAMAGE_MODIFIER);
			selfDamage = getMoveDamage(new Random().nextInt(moveInitiator.getMaxHp() + 101),
				DAMAGE_MODIFIER);
					
        }
        moveTarget.setHp(moveTarget.getCurrentHp() - (int) Math.round(realDamage * multiplier));
        moveInitiator.setHp(moveInitiator.getCurrentHp() - selfDamage); //todo: do multiplier calc on self
    }
    private static int getMoveDamage(int baseDamage, int level) {
        Random r = new Random();
        return (int)(level + ((0.25 * DAMAGE_MODIFIER) / (double)DAMAGE_MODIFIER) * baseDamage * (r.nextInt(DAMAGE_BOUND) * 0.01));
    }
}
