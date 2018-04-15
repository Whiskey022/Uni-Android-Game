package uk.ac.reading.viskantasjuodenas.gpdriver;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class TrackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        final Context self = this;

        TextView abuDhabi = findViewById(R.id.abuDhabiText);
        abuDhabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "abuDhabi", null));
            }
        });

        TextView monza = findViewById(R.id.monzaText);
        monza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "monza", null));
            }
        });

        TextView monaco = findViewById(R.id.monacoText);
        monaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(new GamePanel(self, "monaco", null));
            }
        });
    }
}
