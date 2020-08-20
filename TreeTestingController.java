package control;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

public class TreeTestingController implements Runnable{

    private JFrame frame;
    private TreePanel panel;

    private boolean running;
    private Thread thread;

    public TreeTestingController() {}

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

        int source = panel.getSource();

        System.out.println(source);

        if(source < 0) {
            System.out.println("Stopping");
            stop();
            return;
        }

        ArrayList<Pair<Integer, Integer>> pointList = panel.getPointList();
        bfs(source, pointList);

        panel.repaint();

        System.out.println("Stopping");

        stop();

    }

    public void bfs(int source, ArrayList<Pair<Integer, Integer>> pointList) {

        Queue<Integer> q = new ArrayDeque<>();

        boolean[] visited = panel.getVisited();

        visited[source] = true;

        q.add(source);

        while(!q.isEmpty()) {

            int currSource = q.poll();
            int x1 = pointList.get(currSource).left;
            int y1 = pointList.get(currSource).right;

            visited[currSource] = true;

            for(int i = 0; i < pointList.size(); i++) {

                if(visited[i]) continue;

                int x2 = pointList.get(i).left;
                int y2 = pointList.get(i).right;

                double dist = panel.calcDistance(x1, y1, x2, y2);

                if(dist < panel.MAX_DIST) {

                    panel.addLine(new Pair(new Pair(x1, y1), new Pair(x2, y2)));
                    panel.repaint();
                    q.add(i);

                }

            }

        }

    }

    public void dfs(int source, ArrayList<Pair<Integer, Integer>> pointList) {

        panel.setVisited(source);

        boolean[] visited = panel.getVisited();

        int x1 = pointList.get(source).left;
        int y1 = pointList.get(source).right;

        for(int i = 0; i < pointList.size(); i++) {

            if(visited[i]) continue;

            int x2 = pointList.get(i).left;
            int y2 = pointList.get(i).right;

            double dist = panel.calcDistance(x1, y1, x2, y2);

            if(dist <= panel.MAX_DIST) {
                panel.addLine(new Pair(new Pair(x1, y1), new Pair(x2, y2)));
                panel.repaint();
                dfs(i, pointList);
            }

        }

    }


    public static void main(String[] args) {

        TreeTestingController ttc = new TreeTestingController();

        ttc.frame = new JFrame();
        ttc.panel = new TreePanel();

        ttc.frame.setSize(300, 300);
        ttc.frame.setLocationRelativeTo(null);
        ttc.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ttc.frame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ttc.panel.click(e.getX(), e.getY() - 28);
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

        ttc.start();

    }

}
