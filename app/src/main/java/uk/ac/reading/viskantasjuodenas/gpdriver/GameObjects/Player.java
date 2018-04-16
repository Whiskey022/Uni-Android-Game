package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Player object, controls the player coordinates
 */
public class Player implements GameObject {

    private Bitmap image;
    private float x;
    private float y;

    /**
     *
     * @param image player's image
     * @param x player's starting x coordinate
     * @param y player's starting y coordinate
     */
    public Player(Bitmap image, float x, float y) {
        this.image = image;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    @Override
    public void update(float value) {
        this.x = value;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public Bitmap getImage(){
        return image;
    }
}
