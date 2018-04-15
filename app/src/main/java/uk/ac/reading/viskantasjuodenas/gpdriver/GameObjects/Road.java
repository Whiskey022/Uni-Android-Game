package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Road implements GameObject{

    private int displayWidth;
    private int displayHeight;
    private int roadWidth;
    private int roadStartX;
    private int roadEndX;
    private Rect surface;
    private int markerWidth;
    private int markerHeight;
    private int markerGap;
    private Rect[] markers;
    private Paint paint;

    public Road(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        roadWidth = displayWidth - displayWidth/2;
        roadStartX = displayWidth/4;
        roadEndX = displayWidth - displayWidth/4;

        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/10;

        surface = new Rect( roadStartX, 0, roadEndX, displayHeight);

        markers = new Rect[displayHeight/(markerHeight+markerGap)+3];
        for (int i=0; i<markers.length; i++){
            markers[i] = new Rect(displayWidth/2-markerWidth/2, (markerHeight+markerGap)*i, displayWidth/2+markerWidth/2, (markerHeight+markerGap)*i+markerHeight);
        }

        paint = new Paint();
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.GRAY);
        canvas.drawRect(surface, paint);

        paint.setColor(Color.GREEN);
        for (Rect marker : markers) {
            canvas.drawRect(marker, paint);
        }
    }

    @Override
    public void update(float speed) {
        for (Rect marker : markers) {
            int markerTop = ((int)speed % displayHeight);
            marker.offset(0, markerTop);
            if (marker.top > displayHeight) {
                marker.offset(0, - displayHeight - markerHeight - markerGap);
            }
        }
    }

    public int getRoadStartX(){
        return roadStartX;
    }

    public int getRoadEndX(){
        return roadEndX;
    }
}
