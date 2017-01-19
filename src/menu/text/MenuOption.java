package menu.text;

public abstract class MenuOption {

    private final String optionName;

    protected MenuOption(String name) {
        this.optionName = name;
    }

    public abstract void run();

    public final String getOptionName() {
        return this.optionName;
    }
}
