package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Abu Dhabi road, wide, nothing happens if the driver leaves the track
 */
public class RoadAbuDhabi extends Road{

    private Rect background;

    /**
     * Sets up all road value
     * @param displayWidth screen width
     * @param displayHeight screen height
     */
    public RoadAbuDhabi(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        //Calculate road size and coordinates coordinates
        roadWidth = displayWidth - displayWidth/2;
        roadStartX = displayWidth/4;
        roadEndX = displayWidth - displayWidth/4;

        //Calculate road markers size
        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/10;

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
     * Nothing happens if the driver leaves the track
     * @param startX driver's object left side x
     * @param endX driver's object right side x
     * @return null
     */
    @Override
    public String checkRoadPosition(float startX, float endX) {
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        //Background
        paint.setColor(Color.BLUE);
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
        return "abuDhabi";
    }
}
