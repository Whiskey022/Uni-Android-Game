package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;

public interface GameObject {
     void draw(Canvas canvas);
     void update(float value);
}
