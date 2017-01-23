package menu.text;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Exposes a fluent API to build and execute a menu composed of menu options.
 */
public class MenuBuilder {

    /**
     * A list of registered options
     */
    private List<MenuOption> runnables;
    /**
     * Whether or not the menu is halted
     */
    private boolean halt = false;
    /**
     * The default exit option. Can be set using
     * the exit(MenuOption) method to display some type of
     * farewell message.
     */
    private MenuOption exit = new MenuOption("") {
        @Override
        public void run() {
            return;
        }
    };

    /**
     * A hard coded option that immediately halts execution of the menu,
     * either exiting the application or going back to the previous menu.
     */
    private final MenuOption haltOption = new MenuOption("Exit or go back.") {
        @Override
        public void run() {
            halt();
        }
    };

    /**
     * An error handler called when an uncaught exception occurs during execution of the menu
     */
    private ErrorHandler error = new ErrorHandler() {
        @Override
        public void handle(Exception e) {
            e.printStackTrace();
        }
    };

    /**
     * Instantiates a new menu builder
     */
    public MenuBuilder() {
        this.runnables = new ArrayList<>();
    }

    /**
     * Adds an option to the menu builder
     * @param option The menu option to add
     * @return The current MenuBuilder
     */
    public MenuBuilder option(MenuOption option){
        this.runnables.add(option);
        return this;
    }

    /**
     * Sets the option that is executed when the MenuBuilder is halted
     * @param exit The exit menu option to add
     * @return The current MenuBuilder
     */
    public MenuBuilder exit(MenuOption exit) {
        this.exit = exit;
        return this;
    }

    /**
     * Sets the error handler used for uncaught exceptions
     * @param errorHandler The error handler used for uncaught exceptions
     * @return The current menuBuidler
     */
    public MenuBuilder error(ErrorHandler errorHandler) {
        this.error = errorHandler;
        return this;
    }

    /**
     * Immediately halts execution of the menu.
     */
    public void halt() {
        this.halt = true;
    }

    /**
     * Returns a string representation of the current menu options
     * @return A string representation of the current menu options
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.runnables.size(); i++) {
            sb.append(String.format("[%1$d] %2$s", (i + 1), this.runnables.get(i).getOptionName()) + "\n");
        }
        return sb.toString();
    }

    /**
     * Starts and executes the main loop for this menu.
     */
    public void run() {
        this.halt = false; //reset halt status.
        Scanner scanner = new Scanner(System.in);
        this.runnables.add(haltOption); //Add the option to exit as the last option
        int selection;
        do {
            System.out.println("Select from the choices below.");
            System.out.print(this); //display the options
            try {
                selection = scanner.nextInt();
                scanner.nextLine();
            }catch (InputMismatchException e){ //options are selected by number
                System.out.println("Invalid choice! Please select from the menu options.");
                scanner.nextLine(); //clear the line
                selection = 0; //reset the selection
                continue;
            }
            if(selection > this.runnables.size() || selection == 0) { //ensure the selected option exists
                System.out.println("Invalid choice! Please select from the menu options.");
                continue;
            }
            try {
                if (selection > 0) this.runnables.get(selection - 1).run(); //run the selected option
            }catch (Exception e) {
                this.error.handle(e); //handle any errors
            }

            System.out.println();
        } while (!this.halt);
        this.runnables.remove(haltOption); //remove the halting option to prevent duplicates.
        this.exit.run(); //runs the exit finalizer.
    }


}