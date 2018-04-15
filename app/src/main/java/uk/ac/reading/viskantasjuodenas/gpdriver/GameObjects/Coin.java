package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;

public class Coin {

    private Bitmap image;
    private int x;
    private int y;

    Coin(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void increaseY(int value){
        y += value;
    }

    public boolean isInside(int startX, int startY, int endX, int endY){
        if (y + image.getHeight() > startY && y <endY && x + image.getWidth() > startX && x <endX) {
            return true;
        }
        return false;
    }

    public Bitmap getImage() {
        return image;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }
}
