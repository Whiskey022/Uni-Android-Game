package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * Class to display game time and stores it
 */
public class GameTimer implements GameObject {

    private long startTime;
    private String timeText;
    private Paint paint;

    /**
     * Starts the timer
     */
    public GameTimer(){
        startTime = System.currentTimeMillis();
        updateText("0");
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    /**
     * Updates the timer and its text
     * @param speed
     */
    @Override
    public void update(float speed){
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        updateText(String.format("%d:%02d", minutes, seconds));
   }

    /**
     * Draws time as text
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(timeText, 600, 50, paint);
    }

    /**
     * Updated time text
     * @param text new value
     */
    private void updateText(String text){
        timeText = text;
    }

    /**
     * Get minutes out of time
     * @return minutes
     */
    public int getMinutes(){
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        return seconds / 60;
    }

    /**
     * Get seconds out of time
     * @return seconds
     */
    public int getSeconds(){
        long millis = System.currentTimeMillis() - startTime;
        return (int) (millis / 1000);
    }
}
