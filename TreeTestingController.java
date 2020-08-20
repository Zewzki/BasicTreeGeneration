package control;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class TreeTestingController implements Runnable{

    public static final int SCREEN_WIDTH = 300;
    public static final int SCREEN_HEIGHT = 300;

    public static final int N_POINTS = 100;
    public static final int MAX_DIST = SCREEN_WIDTH / 7;

    private JFrame frame;
    private TreePanel panel;

    private boolean running;
    private Thread thread;

    private Random rand;

    private ArrayList<Point> pointList;
    private ArrayList<Pair<Pair<Integer, Integer>, Pair<Integer, Integer>>> edgeList;

    private int source;
    private boolean[] visited;

    public TreeTestingController() {

        rand = new Random();

        pointList = new ArrayList();
        edgeList = new ArrayList();

        for(int i = 0; i < N_POINTS; i++) {

            int x = rand.nextInt(SCREEN_WIDTH) + 50;
            int y = rand.nextInt(SCREEN_HEIGHT) + 50;
            pointList.add(new Point(x, y));

        }

        visited = new boolean[N_POINTS];
        source = -1;

    }

    public void start() {

        if(running) return;

        running = true;
        thread = new Thread(this);
        thread.start();

    }

    public void stop() {

        if(!running) return;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            System.err.println("Big Error");
        }

    }

    @Override
    public void run() {

        System.out.println("Running");

        double desiredFPS = 20;
        long initialTime = System.nanoTime();
        double timeF = 1000000000 / desiredFPS;
        double deltaF = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while(running) {

            long currentTime = System.nanoTime();
            deltaF += (currentTime - initialTime) / timeF;
            initialTime = currentTime;

            if(deltaF >= 1) {

                if(source < 0) {
                    System.out.println("Stopping");
                    stop();
                    return;
                }

                for(int i = 0 ; i < pointList.size(); i++) pointList.get(i).move();

                edgeList.clear();
                visited = new boolean[N_POINTS];

                dfs(source, pointList);

                panel.repaint();

                frames++;
                deltaF--;

            }

            if(System.currentTimeMillis() - timer > 1000) {
                System.out.println("FPS: " + frames);
                frames = 0;
                timer += 1000;
            }

        }

        System.out.println("Stopping");

        stop();

    }

    public void bfs(int source, ArrayList<Point> pointList) {

        Queue<Integer> q = new ArrayDeque<>();

        visited[source] = true;

        q.add(source);

        while(!q.isEmpty()) {

            int currSource = q.poll();
            Pair<Integer, Integer> currPos = pointList.get(currSource).getPosition();

            int x1 = currPos.left;
            int y1 = currPos.right;

            visited[currSource] = true;

            for(int i = 0; i < pointList.size(); i++) {

                if(visited[i]) continue;

                int x2 = pointList.get(i).getPosition().left;
                int y2 = pointList.get(i).getPosition().right;

                double dist = calcDistance(x1, y1, x2, y2);

                if(dist < MAX_DIST) {

                    edgeList.add(new Pair(new Pair(x1, y1), new Pair(x2, y2)));
                    //panel.repaint();
                    q.add(i);

                }

            }

        }

    }

    public void dfs(int source, ArrayList<Point> pointList) {

        visited[source] = true;

        int x1 = pointList.get(source).getPosition().left;
        int y1 = pointList.get(source).getPosition().right;

        for(int i = 0; i < pointList.size(); i++) {

            if(visited[i]) continue;

            int x2 = pointList.get(i).getPosition().left;
            int y2 = pointList.get(i).getPosition().right;

            double dist = calcDistance(x1, y1, x2, y2);

            if(dist <= MAX_DIST) {
                edgeList.add(new Pair(new Pair(x1, y1), new Pair(x2, y2)));
                //panel.repaint();
                dfs(i, pointList);
            }

        }

    }

    public static double calcDistance(int x1, int y1, int x2, int y2) { return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)); }

    public static void main(String[] args) {

        TreeTestingController ttc = new TreeTestingController();

        ttc.frame = new JFrame();
        ttc.panel = new TreePanel(N_POINTS, SCREEN_WIDTH, SCREEN_HEIGHT, ttc.pointList, ttc.edgeList);

        ttc.frame.setBounds(100, 100, SCREEN_WIDTH + 110, SCREEN_HEIGHT + 125);
        ttc.frame.setLocationRelativeTo(null);
        ttc.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ttc.frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

                int x1 = e.getX();
                int y1 = e.getY() - 28;

                double minDistance = Integer.MAX_VALUE;
                int minIndex = -1;

                for(int i = 0; i < N_POINTS; i++) {

                    int x2 = ttc.pointList.get(i).getPosition().left;
                    int y2 = ttc.pointList.get(i).getPosition().right;

                    double dist = calcDistance(x1, y1, x2, y2);

                    if(dist < minDistance) {
                        minDistance = dist;
                        minIndex = i;
                    }

                }

                ttc.visited = new boolean[N_POINTS];
                ttc.edgeList.clear();

                ttc.source = minIndex;
                ttc.panel.setSource(ttc.source);

                ttc.panel.repaint();
                ttc.start();
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        ttc.frame.add(ttc.panel);
        ttc.frame.setVisible(true);

        //ttc.start();

    }

}
