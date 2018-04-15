package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

public class CoinsManager implements GameObject{

    private Bitmap coinImage;
    private int minWidth;
    private int maxWidth;
    private int displayHeight;
    private int gap;
    private ArrayList<Coin> coinsList;
    private ArrayList<Integer> coinHistoryX;
    private Coin coin;
    private int distanceTravelled = 0;
    private int previousX;
    private int carStartX = 0;
    private int carEndX = 0;
    private int carStartY = 0;
    private int carEndY = 0;
    private PointsText pointsText;

    public CoinsManager(Bitmap coinImage, int minWidth, int maxWidth, int displayHeight, int gap, PointsText pointsText){
        this.coinImage = coinImage;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.displayHeight = displayHeight;
        this.gap = gap;
        this.pointsText = pointsText;

        previousX = (maxWidth - minWidth)/2;
        coinsList = new ArrayList<>();
        coinHistoryX = new ArrayList<>();
    }

    private void generateCoin(){
        int newX = previousX;

        switch (new Random().nextInt(3)){
            case 0:
                break;
            case 1:
                if (newX - 70 < minWidth) {
                    newX = minWidth;
                } else {
                    newX -= 70;
                }
                break;
            case 2:
                if (newX + 70 > maxWidth - coinImage.getWidth()){
                    newX = maxWidth - coinImage.getWidth();
                } else {
                    newX += 70;
                }
                break;
        }

        coin = new Coin(coinImage, newX, 0);
        coinHistoryX.add(newX);
        previousX = newX;
    }

    public void updateCollectionField(int startX, int startY, int endX, int endY){
        carStartX = startX;
        carStartY = startY;
        carEndX = endX;
        carEndY = endY;
    }

    @Override
    public void draw(Canvas canvas) {
        for (Coin coin: coinsList){
            Paint paint = new Paint();
            canvas.drawBitmap(coin.getImage(), coin.getX(), coin.getY(), paint);
        }
    }

    @Override
    public void update(float value) {
        distanceTravelled += value;

        if (distanceTravelled > gap){
            generateCoin();
            coinsList.add(coin);
            distanceTravelled -= gap;
        }

        for (int i=0; i< coinsList.size(); i++){
            if (coinsList.get(i).getY() + value > displayHeight) {
                pointsText.increaseMissed();
                coinsList.remove(i);
            } else if (coinsList.get(i).isInside(carStartX, carStartY, carEndX, carEndY)){
                pointsText.increasePoints();
                coinsList.remove(i);
            }
            coinsList.get(i).increaseY((int) value);
        }
    }

    public String getCoinsHistoryX(){
        String ret = "";

        for (int x: coinHistoryX){
            ret += x + ",";
        }

        return ret;
    }
}
