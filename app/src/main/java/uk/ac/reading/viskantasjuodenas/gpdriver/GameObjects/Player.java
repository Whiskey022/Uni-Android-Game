package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Player implements GameObject {

    private Bitmap image;
    private float x;
    private float y;

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

    public Bitmap getImage(){
        return image;
    }
}
