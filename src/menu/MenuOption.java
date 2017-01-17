public abstract class MenuOption {

    protected Object[] params;
    private final String optionName;
    protected MenuOption(String name, Object... params) {
        this.optionName = name;
        this.params = params;
    }

    public abstract void run();

    public final String getOptionName() {
        return this.optionName;
    }
}
