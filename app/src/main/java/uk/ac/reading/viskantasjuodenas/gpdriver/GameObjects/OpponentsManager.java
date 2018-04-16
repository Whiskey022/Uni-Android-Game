package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

/**
 * Controls all opponents on the track
 */
public class OpponentsManager implements GameObject{

    private ArrayList<Opponent> opponents;
    private Bitmap opponentImage;
    private int minX;
    private int maxX;
    private int displayHeight;

    /**
     *
     * @param opponentImage image for opponent
     * @param minX minimum x value
     * @param maxX maximum x value
     * @param displayHeight screen's height
     */
    public OpponentsManager(Bitmap opponentImage, int minX, int maxX, int displayHeight){
        this.opponentImage = opponentImage;
        this.minX = minX;
        this.maxX = maxX;
        this.displayHeight = displayHeight;

        opponents = new ArrayList<>();
    }

    /**
     * Adds opponent at random x coordinate, with lower speed than player's
     * @param playerSpeed
     */
    private void addOpponent(float playerSpeed){
        //New Opponent
        Opponent newOpponent = new Opponent(opponentImage,
                new Random().nextInt(maxX-minX-opponentImage.getWidth())+minX, -opponentImage.getHeight());

        //Set speed for oppenent
        newOpponent.setSpeed(playerSpeed);

        //Add to the list
        opponents.add(newOpponent);
    }

    /**
     * Checks if field in specified coordinates touched any of the drivers
     * @param startX
     * @param startY
     * @param endX
     * @param endY
     * @return boolean
     */
    public boolean checkCollision(int startX, int startY, int endX, int endY){
        //Checks all opponents
        for (Opponent opponent: opponents){
            //Check if opponent is inside the specified field
            if (opponent.getY() + opponentImage.getHeight() > startY && opponent.getY() < endY &&
                    opponent.getX() + opponentImage.getWidth() > startX && opponent.getX() < endX){
                return true;
            }
        }
        return false;
    }

    /**
     * Randomly changes opponent's direction
     * @param currentSteeringDirection opponent's current steering direction
     * @param x opponent's x coordinate
     * @return String new direction
     */
    private String decideSteeringDirection(String currentSteeringDirection, int x){

        //Check if with current direction opponent still stays on track
        //If not change his direction to "None"
        if (currentSteeringDirection == "Left" && x <= minX){
            return "None";
        }
        if (currentSteeringDirection == "Right" && x + opponentImage.getWidth() >= maxX){
            return "None";
        }

        //Sometimes randomly assign new direction
        switch(new Random().nextInt(500)){
            case 0:
                return "None";
            case 1:
                return "Left";
            case 2:
                return "Right";
            default:
                return currentSteeringDirection;
        }

    }

    /**
     * Draws each opponent on track
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        for (Opponent opponent: opponents){
            opponent.draw(canvas);
        }
    }

    /**
     * Updates each opponent's coordinates and speed on track
     * @param value usually the player speed
     */
    @Override
    public void update(float value) {
        //For each opponent
        for (int i=0; i<opponents.size(); i++){
            //If opponent is outside the view now, remove the opponent
            if (opponents.get(i).getY() < - opponentImage.getHeight()*2 || opponents.get(i).getY() > displayHeight){
                opponents.remove(i);
            //Otherwise, update opponents speed and direction
            } else {
                opponents.get(i).setSteeringDirection(decideSteeringDirection(opponents.get(i).getSteeringDirection(), opponents.get(i).getX()));
                opponents.get(i).update(value);
            }
        }
        //Sometimes randomly generates a new opponent, lower chance with higher opponents count
        if (new Random().nextInt(250*(opponents.size() + 1)) == 0) {
            addOpponent(value);
        }
    }

}
