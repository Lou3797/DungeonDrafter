package command;

import java.util.Stack;

public class Invoker {
    Stack<Command> undo;
    Stack<Command> redo;

    public Invoker() {
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public boolean invoke(Command cmd) {
        undo.push(cmd);
        redo.clear();
        return cmd.execute();
    }

    public boolean undo() {
        if(undo.size() > 0) {
            Command cmd = undo.pop();
            redo.push(cmd);
            return cmd.unexecute();

        }
        else
            return false;
    }

    public boolean redo() {
        if(redo.size() > 0) {
            Command cmd = redo.pop();
            undo.push(cmd);
            return cmd.execute();
        } else {
            return false;
        }
    }
}