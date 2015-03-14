package me.invkrh.gdy;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.NumberPicker;

public class GameSettingActivity extends ActionBarActivity {

    private void setNumberPicker(int id, int min, int max, String[] displayedValues) {
        NumberPicker np = (NumberPicker) findViewById(id);
        np.setDisplayedValues(displayedValues);
        setNumberPicker(id, min, max);
    }

    private void setNumberPicker(int id, int min, int max) {
        NumberPicker np = (NumberPicker) findViewById(id);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(min);
        np.setWrapSelectorWheel(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setting);

        String[] maxValueArr = {"20", "30", "40", "50"};
        String[] initValueArr = {"50", "60", "70", "80", "90", "100"};

        setNumberPicker(R.id.np_num_player, 2, 6);
        setNumberPicker(R.id.np_max_point, 0, 3, maxValueArr);
        setNumberPicker(R.id.np_init_point, 0, 5, initValueArr);
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
