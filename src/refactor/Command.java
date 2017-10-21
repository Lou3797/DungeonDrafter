package refactor;

public interface Command {

    boolean execute();

    boolean unexecute();

    String getCommandName();

}