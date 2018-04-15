package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PointsText implements GameObject{

    private int points = 0;
    private int missed = 0;
    private String percentage = "100";
    private Paint paint;

    public PointsText(){
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    public void increasePoints(){
        points ++;
        calculatePercentage();
    }

    public void increaseMissed(){
        missed ++;
        calculatePercentage();
    }

    private void calculatePercentage(){
        percentage = String.valueOf(points*100/(points + missed));
    }

    public int getPoints(){
        return points;
    }

    public String getPercentage(){
        return percentage;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(String.valueOf(points), 50, 150, paint);
        canvas.drawText(percentage + " %", 50, 250, paint);
    }

    @Override
    public void update(float value) { }
}
