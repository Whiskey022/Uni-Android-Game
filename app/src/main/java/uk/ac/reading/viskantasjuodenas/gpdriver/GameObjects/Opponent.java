package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Opponent class
 */
public class Opponent implements GameObject{

    private Bitmap image;
    private float speed;
    private int x;
    private int y;
    private String steeringDirection = "None";

    /**
     *
     * @param image image for opponent
     * @param x opponent's x coordinate
     * @param y opponent's y coordiante
     */
    Opponent(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    /**
     * Set opponent's speed bases on player's speed
     * @param playerSpeed
     */
    public void setSpeed(float playerSpeed){
        speed = playerSpeed - 1f;
    }

    /**
     * Set opponent's steering direction
     * @param steeringDirection
     */
    public void setSteeringDirection(String steeringDirection){
        this.steeringDirection = steeringDirection;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    /**
     * Update opponent's x based on his steering direction, and y base on his and player's speed
     * @param value usually the player speed
     */
    @Override
    public void update(float value) {
        //Update y based on player and opponent speeds
        y = (int)(y + value - speed);

        //Update x based on direction
        switch (steeringDirection){
            case "None":
                break;
            case "Left":
                x -= 1;
                break;
            case "Right":
                x += 1;
                break;
        }
    }

    public int getY(){
        return y;
    }

    public int getX(){
        return x;
    }

    public String getSteeringDirection(){
        return steeringDirection;
    }

}
