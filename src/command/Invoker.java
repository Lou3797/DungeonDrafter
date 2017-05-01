package command;

import java.util.Stack;

public class Invoker {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public Invoker() {
        undoStack = new Stack<>();
        redoStack = new Stack<>();
    }

    /**
     * Executes the given command and adds it to the undo stack.
     * @param cmd The command to be executed
     * @return The return value of the execution
     */
    public boolean invoke(Command cmd) {
        undoStack.push(cmd);
        redoStack.clear();
        return cmd.execute();
    }

    public boolean undo() {
        if(undoStack.size() > 0) {
            Command cmd = undoStack.pop();
            redoStack.push(cmd);
            return cmd.unexecute();

        }
        else
            return false;
    }

    public boolean redo() {
        if(redoStack.size() > 0) {
            Command cmd = redoStack.pop();
            undoStack.push(cmd);
            return cmd.execute();
        } else {
            return false;
        }
    }
}