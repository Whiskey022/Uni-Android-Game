package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Canvas;

/**
 * Interface for game objects
 */
public interface GameObject {

     /**
      * Draw object on canvas
      * @param canvas
      */
     void draw(Canvas canvas);

     /**
      * Update object values
      * @param value usually the player speed
      */
     void update(float value);
}
