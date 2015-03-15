package me.invkrh.gdy;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class PlayerSettingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setting);

        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.gdy_file_key), Context.MODE_PRIVATE);
        int num_player = sharedPref.getInt(getString(R.string.num_player), -1);
        if (num_player == -1) {
            throw new IllegalStateException("number of player can not be negative, sharedPreference might be broken.");
        }

        TextView test = (TextView) findViewById(R.id.test_tv);
        test.setText("There are " + num_player + " players.");
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
