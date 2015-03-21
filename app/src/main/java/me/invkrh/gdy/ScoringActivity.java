package me.invkrh.gdy;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import me.invkrh.gdy.common.DataModel;
import me.invkrh.gdy.common.Utils;
import me.invkrh.gdy.model.Player;
import me.invkrh.gdy.model.PlayerAdapter;


public class ScoringActivity extends ActionBarActivity {

    private void GUIReset() {
        findViewById(R.id.card_np).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.multiple_np).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.multiple_tv).setVisibility(LinearLayout.VISIBLE);
        findViewById(R.id.or_tv).setVisibility(LinearLayout.VISIBLE);
        CheckBox max_cb = (CheckBox) findViewById(R.id.checkBox);
        max_cb.setChecked(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);
        final int winnerId = Utils.safeLongToInt(getIntent().getLongExtra("winner", -1));
        if (winnerId < 0) {
            throw new IllegalStateException("Winner id can not be negative.");
        }

        // Construct the data source
        final ArrayList<Player> playerList = DataModel.restoreAllPlayers(this);

        // set winner
        playerList.get(winnerId - 1).isWinner = true;
        // Create the adapter to convert the array to views
        final PlayerAdapter adapter = new PlayerAdapter(this, playerList);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.player_score_lv);
        listView.setAdapter(adapter);

        final LinearLayout consoleBar = (LinearLayout) findViewById(R.id.console_bar);
        consoleBar.setVisibility(LinearLayout.INVISIBLE);

        Button commitBtn = (Button) findViewById(R.id.commit_button);
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consoleBar.setVisibility(LinearLayout.INVISIBLE);
                for (Player p : playerList) {
                    if (p.isProcessing && !p.isWinner) {
                        p.isProcessing = false;
                        break;
                    }
                }
                adapter.notifyDataSetChanged();
                GUIReset();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position != winnerId - 1) {
                    boolean hasProcessingPlayer = false;
                    for (Player p : playerList) {
                        if (p.isProcessing) {
                            hasProcessingPlayer = true;
                            break;
                        }
                    }
                    if (!hasProcessingPlayer) {
                        view.setBackground(getResources().getDrawable(R.drawable.abc_list_selector_disabled_holo_light));
                        playerList.get(position).isProcessing = true;
                        consoleBar.setVisibility(LinearLayout.VISIBLE);
                    }
                }
            }
        });

        String[] card_num = {"1", "2", "3", "4", "5"};
        String[] multiple_num = {"1", "2", "4", "8", "16"};
        Utils.setNumberPicker(this, R.id.card_np, 5, card_num);
        Utils.setNumberPicker(this, R.id.multiple_np, 1, multiple_num);

        // TODO: add cancel(focus), redo(commit) button

        CheckBox max_cb = (CheckBox) findViewById(R.id.checkBox);
        max_cb.setText("Max = " + DataModel.restoreMaxPoint(this));
        max_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                View v1 = findViewById(R.id.card_np);
                View v2 = findViewById(R.id.multiple_np);
                View v3 = findViewById(R.id.multiple_tv);
                View v4 = findViewById(R.id.or_tv);

                if (isChecked) {
                    v1.setVisibility(LinearLayout.INVISIBLE);
                    v2.setVisibility(LinearLayout.INVISIBLE);
                    v3.setVisibility(LinearLayout.INVISIBLE);
                    v4.setVisibility(LinearLayout.INVISIBLE);
                } else {
                    v1.setVisibility(LinearLayout.VISIBLE);
                    v2.setVisibility(LinearLayout.VISIBLE);
                    v3.setVisibility(LinearLayout.VISIBLE);
                    v4.setVisibility(LinearLayout.VISIBLE);
                }
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
