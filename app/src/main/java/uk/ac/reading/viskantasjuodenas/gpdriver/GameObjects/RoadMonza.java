package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class RoadMonza extends Road{

    private Rect background;

    public RoadMonza(int displayWidth, int displayHeight){
        this.displayWidth = displayWidth;
        this.displayHeight = displayHeight;

        roadWidth = displayWidth - displayWidth/2;
        roadStartX = displayWidth/4;
        roadEndX = displayWidth - displayWidth/4;

        markerWidth = displayWidth/50;
        markerHeight = displayHeight/15;
        markerGap = displayHeight/6;

        background = new Rect(0, 0, displayWidth, displayHeight);

        surface = new Rect( roadStartX, 0, roadEndX, displayHeight);

        markers = new Rect[displayHeight/(markerHeight+markerGap)+3];
        for (int i=0; i<markers.length; i++){
            markers[i] = new Rect(displayWidth/2-markerWidth/2, (markerHeight+markerGap)*i, displayWidth/2+markerWidth/2, (markerHeight+markerGap)*i+markerHeight);
        }

        paint = new Paint();
    }

    @Override
    public String checkRoadPosition(float startX, float endX) {
        if (startX < roadStartX - 10 || endX > roadEndX + 10) {
            return "Gravel";
        }
        return null;
    }

    @Override
    public void draw(Canvas canvas) {
        paint.setColor(Color.YELLOW);
        canvas.drawRect(background, paint);

        paint.setColor(Color.GRAY);
        canvas.drawRect(surface, paint);

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
