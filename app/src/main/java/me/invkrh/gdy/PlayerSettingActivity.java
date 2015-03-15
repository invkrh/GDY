package me.invkrh.gdy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;


public class PlayerSettingActivity extends ActionBarActivity {

    private final static int[] PLAYER_COLOR =
            {Color.BLACK, Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setting);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.gdy_file_key), Context.MODE_PRIVATE);
        int num_player = sharedPref.getInt(getString(R.string.num_player), -1);
        if (num_player == -1) {
            throw new IllegalStateException("number of player can not be negative, sharedPreference might be broken.");
        }

        LinearLayout playerListLayout = (LinearLayout) findViewById(R.id.play_list_layout);

        for (int i = 1; i <= num_player; i++) {
            View child = getLayoutInflater().inflate(R.layout.item_player, null);
            ((TextView) child.findViewById(R.id.player_number_tv)).setText("Player " + i);
            child.findViewById(R.id.player_color_tv).setBackgroundColor(PLAYER_COLOR[i - 1]);
            ((EditText) child.findViewById(R.id.player_name_et)).setText("");
            playerListLayout.addView(child);
        }

        Button startButton = (Button) findViewById(R.id.start_btn);
        final Intent intent = new Intent(this, PlayerSettingActivity.class);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player_setting, menu);
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
