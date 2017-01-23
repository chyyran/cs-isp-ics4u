package menu.text;

/**
 * Represents an object in a menu.
 */
public abstract class MenuOption {

    /**
     * The name of the option to be displayed to the user
     */
    private final String optionName;

    /**
     * Instantiates the menu option with the given name
     * @param name The name of the option to be displayed to the user
     */
    protected MenuOption(String name) {
        this.optionName = name;
    }

    /**
     * Executes the menu option
     */
    public abstract void run();

    /**
     * Gets the name of the option
     * @return The name of the option
     */
    public final String getOptionName() {
        return this.optionName;
    }
}
