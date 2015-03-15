package me.invkrh.gdy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Arrays;

public class GameSettingActivity extends ActionBarActivity {

    private void setNumberPicker(int id, int value, String[] displayedValues) {
        int valueIdx = Arrays.asList(displayedValues).indexOf(Integer.toString(value));
        int min = 0;
        int max = displayedValues.length - 1;

        NumberPicker np = (NumberPicker) findViewById(id);
        np.setDisplayedValues(displayedValues);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(valueIdx);
        np.setWrapSelectorWheel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);

        // TODO: get SharedPreference

        String[] nbPlayerArr = {"2", "3", "4", "5", "6"};
        String[] maxValueArr = {"20", "30", "40", "50"};
        String[] initValueArr = {"50", "60", "70", "80", "90", "100"};

        setNumberPicker(R.id.num_player_np, 4, nbPlayerArr);
        setNumberPicker(R.id.max_point_np, 20, maxValueArr);
        setNumberPicker(R.id.np_init_point, 50, initValueArr);

        Button startButton = (Button) findViewById(R.id.go_to_player_settings_btn);
        final Intent intent = new Intent(this, GameSettingActivity.class);
        startButton.setOnClickListener(v -> startActivity(intent));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_game_setting, menu);
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
