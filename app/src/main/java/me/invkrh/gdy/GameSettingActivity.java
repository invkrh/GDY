package me.invkrh.gdy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.Arrays;

public class GameSettingActivity extends ActionBarActivity {

    // available setting values
    private static String[] nbPlayerArr = {"2", "3", "4", "5", "6"};
    private static String[] maxValueArr = {"20", "30", "40", "50"};
    private static String[] initValueArr = {"50", "60", "70", "80", "90", "100"};

    private int getNumberPickerValue(int id) {
        NumberPicker np = (NumberPicker) findViewById(id);
        int valIdx = np.getValue();
        String[] valArr = np.getDisplayedValues();
        return Integer.parseInt(valArr[valIdx]);
    }

    private void setNumberPicker(int id, int  value, String[] displayedValues) {
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

        //  load settings
        final SharedPreferences sharedPref = getSharedPreferences(getString(R.string.gdy_file_key), Context.MODE_PRIVATE);
        int num_player = sharedPref.getInt(getString(R.string.num_player), 4);
        int max_points = sharedPref.getInt(getString(R.string.max_points), 20);
        int init_points = sharedPref.getInt(getString(R.string.init_points), 50);

        // use retrieved settings
        setNumberPicker(R.id.num_player_np, num_player, nbPlayerArr);
        setNumberPicker(R.id.max_point_np, max_points, maxValueArr);
        setNumberPicker(R.id.init_point_np, init_points, initValueArr);

        Button nextButton = (Button) findViewById(R.id.goto_player_settings_btn);
        final Intent intent = new Intent(this, PlayerSettingActivity.class);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // save settings
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.num_player), getNumberPickerValue(R.id.num_player_np));
                editor.putInt(getString(R.string.max_points), getNumberPickerValue(R.id.max_point_np));
                editor.putInt(getString(R.string.init_points), getNumberPickerValue(R.id.init_point_np));
                editor.apply();
                startActivity(intent);
            }
        });
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
