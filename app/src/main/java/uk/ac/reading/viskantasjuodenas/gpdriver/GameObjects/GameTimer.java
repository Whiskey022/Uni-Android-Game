package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.time.Instant;

import java.time.Duration;

public class GameTimer implements GameObject {

    private long startTime;
    private String timeText;
    private Paint paint;

    public GameTimer(){
        startTime = System.currentTimeMillis();
        updateText("0");
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    @Override
    public void update(float speed){
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        updateText(String.format("%d:%02d", minutes, seconds));
   }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(timeText, 400, 50, paint);
    }

    private void updateText(String text){
        timeText = text;
    }

    public int getMinutes(){
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
        return seconds / 60;
    }

    public int getSeconds(){
        long millis = System.currentTimeMillis() - startTime;
        return (int) (millis / 1000);
    }
}
