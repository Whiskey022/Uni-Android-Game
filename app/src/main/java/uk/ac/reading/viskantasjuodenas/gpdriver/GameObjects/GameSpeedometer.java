package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class GameSpeedometer implements GameObject {

    private float kmSpeed;
    private String speedText;
    private Paint paint;

    public GameSpeedometer (float speed){
        this.kmSpeed = speed;
        updateSpeedText();
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    @Override
    public void update(float speed){
        this.kmSpeed = speed * 4;
        updateSpeedText();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(speedText, 50, 50, paint);
    }

    private void updateSpeedText(){
        speedText = String.format("%.2f", kmSpeed) + " km/h";
    }
}
