package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Context;
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

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback, SensorEventListener{

    private MainThread thread;
    private Player player;
    private Road road;
    private float speed = 1;
    private float playerX;
    private float degrees;
    private SensorManager sensorManager;
    private Sensor sensor;
    private String carStatus = "None";

    public GamePanel(Context context){
        super(context);
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        playerX = getResources().getDisplayMetrics().widthPixels/2;

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        image = Bitmap.createScaledBitmap(image, 150, 150, false);
        player = new Player(image, playerX - image.getWidth()/2, getResources().getDisplayMetrics().widthPixels/2 - image.getHeight()/2);

        road = new Road(getResources().getDisplayMetrics().widthPixels, getResources().getDisplayMetrics().heightPixels);

        degrees = 0;

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_GAME);

        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        thread  = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true) {
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

    public void update() {
        controlSpeed();
        road.update((int)speed);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        road.draw(canvas);
        player.draw(canvas);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.values[0] > 1) {
            player.update(-event.values[0]/(float)Math.pow(speed, 0.2));
            System.out.println(-event.values[0]/(float)Math.pow(speed, 0.2));
        } else if (event.values[0] < -1) {
            player.update(-event.values[0]/(float)Math.pow(speed, 0.2));
            System.out.println(-event.values[0]/(float)Math.pow(speed, 0.2));
        } else {
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void controlSpeed(){
        switch (carStatus){
            case "Throttle":
                speed += 0.1;
                break;
            case "Brake":
                speed -= 0.3;
                break;
            default:
                break;
        }
    }
}
