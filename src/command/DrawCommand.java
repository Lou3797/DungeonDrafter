package command;

public class DrawCommand implements Command {

    public DrawCommand() {

    }

    @Override
    public boolean execute() {

        return false;
    }

    @Override
    public boolean unexecute() {

        return false;
    }

    @Override
    public String getCommandName() {
        return "DrawCommand";
    }
}
