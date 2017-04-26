package mainui.DELmapeditor;

public class DrawPen {
    private int size;
    private double[] xPoints;
    private double[] yPoints;


    public DrawPen() {
        size = 0;
        xPoints = new double[size];
        yPoints = new double[size];
    }

    public void addPoint(double x, double y) {
        size++;
        double[] xTemp = new double[size];
        double[] yTemp = new double[size];
        System.arraycopy(xPoints, 0, xTemp, 0, xPoints.length);
        System.arraycopy(yPoints, 0, yTemp, 0, yPoints.length);
        xTemp[size-1] = x;
        yTemp[size-1] = y;
        xPoints = xTemp;
        yPoints = yTemp;
    }


    public double[] getxPoints() {
        return xPoints;
    }

    public double[] getyPoints() {
        return yPoints;
    }

    public void clear() {
        size = 0;
        xPoints = new double[size];
        yPoints = new double[size];
    }

    public int getSize() {
        return size;
    }
}

