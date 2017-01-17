import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by chyyran on 11/25/16.
 */
public class MenuBuilder {

    private List<MenuOption> runnables;
    private MenuOption exit = new GoodbyeOption("Goodbye");
    private ErrorHandler error = new ErrorHandler() {
        @Override
        public void handle(Exception e) {
            e.printStackTrace();
        }
    };

    public MenuBuilder() {
        this.runnables = new ArrayList<>();
    }

    public MenuBuilder option(MenuOption m){
        this.runnables.add(m);
        return this;
    }

    public MenuBuilder exit(MenuOption r) {
        this.exit = r;
        return this;
    }

    public MenuBuilder error(ErrorHandler r) {
        this.error = r;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.runnables.size(); i++) {
            sb.append(String.format("[%1$d] %2$s", (i + 1), this.runnables.get(i).getOptionName()) + "\n");
        }
        return sb.toString();
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int selection;
        do {
            System.out.println("Select from the choices below. Enter 0 or less than to exit.");
            System.out.print(this);
            selection = scanner.nextInt();
            scanner.nextLine();
            if(selection > this.runnables.size()) {
                System.out.println("Invalid choice! Please select from the menu options.");
                continue;
            }
            try {
                if (selection > 0) this.runnables.get(selection - 1).run();
            }catch (Exception e) {
                this.error.handle(e);
            }
            System.out.print("Press enter to continue.");
            scanner.nextLine();
            System.out.println();
        } while(selection > 0);
        this.exit.run();
    }


}