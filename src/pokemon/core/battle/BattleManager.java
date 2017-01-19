package pokemon.core.battle;

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
}
