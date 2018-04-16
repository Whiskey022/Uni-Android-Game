package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.graphics.Canvas;
import android.view.SurfaceHolder;


/**
 * Thread class that controls GamePanel
 */
public class MainThread extends Thread {
    public static final int MAX_FPS = 30;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    public static Canvas canvas;

    /**
     * Pass surfaceHolder context and GamePanel context
     * @param surfaceHolder
     * @param gamePanel
     */
    public MainThread(SurfaceHolder surfaceHolder, GamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    /**
     * Change threads state
     * @param running boolean
     */
    public void setRunning(boolean running) {
        this.running = running;
    }

    //run Method keeps trying to draw and update GamePanel if the thread is running
    @Override
    public void run(){
        long startTime;
        long timeMillis;
        long waitTime;
        long targetTime = 1000/MAX_FPS;

        //Only if the thread is set to running
        while(running) {
            //Get current time
            startTime = System.nanoTime();
            canvas = null;

            try {
                //Retrieve canvas
                canvas = this.surfaceHolder.lockCanvas();
                //Update and draw gamePanel in synchronization
                synchronized (surfaceHolder) {
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null) {
                    try {
                        //After updating and drawing, send back the new canvas
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Calculate waitTime
            timeMillis = (System.nanoTime()) - startTime/1000000;
            waitTime = targetTime - timeMillis;

            //If the game is waiting, doing nothing, set thread to sleeping
            try {
                if (waitTime > 0) {
                    this.sleep(waitTime);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
