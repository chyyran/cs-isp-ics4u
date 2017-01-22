package pokemon.core.battle;

/**
 * A list of possible states in a Pokemon battle
 */
public enum BattleState {
    /**
     * Player one is to make a move, or is making a move
     */
    PLAYER_ONE_MOVE,
    /**
     * Player two is to make a move, or is making a move
     */
    PLAYER_TWO_MOVE,
    /**
     * Player one is fainted, or for whatever other reason must choose a new
     * Pokemon to use in battle.
     */
    PLAYER_ONE_FAINTED,
    /**
     * Player two is fainted, or for whatever other reason must choose a new
     * Pokemon to use in battle.
     */
    PLAYER_TWO_FAINTED,
    /**
     * Player one has won the battle
     */
    PLAYER_ONE_VICTORY,
    /**
     * Player two has won the battle.
     */
    PLAYER_TWO_VICTORY
}
