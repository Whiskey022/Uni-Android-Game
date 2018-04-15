package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Opponent implements GameObject{

    private Bitmap image;
    private float speed;
    private int x;
    private int y;
    private String steeringDirection = "None";

    Opponent(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void setSpeed(float playerSpeed){
        speed = playerSpeed - 1f;
    }

    public void setSteeringDirection(String steeringDirection){
        this.steeringDirection = steeringDirection;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    @Override
    public void update(float value) {
        y = (int)(y + value - speed);
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
