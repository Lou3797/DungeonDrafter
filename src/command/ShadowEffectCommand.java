package command;

import javafx.scene.canvas.Canvas;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

public class ShadowEffectCommand implements Command {
    private Canvas parent;
    private double radius;

    public ShadowEffectCommand(Canvas parent, double radius) {
        this.parent = parent;
        this.radius = radius;
    }

    @Override
    public boolean execute() {
        parent.setEffect(new DropShadow(this.radius, Color.BLACK));
        return true;
    }

    @Override
    public boolean unexecute() {
        parent.setEffect(null);
        return false;
    }

    @Override
    public String getCommandName() {
        return "Effect command";
    }
}
