package command;

/**
 * The interface for a basic command pattern command.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.21
 * @since 4/21/2018
 */
public interface Command {

    boolean execute();

    boolean unexecute();

    String getCommandName();

}