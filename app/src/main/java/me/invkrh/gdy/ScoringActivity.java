package me.invkrh.gdy;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class ScoringActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        long winnerId = getIntent().getLongExtra("winner", -1);
        if (winnerId < 0) {
            throw new IllegalStateException("Winner id can not be negative.");
        }
        ((TextView) findViewById(R.id.status)).setText("Winner is " + winnerId);

        final LinearLayout p1 = (LinearLayout) findViewById(R.id.player1);
        p1.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Drawable d = getDrawable(R.drawable.abc_list_selector_disabled_holo_dark);
                p1.setBackground(d);
            }
        });

        final LinearLayout p2 = (LinearLayout) findViewById(R.id.player2);
        p2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Drawable d = getDrawable(R.drawable.abc_list_selector_disabled_holo_dark);
                p2.setBackground(d);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scoring, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
