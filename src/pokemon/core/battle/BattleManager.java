package pokemon.core.battle;

import pokemon.data.Pokemon;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonTeam;

public class BattleManager {

    private BattleState state;
    private final PokemonTeam teamOne;
    private final PokemonTeam teamTwo;

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
        if (moveTarget.getSpecies().getPrimaryType().isImmuneAgainst(move.getType())) multiplier = 0;
        if (moveTarget.getSpecies().getPrimaryType().isStrongAgainst(move.getType())) multiplier = 0.5;
        if (moveTarget.getSpecies().getPrimaryType().isWeakAgainst(move.getType())) multiplier = 2;
        moveTarget.setHp(moveTarget.getCurrentHp() - (int) Math.round(move.getDamage() * multiplier));
        moveInitiator.setHp(moveInitiator.getCurrentHp() - move.getSelfDamage()); //todo: do multiplier calc on self

    }
}
