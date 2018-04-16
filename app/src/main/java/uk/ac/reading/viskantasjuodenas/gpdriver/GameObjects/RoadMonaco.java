package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Narrow road, has walls, if driver hits one - game over
 */
public class RoadMonaco extends Road{

    private Rect[] walls;

    /**
     * Sets up all road value
     * @param displayWidth screen width
     * @param displayHeight screen height
     */
    public RoadMonaco(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        //Calculate road size and coordinates coordinates
        roadWidth = displayWidth - (int)(displayWidth/1.5);
        roadStartX = displayWidth/3;
        roadEndX = displayWidth - displayWidth/3;

        //Calculate road markers size
        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/11;

        //Set wall objects
        walls = new Rect[2];
        walls[0] = new Rect(roadStartX - 20, 0, roadStartX, displayHeight);
        walls[1] = new Rect(roadEndX, 0, roadEndX + 20, displayHeight);

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
     * Checks if the driver hit a wall
     * @param startX driver's object left side x
     * @param endX driver's object right side x
     * @return "Wall" or null
     */
    @Override
    public String checkRoadPosition(float startX, float endX) {
        if (startX < roadStartX || endX > roadEndX) {
            return "Wall";
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        //Walls
        paint.setColor(Color.BLACK);
        canvas.drawRect(walls[0], paint);
        canvas.drawRect(walls[1], paint);

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
        return "monaco";
    }
}
