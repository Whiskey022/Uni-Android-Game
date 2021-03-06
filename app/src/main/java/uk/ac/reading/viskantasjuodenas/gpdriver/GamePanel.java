package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.CoinsManager;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.GameObject;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.GameSpeedometer;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.GameTimer;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.OpponentsManager;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.Player;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.PointsText;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.Road;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.RoadAbuDhabi;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.RoadMonaco;
import uk.ac.reading.viskantasjuodenas.gpdriver.GameObjects.RoadMonza;


/**
 * Class that controls the game - drawing, calculations
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener{

    private MainThread thread;
    private float speed = 1;
    private Player player;
    private Bitmap playerImage;
    private Road road;
    private SensorManager sensorManager;
    private ArrayList<GameObject> gameObjects;
    private String carStatus = "None";
    private Boolean collided = false;

    /**
     *
     * @param context Class context
     * @param track Selected track
     * @param level Loaded saved level if any
     */
    public GamePanel(Context context, String track, String level){
        super(context);
        getHolder().addCallback(this);

        //Setting up game thread
        thread = new MainThread(getHolder(), this);

        gameObjects = new ArrayList<>();

        //Road creation based on selected track
        road = null;
        switch (track){
            case "monza":
                road = new RoadMonza(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
                break;
            case "monaco":
                road = new RoadMonaco(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
                break;
            default:
                road = new RoadAbuDhabi(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);
        }

        //Initializing other game objects, adding all of them to the gameObjects arraylist
        GameSpeedometer gameSpeedometer = new GameSpeedometer(speed);
        GameTimer gameTimer = new GameTimer();
        PointsText pointsText = new PointsText();
        Bitmap coinImage = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.coin), 50, 50, false);
        CoinsManager coinsManager = new CoinsManager(coinImage, road.getRoadStartX(), road.getRoadEndX(), getResources().getDisplayMetrics().heightPixels,  getResources().getDisplayMetrics().heightPixels/5, level, pointsText);
        gameObjects.add(road);
        gameObjects.add(gameSpeedometer);
        gameObjects.add(gameTimer);
        gameObjects.add(pointsText);
        gameObjects.add(coinsManager);

        //Initializing OpponentsManager, adding to the arraylist
        Bitmap opponentImage = BitmapFactory.decodeResource(getResources(), R.drawable.mclaren);
        opponentImage = Bitmap.createScaledBitmap(opponentImage, (int)(opponentImage.getWidth()*0.7), (int)(opponentImage.getHeight()*0.7), false);
        OpponentsManager opponentsManager = new OpponentsManager(opponentImage, road.getRoadStartX(), road.getRoadEndX(), getResources().getDisplayMetrics().heightPixels);
        gameObjects.add(opponentsManager);


        //Setting up Player object
        playerImage = BitmapFactory.decodeResource(getResources(), R.drawable.ferrari);
        playerImage = Bitmap.createScaledBitmap(playerImage, (int)(playerImage.getWidth()*0.7), (int)(playerImage.getHeight()*0.7), false);
        float playerX = getResources().getDisplayMetrics().widthPixels/2;
        float playerY = (float)(getResources().getDisplayMetrics().heightPixels/1.5 - playerImage.getHeight()/2);
        player = new Player(playerImage, playerX - playerImage.getWidth()/2, playerY);
        gameObjects.add(player);

        //Setting up accelerometer sensor
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

        //Set view to focused
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) { }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Starting MainThread - which keeps updating and drawing the gamePanel
        thread  = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //On surface destroyed, keep trying to stop the main thread.
        boolean retry = true;
        while (retry) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public  boolean onTouchEvent(MotionEvent event) {
        //If screen is being touched, change the speed of the car
        //If the screen is touched on the right side - accelerate, otherwise - brake
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                if (event.getX() > getResources().getDisplayMetrics().widthPixels/2) {
                    carStatus = "Throttle";
                } else {
                    carStatus = "Brake";
                }
                break;
            default:
                carStatus = "None";
                break;
        }
        return true;
    }

    /**
     * Updating all game objects values
     */
    public void update() {
        //Change the car speed if needed
        controlSpeed();

        //Update all game objects
        for (GameObject obj: gameObjects){
            //If object is CoinManager, also update the coordinates where the coins are being collected
            if (obj.getClass() == CoinsManager.class){
                ((CoinsManager)obj).updateCollectionField((int)player.getX(), (int)player.getY(),
                        (int)player.getX() + playerImage.getWidth(), (int)player.getY() + playerImage.getHeight());
            //If the object is OpponentsManager, check if the player has collided with something, if yes - end game.
            } else if (obj.getClass() == OpponentsManager.class){
                collided = collided || ((OpponentsManager)obj).checkCollision((int)player.getX(), (int)player.getY(),
                        (int)player.getX() + player.getImage().getWidth(), (int)player.getY() + player.getImage().getWidth());
                if (collided) {
                    endGame();
                }
            }
            //Do not update Player object - it gets updated seperately
            if(obj.getClass() != Player.class) {
                obj.update((int) speed);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw background
        canvas.drawColor(Color.WHITE);
        //Draw each object
        for (GameObject obj : gameObjects){
            obj.draw(canvas);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //Calculate new x coordinate for the player based on phone's movement
        float newX = -event.values[0]/(float)Math.pow(speed, 0.1) + player.getX();
        //Check if phone was tilted enough to change x coordinate both on the right and left sides
        if (event.values[0] > 1 && newX > 0) {
            player.update(newX);
        } else if (event.values[0] < -1 && newX + playerImage.getWidth() < getResources().getDisplayMetrics().widthPixels) {
            player.update(newX);
        }

        //Check if the car is now on the gravel or hitting a wall
        String roadStatus = road.checkRoadPosition(player.getX(), player.getX() + playerImage.getWidth());

        //If car is on the gravel, it will have to brake,
        //if it hit the wall, collided set to true and will end the game
        if (roadStatus == "Gravel") {
            carStatus = "Brake";
        } else if (roadStatus == "Wall") {
            collided = true;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) { }

    /**
     * Controling speed based on car's status
     */
    private void controlSpeed(){
        switch (carStatus){
            case "Throttle":
                speed += 0.3 - speed*0.004;         //Higher the speed, slower it accelerates
                break;
            case "Brake":
                if (speed >= 1) {                   //If braking, slow down until the speed is 1
                    speed -= 0.4;
                }
                break;
            default:
                break;
        }
    }

    /**
     * Method to end the game
     */
    private void endGame(){
        //Stop thread
        thread.setRunning(false);

        //Create new intent for EndGamActity
        Intent intent = new Intent().setClass(getContext(), EndGameActivity.class);

        //Pass data to the new intent, it will be used to save highscores
        for (GameObject gameObject: gameObjects){
            if (gameObject.getClass() == PointsText.class){
                intent.putExtra("COINS", ((PointsText)gameObject).getPoints());
                intent.putExtra("PERCENTAGE", ((PointsText)gameObject).getPercentage());
            } else if (gameObject.getClass() == CoinsManager.class){
                intent.putExtra("LEVEL", ((CoinsManager)gameObject).getCoinsHistoryX());
            } else if (gameObject.getClass() == GameTimer.class){
                intent.putExtra("MINUTES", ((GameTimer)gameObject).getMinutes());
                intent.putExtra("SECONDS", ((GameTimer)gameObject).getSeconds());
            } else if (gameObject.getClass().getSuperclass() == Road.class){
                intent.putExtra("TRACK", ((Road)gameObject).getRoadType());
            }
        }

        //Start new activity
        getContext().startActivity(intent);
    }
}
