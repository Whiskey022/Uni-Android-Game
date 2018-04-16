package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Activity for the main menu
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Save class context
        final Context self = this;

        //On "Play" click
        TextView playTextView = findViewById(R.id.playText);
        playTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, TrackActivity.class);
                startActivity(intent);
            }
        });

        //On "HighScores" click
        TextView scoresTextView = findViewById(R.id.scoresText);
        scoresTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, ScoresActivity.class);
                startActivity(intent);
            }
        });

        //On "About" click
        TextView aboutTextView = findViewById(R.id.aboutText);
        aboutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(self, AboutActivity.class);
                startActivity(intent);
            }
        });

    }
}
