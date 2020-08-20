package control;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class TreePanel extends JPanel {

    private static final int sWidth = 300;
    private static final int sHeight = 300;

    public static final int N_POINTS = 100;
    public static final int MAX_DIST = sWidth / 3;

    private Random rand;
    private ArrayList<Pair<Integer, Integer>> pointList;
    private int source;
    private ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> lineList;
    private boolean[] visited;

    public TreePanel() {

        setBackground(Color.WHITE);

        rand = new Random();
        pointList = new ArrayList();
        source = -1;
        lineList = new ArrayList();
        visited = new boolean[N_POINTS];

        for(int i = 0; i < N_POINTS; i++) {

            int x = (int) (rand.nextGaussian() * sWidth / 2) + sWidth * 2;
            int y = (int) (rand.nextGaussian() * sHeight / 2) + sHeight * 2;
            pointList.add(new Pair(x, y));

        }

    }

    public void click(int x1, int y1) {

        double minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for(int i = 0; i < N_POINTS; i++) {

            int x2 = pointList.get(i).left;
            int y2 = pointList.get(i).right;

            double dist = calcDistance(x1, y1, x2, y2);

            if(dist < minDistance) {
                minDistance = dist;
                minIndex = i;
            }

        }

        visited = new boolean[N_POINTS];
        lineList.clear();

        source = minIndex;

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(int i = 0; i < N_POINTS; i++) {

            if(i == source) g.setColor(Color.BLUE);
            else g.setColor(Color.BLACK);

            int x = pointList.get(i).left;
            int y = pointList.get(i).right;
            g.fillOval(x, y, 10, 10);

        }

        g.setColor(Color.RED);

        for(int i = 0; i < lineList.size(); i++) {

            Pair a = lineList.get(i).left;
            Pair b = lineList.get(i).right;

            g.drawLine((int) a.left, (int) a.right, (int) b.left, (int) b.right);

        }


    }

    public double calcDistance(int x1, int y1, int x2, int y2) { return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); }

    public void addLine(Pair<Pair<Integer, Integer>, Pair<Integer, Integer>> newLine) { lineList.add(newLine); }

    public int getSource() { return source; }

    public ArrayList<Pair<Integer, Integer>> getPointList() { return pointList; }

    public boolean[] getVisited() { return visited; }
    public void setVisited(int i) { visited[i] = true; }


}
