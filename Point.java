package control;

import java.util.Random;

public class Point {

    private static final int MAX_VEL = 7;

    private Pair<Integer, Integer> position;
    private Pair<Integer, Integer> velocity;

    private static final int OFFSET = 50;

    private static final int MIN_W = 0 + OFFSET;
    private static final int MIN_H = 0 + OFFSET;
    private static final int MAX_W = 300 + OFFSET;
    private static final int MAX_H = 300 + OFFSET;

    public Point(int x, int y, int vX, int vY) {

        position = new Pair(x, y);
        velocity = new Pair(vX, vY);

    }

    public Point(int x, int y) {

        position = new Pair(x, y);

        Random rand = new Random();

        int vX = rand.nextInt(MAX_VEL);
        int vY = rand.nextInt(MAX_VEL);

        velocity = new Pair(vX, vY);

    }

    public void move() {

        int x = position.left;
        int y = position.right;

        x += velocity.left;
        y += velocity.right;

        if(x <= MIN_W || x >= MAX_W) {
            velocity.left *= -1;
        }

        if(y <= MIN_W || y >= MAX_W) {
            velocity.right *= -1;
        }

        position.left = x;
        position.right = y;

    }

    public Pair<Integer, Integer> getPosition() { return position; }
    public Pair<Integer, Integer> getVelocity() { return velocity; }

}
