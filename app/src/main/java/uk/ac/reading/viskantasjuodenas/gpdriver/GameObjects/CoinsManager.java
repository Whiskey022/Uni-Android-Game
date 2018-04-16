package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class controls all Coin objects
 */
public class CoinsManager implements GameObject{

    private Bitmap coinImage;
    private int minWidth;
    private int maxWidth;
    private int displayHeight;
    private int gap;
    private ArrayList<Coin> coinsList;
    private ArrayList<Integer> coinHistoryX;
    private ArrayList<Coin> loadedCoins;
    private Coin coin;
    private int distanceTravelled = 0;
    private int previousX;
    private int carStartX = 0;
    private int carEndX = 0;
    private int carStartY = 0;
    private int carEndY = 0;
    private PointsText pointsText;

    /**
     * Loads coins from Level String if it's not empty, otherwise initializes empty coins list
     * @param coinImage image for Coin objects
     * @param minWidth minimum x coordinate
     * @param maxWidth maximum x coordinate
     * @param displayHeight screen's height
     * @param gap gap between coins
     * @param level loaded coins in String format
     * @param pointsText PoinsText object
     */
    public CoinsManager(Bitmap coinImage, int minWidth, int maxWidth, int displayHeight, int gap, String level, PointsText pointsText){
        this.coinImage = coinImage;
        this.minWidth = minWidth;
        this.maxWidth = maxWidth;
        this.displayHeight = displayHeight;
        this.gap = gap;
        this.pointsText = pointsText;

        //Setup previousX to a random value
        previousX = new Random().nextInt(maxWidth - minWidth - coinImage.getWidth()) + minWidth;

        coinsList = new ArrayList<>();
        //Load coins from "level" if it has any
        loadCoinsList(level);
        coinHistoryX = new ArrayList<>();
    }

    /**
     * Add a new coin to the list at a random x
     */
    private void generateCoin(){
        int newX = previousX;

        //Randomly decide the new x
        switch (new Random().nextInt(3)){
            case 0:
                break;
            case 1:
                //Check if newX would not be outside the road
                if (newX - 70 < minWidth) {
                    newX = minWidth;
                } else {
                    newX -= 70;
                }
                break;
            case 2:
                //Check if newX would not be outside the road
                if (newX + 70 > maxWidth - coinImage.getWidth()){
                    newX = maxWidth - coinImage.getWidth();
                } else {
                    newX += 70;
                }
                break;
        }

        //Create Coin at newX, add it to the coinHistoryX
        coin = new Coin(coinImage, newX, 0);
        coinHistoryX.add(newX);
        previousX = newX;
    }

    /**
     * Update the field with specified coordinates where the coins will get collected
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     */
    public void updateCollectionField(int startX, int startY, int endX, int endY){
        carStartX = startX;
        carStartY = startY;
        carEndX = endX;
        carEndY = endY;
    }

    /**
     * Draw all Coin objects
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        for (Coin coin: coinsList){
            Paint paint = new Paint();
            canvas.drawBitmap(coin.getImage(), coin.getX(), coin.getY(), paint);
        }
    }

    /**
     * Update all Coin objects
     * @param value usually the player speed
     */
    @Override
    public void update(float value) {
        //Calculate distance travelled with speed
        distanceTravelled += value;

        //If distance travelled higher than gap between coins, add new coin
        if (distanceTravelled > gap){
            //If we have any loaded coins from API, use one of them
            if (loadedCoins.size() != 0){
                //Add the first coin to the list, to the history list, and remove that first coin from loadedCoins
                coinsList.add(loadedCoins.get(0));
                coinHistoryX.add(loadedCoins.get(0).getX());
                loadedCoins.remove(0);
            //Otherwise, generate a new coin
            } else {
                generateCoin();
                coinsList.add(coin);
            }
            //Decrease the distance travelled
            distanceTravelled -= gap;
        }

        //For all coins, update y coordinate based on speed
        for (int i=0; i< coinsList.size(); i++){
            //Count coins as missed if it left the view
            if (coinsList.get(i).getY() + value > displayHeight) {
                pointsText.increaseMissed();
                coinsList.remove(i);
            //Count coins as collected if the driver got them
            } else if (coinsList.get(i).isInside(carStartX, carStartY, carEndX, carEndY)){
                pointsText.increasePoints();
                coinsList.remove(i);
            }
            coinsList.get(i).increaseY((int) value);
        }
    }

    /**
     * @return Coins x coordiante history in String format
     */
    public String getCoinsHistoryX(){
        String ret = "";

        //For each coin, add x coordinate and comma to string
        for (int x: coinHistoryX){
            ret += x + ",";
        }

        return ret;
    }

    /**
     * Generate Coins array list from String format of loaded coins
     * @param level Coins x coordinates in String format
     */
    private void loadCoinsList(String level){
        //If level has valid value
        if (level != null && level != "") {

            ArrayList<Coin> coins = new ArrayList<>();

            //Split level by commas into a String array
            String[] xPositions = level.split(",");

            //For each x value in String, create a new coin
            for (String xPos: xPositions){
                coins.add(new Coin(coinImage, Integer.valueOf(xPos), 0));
            }

            //Save coins
            loadedCoins = coins;
        } else {
            loadedCoins = new ArrayList<>();
        }
    }
}
