import java.awt.Color;
import java.awt.*;
import java.awt.geom.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Iterator;

/**
 * Class BallDemo - a short demonstration showing animation with the 
 * Canvas class. 
 *
 * @author Bill Crosbie
 * @version 2015-March-BB
 *
 * @author Michael Kölling and David J. Barnes
 * @version 2011.07.31
 * 
 * @author Ben Adelson
 * @version 2019.10.13
 */

public class BallDemo   
{
    private Canvas myCanvas;

    /**
     * Create a BallDemo object. Creates a fresh canvas and makes it visible.
     */
    public BallDemo()
    {
        myCanvas = new Canvas("Ball Demo", 600, 500);
    }

    /**
     * Simulate two bouncing balls
     */
    public void bounce()
    {
        int ground = 400;   // position of the ground line

        myCanvas.setVisible(true);

        // draw the ground
        myCanvas.drawLine(50, ground, 550, ground);

        // crate and show the balls
        BouncingBall ball = new BouncingBall(50, 50, 16, Color.BLUE, ground, myCanvas);
        ball.draw();
        BouncingBall ball2 = new BouncingBall(70, 80, 20, Color.RED, ground, myCanvas);
        ball2.draw();

        // make them bounce
        boolean finished =  false;
        while(!finished) {
            myCanvas.wait(50);           // small delay
            ball.move();
            ball2.move();
            // stop once ball has travelled a certain distance on x axis
            if(ball.getXPosition() >= 550 || ball2.getXPosition() >= 550) {
                finished = true;
            }
        }
    }
    
    /**
     * Simulates bouncing balls in a box
     */
    
    public void boxBounce(int numberOfBalls)
    {
        myCanvas.setVisible(true);
        // draw the box
        Rectangle box = new Rectangle(50,50,400,400);
        myCanvas.draw(box);
        // crate and show the balls
        Random random = new Random();
        HashSet<BoxBall> balls = new HashSet<BoxBall>();
        for(int i=0; i<numberOfBalls; i++) {
            Dimension size = myCanvas.getSize();
            int x = (int) box.getX() + random.nextInt((int) box.getWidth() - 16);            
            int y = (int) box.getY() + random.nextInt((int) box.getHeight() - 16);
            int xSpeed = random.nextInt(30);
            int ySpeed = random.nextInt(30);
            Color colorMixed = new Color(random.nextInt(256),random.nextInt(256),random.nextInt(256));
            BoxBall ball = new BoxBall(x, y, xSpeed, ySpeed, 16, box, myCanvas,colorMixed);
            balls.add(ball);
            ball.draw();
        }
        // make them bounce        
        boolean finished = false;
        while(!finished) {
            myCanvas.wait(40);           // small delay
            Iterator<BoxBall> it = balls.iterator();
            
            finished = true;
            while(it.hasNext()) {
                BoxBall ball = it.next();
                ball.move();
                // stop only once all balls has stopped moving
                if(ball.isMoving()) {
                    finished = false;
                }
            }
        }
        Iterator<BoxBall> it = balls.iterator();
        while(it.hasNext()) {
            BoxBall ball = it.next();
            ball.erase();
        }
    } 
}
