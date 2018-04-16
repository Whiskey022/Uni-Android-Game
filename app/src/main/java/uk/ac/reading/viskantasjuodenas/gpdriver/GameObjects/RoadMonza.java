package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Wide road, has gravel on the sides which slows the driver down
 */
public class RoadMonza extends Road{

    private Rect background;

    /**
     * Sets up all road value
     * @param displayWidth screen width
     * @param displayHeight screen height
     */
    public RoadMonza(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        //Calculate road size and coordinates coordinates
        roadWidth = displayWidth - displayWidth/2;
        roadStartX = displayWidth/4;
        roadEndX = displayWidth - displayWidth/4;

        //Calculate road markers size
        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/6;

        //Set background object
        background = new Rect(0, 0, displayWidth, displayHeight);

        //Set road surface object
        surface = new Rect( roadStartX, 0, roadEndX, displayHeight);

        //Set all road markers
        markers = new Rect[displayHeight/(markerHeight+markerGap)+3];
        for (int i=0; i<markers.length; i++){
            markers[i] = new Rect(displayWidth/2-markerWidth/2, (markerHeight+markerGap)*i, displayWidth/2+markerWidth/2, (markerHeight+markerGap)*i+markerHeight);
        }

        paint = new Paint();
    }

    /**
     * Checks if the driver is on gravel
     * @param startX driver's object left side x
     * @param endX driver's object right side x
     * @return "Gravel" or null
     */
    @Override
    public String checkRoadPosition(float startX, float endX) {
        if (startX < roadStartX - 10 || endX > roadEndX + 10) {
            return "Gravel";
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        //Background
        paint.setColor(Color.YELLOW);
        canvas.drawRect(background, paint);

        //Road surface
        paint.setColor(Color.GRAY);
        canvas.drawRect(surface, paint);

        //All markers
        paint.setColor(Color.WHITE);
        for (Rect marker : markers) {
            canvas.drawRect(marker, paint);
        }
    }

    @Override
    public String getRoadType(){
        return "monza";
    }
}
