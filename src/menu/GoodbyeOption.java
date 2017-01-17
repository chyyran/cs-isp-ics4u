/**
 * Created by ronny on 2017-01-07.
 */
public class GoodbyeOption extends MenuOption {

    private final String message;
    public GoodbyeOption(String message) {
        super("Exit");
        this.message = message;
    }
    public void run() {
        System.out.print(message);
    }
}
