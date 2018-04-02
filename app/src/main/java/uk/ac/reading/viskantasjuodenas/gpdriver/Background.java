package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Background implements GameObject {

    private Bitmap image;

    public Background(Bitmap image){
        this.image = image;
    }

    @Override
    public void draw(Canvas canvas){
        canvas.drawBitmap(image, -100, -100, null);
    }

    public void update(float degrees, float widthCenter, float heightCenter, Bitmap source) {
        Matrix matrix = new Matrix();
        matrix.setTranslate(widthCenter, heightCenter);
        matrix.postRotate(degrees, widthCenter, heightCenter);
        image = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, false);
    }

}
