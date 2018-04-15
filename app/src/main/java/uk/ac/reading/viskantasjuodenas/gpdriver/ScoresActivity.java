package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class ScoresActivity extends AppCompatActivity{

    private TableLayout tableLayout;
    private String[] keys = {"Username", "Coins", "Percentage"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        tableLayout = findViewById(R.id.scoresTable);

        try {
            new SubmitTask().execute(new URL("http://viskis22.pythonanywhere.com/Scores/"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class SubmitTask extends AsyncTask<URL, String, String> {


        @Override
        protected String doInBackground(URL... params) {
            return HttpRequest.getResponse(params[0]);
        }

        @Override
        protected void onPostExecute(String result){
            boolean success = false;
            try {
                JSONObject jsonObject = new JSONObject(result);
                System.out.println(jsonObject.toString());
                if (jsonObject.getBoolean("Success")) {
                    for (int i = 0; i < jsonObject.getJSONObject("Values").length(); i++) {
                        JSONObject jsonResult = jsonObject.getJSONObject("Values").getJSONObject(String.valueOf(i));

                        TableRow row = new TableRow(ScoresActivity.this);

                        for (String key: keys){
                            TextView textView = new TextView(ScoresActivity.this);
                            textView.setText(jsonResult.getString(key));
                            textView.setBackground(ContextCompat.getDrawable(ScoresActivity.this, R.drawable.cell));
                            row.addView(textView);
                        }

                        String time = String.format("%d:%02d", jsonResult.getInt("Minutes"), jsonResult.getInt("Seconds"));
                        TextView textView = new TextView(ScoresActivity.this);
                        textView.setText(time);
                        textView.setBackground(ContextCompat.getDrawable(ScoresActivity.this, R.drawable.cell));
                        row.addView(textView);

                        Button playButton = new Button(ScoresActivity.this);
                        playButton.setText("Play");
                        row.addView(playButton);

                        tableLayout.addView(row);
                    }
                }
                if (jsonObject.getBoolean("Success")){
                    success = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!success){
                Toast.makeText(ScoresActivity.this, "Failure to submit results", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
