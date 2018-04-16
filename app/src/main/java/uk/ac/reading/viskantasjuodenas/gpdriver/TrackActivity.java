package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Activity for the track selection
 */
public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        //Saving context
        final Context self = this;

        //On Abu Dhabi track selection
        TextView abuDhabi = findViewById(R.id.abuDhabiText);
        abuDhabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "abuDhabi", null));
            }
        });

        //On Monza track selection
        TextView monza = findViewById(R.id.monzaText);
        monza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "monza", null));
            }
        });


        //On Monaco track selection
        TextView monaco = findViewById(R.id.monacoText);
        monaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "monaco", null));
            }
        });
    }
}
