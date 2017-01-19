package pokemon.menu;

import menu.text.MenuBuilder;
import menu.text.MenuOption;
import pokemon.data.PokemonMove;
import pokemon.data.PokemonTeam;
import serialization.srsf.Lazy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TeamMenu extends MenuOption {
    private PokemonTeam team;
    private final MenuBuilder teamMenu;

    public TeamMenu(final PokemonTeam _team, final List<Lazy<PokemonMove>> moves) {
        super("Team Manager");
        this.team = _team;
        this.teamMenu = new MenuBuilder();
        this.teamMenu
                .option(new MenuOption("Make or Edit Team") {
                    @Override
                    public void run() {
                        int selection = 0;
                        do {
                            if (team == null) team = new PokemonTeam();
                            System.out.println(team);
                            System.out.println("Pick a slot.");
                            //this should ideally be done with a MenuOption, but in the interest of time.
                        } while (selection > 0);
                    }
                })
                .option(new MenuOption("View Team") {
                    @Override
                    public void run() {
                        if (team != null) {
                            System.out.print(team);
                        } else {
                            System.out.print("You don't have a team!");
                        }

                    }
                });
    }

    private static List<Lazy<PokemonMove>> getFourMoves(List<Lazy<PokemonMove>> validMoves) {
        List<Lazy<PokemonMove>> copy = new LinkedList<>(validMoves);
        Collections.shuffle(copy);
        return copy.subList(0, 4);
    }

    @Override
    public void run() {

    }
}
