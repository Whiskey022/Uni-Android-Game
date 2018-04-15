package uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Random;

public class OpponentsManager implements GameObject{

    private ArrayList<Opponent> opponents;
    private Bitmap opponentImage;
    private int minX;
    private int maxX;
    private int displayHeight;

    public OpponentsManager(Bitmap opponentImage, int minX, int maxX, int displayHeight){
        this.opponentImage = opponentImage;
        this.minX = minX;
        this.maxX = maxX;
        this.displayHeight = displayHeight;

        opponents = new ArrayList<>();
    }

    private void addOpponent(float playerSpeed){
        Opponent newOpponent = new Opponent(opponentImage, new Random().nextInt(maxX-minX-opponentImage.getWidth())+minX, -opponentImage.getHeight());
        newOpponent.setSpeed(playerSpeed);
        opponents.add(newOpponent);
    }

    public boolean checkCollision(int startX, int startY, int endX, int endY){
        for (Opponent opponent: opponents){
            if (opponent.getY() + opponentImage.getHeight() > startY && opponent.getY() < endY &&
                    opponent.getX() + opponentImage.getWidth() > startX && opponent.getX() < endX){
                return true;
            }
        }
        return false;
    }

    private String decideSteeringDirection(String currentSteeringDirection, int x){
        if (currentSteeringDirection == "Left" && x <= minX){
            return "None";
        }
        if (currentSteeringDirection == "Right" && x + opponentImage.getWidth() >= maxX){
            return "None";
        }

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

    @Override
    public void draw(Canvas canvas) {
        for (Opponent opponent: opponents){
            opponent.draw(canvas);
        }
    }

    @Override
    public void update(float value) {
        for (int i=0; i<opponents.size(); i++){
            if (opponents.get(i).getY() < - opponentImage.getHeight()*2 || opponents.get(i).getY() > displayHeight){
                opponents.remove(i);
            } else {
                opponents.get(i).setSteeringDirection(decideSteeringDirection(opponents.get(i).getSteeringDirection(), opponents.get(i).getX()));
                opponents.get(i).update(value);
            }
        }
        if (new Random().nextInt(250*(opponents.size() + 1)) == 0) {
            addOpponent(value);
        }
    }

}
