package me.invkrh.gdy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import me.invkrh.gdy.common.DataModel;
import me.invkrh.gdy.common.Utils;
import me.invkrh.gdy.model.Player;
import me.invkrh.gdy.model.PlayerAdapter;


public class ChooseWinnerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_winner);
        // Construct the data source
        ArrayList<Player> playerList = DataModel.restoreAllPlayers(this);
        // Create the adapter to convert the array to views
        final PlayerAdapter adapter = new PlayerAdapter(this, playerList);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.player_list_lv);
        listView.setAdapter(adapter);

        final Intent intent = new Intent(this, ScoringActivity.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("winner", position + 1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_winner, menu);
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
