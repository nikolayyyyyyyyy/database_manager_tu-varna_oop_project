package models.command;

import interfaces.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String... command) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
