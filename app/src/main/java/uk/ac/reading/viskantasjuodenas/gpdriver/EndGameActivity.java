package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class EndGameActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_endgame);

        TextView coinsText = findViewById(R.id.coins);
        coinsText.setText(String.valueOf(getIntent().getIntExtra("COINS", 0)) + " Coins");

        TextView percentageText = findViewById(R.id.percentage);
        percentageText.setText(getIntent().getStringExtra("PERCENTAGE") + " %");

        TextView timeText = findViewById(R.id.time);
        timeText.setText(String.format("%d:%02d", getIntent().getIntExtra("MINUTES", 0), getIntent().getIntExtra("SECONDS", 0)));

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    new SubmitTask().execute(new URL(constructRequestURL("http://viskis22.pythonanywhere.com/Submit")));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private String constructRequestURL(String originalURL){
        String newURL = originalURL;

        EditText usernameText = findViewById(R.id.usernameEdit);

        newURL += "?username=" + usernameText.getText();
        newURL += "&coins=" + String.valueOf(getIntent().getIntExtra("COINS", 0));
        newURL += "&percentage=" + String.valueOf(getIntent().getStringExtra("PERCENTAGE"));
        newURL += "&minutes=" + String.valueOf(getIntent().getIntExtra("MINUTES", 0));
        newURL += "&seconds=" + String.valueOf(getIntent().getIntExtra("SECONDS", 0));
        newURL += "&level=" + getIntent().getStringExtra("LEVEL");

        return newURL;
    }

    private class SubmitTask extends AsyncTask<URL, String, String>{

        @Override
        protected String doInBackground(URL... params) {
           return HttpRequest.getResponse(params[0]);
        }

        @Override
        protected void onPostExecute(String result){
            boolean success = false;
            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getBoolean("Success")){
                    Intent intent = new Intent(EndGameActivity.this, MainActivity.class);
                    startActivity(intent);
                    success = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (!success){
                Toast.makeText(EndGameActivity.this, "Failure to submit results", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
