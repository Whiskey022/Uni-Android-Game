package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


/**
 * Abstract Road class, draws itself and check if driver drove outside the track
 */
public abstract class Road implements GameObject{

    protected int displayWidth;
    protected int displayHeight;
    protected int roadWidth;
    protected int roadStartX;
    protected int roadEndX;
    protected Rect surface;
    protected int markerWidth;
    protected int markerHeight;
    protected int markerGap;
    protected Rect[] markers;
    protected Paint paint;

    /**
     * Check if the driver is outside the track
     * @param startX driver's object left side x
     * @param endX driver's object right side x
     * @return String if the driver drove outside the track
     */
    abstract public String checkRoadPosition(float startX, float endX);

    /**
     * Draws road markers, the road itself, background colour
     * @param canvas
     */
    @Override
    public abstract void draw(Canvas canvas);

    /**
     * Draws road markers, and the road itself
     * @param speed
     */
    @Override
    public void update(float speed){
        for (Rect marker : markers) {
            int markerTop = ((int)speed % displayHeight);
            marker.offset(0, markerTop);
            if (marker.top > displayHeight) {
                marker.offset(0, - displayHeight - markerHeight - markerGap);
            }
        }
    }

    public abstract String getRoadType();

    public int getRoadStartX(){
        return roadStartX;
    }

    public int getRoadEndX(){
        return roadEndX;
    }
}
