package pokemon.core.battle;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonTeam;

public class BattleManager {

    private BattleState state;
    private final PokemonTeam teamOne;
    private final PokemonTeam teamTwo;
	private static final double IMMUNE = 0;
	private static final double NOTEFFECTIVE = 0.5;
	private static final double SUPEREFFECTIVE = 2;
	private static final String SPECIAL_MOVE = "Pooh";
	
	

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
		double Bean = 0 
        double multiplier = 1;
		
		Random r = new Random();
        if (moveTarget.getSpecies().getPrimaryType().isImmuneAgainst(move.getType())) multiplier = IMMUNE;
        if (moveTarget.getSpecies().getPrimaryType().isStrongAgainst(move.getType())) multiplier = NOTEFFECTIVE;
        if (moveTarget.getSpecies().getPrimaryType().isWeakAgainst(move.getType())) multiplier = SUPEREFFECTIVE;
		
		if (move.getName.equals(SPECIAL_MOVE)){
			moveTarget.setHp(moveTarget.getCurrentHp() - r.nextInt(400) )
		}else{
			moveTarget.setHp(moveTarget.getCurrentHp() - (int) Math.round(move.getDamage() * multiplier * (r.nextDouble() + 1)));
		}
        moveInitiator.setHp(moveInitiator.getCurrentHp() - move.getSelfDamage()); //todo: do multiplier calc on self

    }
}
