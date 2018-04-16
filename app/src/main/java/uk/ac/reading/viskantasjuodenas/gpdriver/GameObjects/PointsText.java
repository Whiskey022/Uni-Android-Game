package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Text object that stores and displays points (collected coins and percentage)
 */
public class PointsText implements GameObject{

    private int points = 0;
    private int missed = 0;
    private String percentage = "100";
    private Paint paint;

    /**
     * Conrsturctor sets paint and text size
     */
    public PointsText(){
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    /**
     * Increase points by 1
     */
    public void increasePoints(){
        points ++;
        calculatePercentage();
    }

    /**
     * Increase missed count by one
     */
    public void increaseMissed(){
        missed ++;
        calculatePercentage();
    }

    /**
     * Calculate points/missed percentage
     */
    private void calculatePercentage(){
        percentage = String.valueOf(points*100/(points + missed));
    }

    public int getPoints(){
        return points;
    }

    public String getPercentage(){
        return percentage;
    }

    /**
     * Draw text for points collected and percentage
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(points), 50, 150, paint);
        canvas.drawText(percentage + " %", 50, 250, paint);
    }

    @Override
    public void update(float value) { }
}
