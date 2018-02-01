package command;

public interface Command {

    boolean execute();

    boolean unexecute();

    String getCommandName();

}