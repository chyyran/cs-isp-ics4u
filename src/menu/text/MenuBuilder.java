package menu.text;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuBuilder {

    private List<MenuOption> runnables;
    private boolean halt = false;
    private MenuOption exit = new MenuOption("Exit") {
        @Override
        public void run() {
            return;
        }
    };

    private MenuOption haltOption = new MenuOption("Exit or go back.") {
        @Override
        public void run() {
            halt();
        }
    };

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

    public void halt() {
        this.halt = true;
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
        this.runnables.add(haltOption); //monkeypatch in halter
        int selection;
        do {
            System.out.println("Select from the choices below.");
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

            System.out.println();
        } while (!this.halt);
        this.runnables.remove(haltOption); //remove halter
        this.exit.run();
    }


}