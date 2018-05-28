package unxavi.com.github.jokedisplayandroidlib;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class JokeDisplayActivity extends AppCompatActivity {

    public static final String JOKE_KEY = "JOKE_KEY";

    private TextView jokeTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke_display);
        Toolbar toolbar = findViewById(R.id.toolbar);
        jokeTv = findViewById(R.id.jokeTv);
        setSupportActionBar(toolbar);

        if (getIntent() != null) {
            String joke = getIntent().getStringExtra(JOKE_KEY);
            if (TextUtils.isEmpty(joke)) {
                closeOnError();
            } else {
                jokeTv.setText(joke);
            }
        } else {
            closeOnError();
        }
    }

    private void closeOnError() {
        Toast.makeText(this, "No Joke provided, closing", Toast.LENGTH_SHORT).show();
        finish();
    }

}
