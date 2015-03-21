package me.invkrh.gdy;

import android.content.Intent;
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

import java.util.ArrayList;

import me.invkrh.gdy.common.DataModel;
import me.invkrh.gdy.model.Player;


public class PlayerSettingActivity extends ActionBarActivity {

    private static final int[] PLAYER_COLOR =
            {Color.BLACK, Color.BLUE, Color.RED, Color.CYAN, Color.YELLOW, Color.MAGENTA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_setting);

        int num_player = DataModel.restorePlayerNumber(this);
        final LinearLayout playerListLayout = (LinearLayout) findViewById(R.id.player_list_layout);

        for (int i = 1; i <= num_player; i++) {
            Player p = DataModel.getPlayerById(this, i);
            View child = getLayoutInflater().inflate(R.layout.editable_player_item, null);
            ((TextView) child.findViewById(R.id.player_number_tv)).setText("Player " + i);
            child.findViewById(R.id.player_color_tv).setBackgroundColor(PLAYER_COLOR[i - 1]);
            ((EditText) child.findViewById(R.id.player_name_et)).setText(p.name);
            playerListLayout.addView(child);
        }

        Button startButton = (Button) findViewById(R.id.start_btn);
        final Intent intent = new Intent(this, ChooseWinnerActivity.class);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayerSettingActivity self = PlayerSettingActivity.this;
                int player_num = DataModel.restorePlayerNumber(self);
                int init_points = DataModel.restoreInitialPoints(self);
                ArrayList<Player> playerList = new ArrayList<Player>();
                for (int i = 1; i <= player_num; i++) {
                    String name = ((EditText) playerListLayout.getChildAt(i - 1).findViewById(R.id.player_name_et)).getText().toString();
                    playerList.add(new Player(i, PLAYER_COLOR[i - 1], name, init_points));
                }
                DataModel.persistAllPlayers(self, playerList);
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
