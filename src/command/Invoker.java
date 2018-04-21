package command;

import java.util.Stack;

/**
 * A simple invoker that supports undo and redo commands.
 *
 * @author rothnj (Lou3797@github)
 * @version 2018.4.21
 */
public class Invoker {
    private Stack<Command> undo;
    private Stack<Command> redo;

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
        } else {
            return false;
        }
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