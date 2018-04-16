package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;

/**
 * Coin class that stores it's current coordinates
 */
public class Coin {

    private Bitmap image;
    private int x;
    private int y;

    /**
     *
     * @param image image for Coin
     * @param x coordinate
     * @param y coordinate
     */
    Coin(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    /**
     * Increase y coordinate based on speed
     * @param value player speed
     */
    public void increaseY(int value){
        y += value;
    }

    /**
     * Checks if the coin is in between coordinates
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return boolean
     */
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
