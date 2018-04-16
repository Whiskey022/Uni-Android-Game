package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Activity retrieves score results from API and displays them
 */
public class ScoresActivity extends AppCompatActivity{

    private TableLayout tableLayout;
    private String[] keys = {"Username", "Coins", "Percentage", "Track"};   //Values to retrieve from API response

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        //Set tableLayout
        tableLayout = findViewById(R.id.scoresTable);

        try {
            //Try talking with API
            new SubmitTask().execute(new URL("http://viskis22.pythonanywhere.com/Scores/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Async task to communicate with API
     */
    private class SubmitTask extends AsyncTask<URL, String, String> {

        @Override
        protected String doInBackground(URL... params) {
            //Send request and get the response from API
            return HttpRequest.getResponse(params[0]);
        }

        @Override
        protected void onPostExecute(String result){
            boolean success = false;

            try {
                //Try converting response to JSON
                JSONObject jsonObject = new JSONObject(result);

                //If response has Success - true
                if (jsonObject.getBoolean("Success")) {
                    //Read all objects in "Values"
                    for (int i = 0; i < jsonObject.getJSONObject("Values").length(); i++) {
                        //Get score object
                        final JSONObject jsonResult = jsonObject.getJSONObject("Values").getJSONObject(String.valueOf(i));

                        //Set a new TableRow
                        TableRow row = new TableRow(ScoresActivity.this);

                        //For all keys specified,
                        //create new TextView, set text to data found, add to the TableRow
                        for (String key: keys){
                            TextView textView = new TextView(ScoresActivity.this);
                            textView.setText(jsonResult.getString(key));
                            textView.setBackground(ContextCompat.getDrawable(ScoresActivity.this, R.drawable.cell));
                            row.addView(textView);
                        }

                        //Retrieve time from the response, set as TextView and add to the TableRow
                        String time = String.format("%d:%02d", jsonResult.getInt("Minutes"), jsonResult.getInt("Seconds"));
                        TextView textView = new TextView(ScoresActivity.this);
                        textView.setText(time);
                        textView.setBackground(ContextCompat.getDrawable(ScoresActivity.this, R.drawable.cell));
                        row.addView(textView);

                        //Set "Play" button for the score
                        //On click will play the same game as the score was set on
                        Button playButton = new Button(ScoresActivity.this);
                        playButton.setText("Play");
                        playButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    setContentView(new GamePanel(ScoresActivity.this, jsonResult.getString("Track"), jsonResult.getString("Level")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        row.addView(playButton);

                        //Add the TableRow to the TableLayout
                        tableLayout.addView(row);
                    }
                    success = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //If unsuccessful response - show toast message
            if (!success){
                Toast.makeText(ScoresActivity.this, "Failure to submit results", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
