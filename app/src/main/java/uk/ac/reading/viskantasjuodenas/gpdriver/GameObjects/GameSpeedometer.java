package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class to display and store speed of the player
 */
public class GameSpeedometer implements GameObject {

    private float kmSpeed;
    private String speedText;
    private Paint paint;

    /**
     * Sets up speed to display
     * @param speed player's current speed
     */
    public GameSpeedometer (float speed){
        this.kmSpeed = speed;
        updateSpeedText();
        paint = new Paint(Color.RED);
        paint.setTextSize(paint.getTextSize() * 3);
    }

    /**
     * Updates km/h speed and text to display
     * @param speed
     */
    @Override
    public void update(float speed){
        this.kmSpeed = speed * 4;
        updateSpeedText();
    }

    /**
     * Draws speed as text
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        canvas.drawText(speedText, 50, 50, paint);
    }

    /**
     * Update speed text with new kmSpeed value
     */
    private void updateSpeedText(){
        speedText = String.format("%.2f", kmSpeed) + " km/h";
    }
}
