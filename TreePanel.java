package control;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TreePanel extends JPanel {

    private static final int POINT_SIZE = 5;

    private int N_POINTS;
    private int sWidth;
    private int sHeight;

    private Random rand;
    private ArrayList<Point> drawPoints;
    private ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> drawEdges;
    private int source;

    public TreePanel(int N_POINTS, int sWidth, int sHeight, ArrayList<Point> drawPoints, ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> drawEdges) {

        setBackground(Color.WHITE);

        this.N_POINTS = N_POINTS;
        this.sWidth = sWidth;
        this.sHeight = sHeight;

        rand = new Random();
        this.drawPoints = drawPoints;
        source = -1;
        this.drawEdges = drawEdges;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < N_POINTS; i++) {

            if(i == source) g.setColor(Color.BLUE);
            else g.setColor(Color.BLACK);

            int x = drawPoints.get(i).getPosition().left;
            int y = drawPoints.get(i).getPosition().right;
            g.fillOval(x, y, 2 * POINT_SIZE, 2 * POINT_SIZE);

        }

        g.setColor(Color.RED);

        for(int i = 0; i < drawEdges.size(); i++) {

            Pair a = drawEdges.get(i).left;
            Pair b = drawEdges.get(i).right;

            g.drawLine((int) a.left + POINT_SIZE, (int) a.right + POINT_SIZE, (int) b.left + POINT_SIZE, (int) b.right + POINT_SIZE);

        }


    }

    public void addLine(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> newLine) { drawEdges.add(newLine); }

    public void setSource(int i) { source = i; }


}
