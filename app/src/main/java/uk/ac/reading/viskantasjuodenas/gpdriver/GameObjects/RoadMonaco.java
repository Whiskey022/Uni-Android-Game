package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RoadMonaco extends Road{

    private Rect[] walls;

    public RoadMonaco(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        roadWidth = displayWidth - (int)(displayWidth/1.5);
        roadStartX = displayWidth/3;
        roadEndX = displayWidth - displayWidth/3;

        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/11;

        walls = new Rect[2];
        walls[0] = new Rect(roadStartX - 20, 0, roadStartX, displayHeight);
        walls[1] = new Rect(roadEndX, 0, roadEndX + 20, displayHeight);

        surface = new Rect( roadStartX, 0, roadEndX, displayHeight);

        markers = new Rect[displayHeight/(markerHeight+markerGap)+3];
        for (int i=0; i<markers.length; i++){
            markers[i] = new Rect(displayWidth/2-markerWidth/2, (markerHeight+markerGap)*i, displayWidth/2+markerWidth/2, (markerHeight+markerGap)*i+markerHeight);
        }

        paint = new Paint();
    }

    @Override
    public String checkRoadPosition(float startX, float endX) {
        if (startX < roadStartX || endX > roadEndX) {
            return "Wall";
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawRect(walls[0], paint);
        canvas.drawRect(walls[1], paint);

        paint.setColor(Color.GRAY);
        canvas.drawRect(surface, paint);

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
