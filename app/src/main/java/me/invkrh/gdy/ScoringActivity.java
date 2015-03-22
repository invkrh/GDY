package me.invkrh.gdy;

import android.annotation.TargetApi;
import android.content.Intent;
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
import android.widget.Toast;

import java.util.ArrayList;

import me.invkrh.gdy.common.DataModel;
import me.invkrh.gdy.common.Utils;
import me.invkrh.gdy.model.Player;
import me.invkrh.gdy.model.PlayerAdapter;
import me.invkrh.gdy.model.Transaction;


public class ScoringActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoring);

        final int max = DataModel.restoreMaxPoint(ScoringActivity.this);

        // Construct the data source
        final ArrayList<Player> playerList = DataModel.restoreAllPlayers(this);
        final Transaction[] transactionHistory = new Transaction[playerList.size()];

        // get winner id
        final int winnerId = getIntent().getIntExtra("winner", -1);
        if (winnerId < 0) {
            throw new IllegalStateException("Winner id can not be negative.");
        }

        // set winner
        playerList.get(winnerId - 1).isWinner = true;

        // Create the adapter to convert the array to views
        final PlayerAdapter adapter = new PlayerAdapter(this, playerList);

        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.player_score_lv);
        listView.setAdapter(adapter);

        //  initially hide console bar
        final LinearLayout consoleBar = (LinearLayout) findViewById(R.id.console_bar);
        consoleBar.setVisibility(LinearLayout.INVISIBLE);

        // get checkbox
        final CheckBox max_cb = (CheckBox) findViewById(R.id.checkBox);

        // get commit button
        final Button commitBtn = (Button) findViewById(R.id.commit_button);

        // get rollback button
        final Button rollbackBtn = (Button) findViewById(R.id.rollback_btn);
        rollbackBtn.setEnabled(false);

        // get next round button
        final Button nextRoundBtn = (Button) findViewById(R.id.next_round_button);

        // calculator layout
        final LinearLayout calculatorLayout = (LinearLayout ) findViewById(R.id.calculator);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Player currentClickedPlayer = playerList.get(position);
                if (!currentClickedPlayer.isWinner) {
                    Player previousClickedPlayer = null;

                    for (Player p : playerList) {
                        if (p.isSelected) {
                            previousClickedPlayer = p;
                            previousClickedPlayer.isSelected = false;
                        }
                    }

                    // show console bar if no player is selected or if clicked on a new player
                    if (previousClickedPlayer == null || previousClickedPlayer != currentClickedPlayer) {
                        currentClickedPlayer.isSelected = true;
                        // setEnable based on if corresponding transaction exists
                        if (transactionHistory[position] == null) {
                            commitBtn.setEnabled(true);
                            rollbackBtn.setEnabled(false);
                        } else {
                            commitBtn.setEnabled(false);
                            rollbackBtn.setEnabled(true);
                        }
                        consoleBar.setVisibility(LinearLayout.VISIBLE);
                    } else { // click on winner or selected player
                        consoleBar.setVisibility(LinearLayout.INVISIBLE);
                    }

                    if(commitBtn.isEnabled()) {
                        calculatorLayout.setVisibility(LinearLayout.VISIBLE);
                    } else {
                        calculatorLayout.setVisibility(LinearLayout.INVISIBLE);
                    }

                    adapter.notifyDataSetChanged();
                }
            }
        });

        // number picker settings
        String[] card_num = {"1", "2", "3", "4", "5"};
        String[] multiple_num = {"1", "2", "4", "8", "16"};
        Utils.setNumberPicker(this, R.id.card_np, 5, card_num);
        Utils.setNumberPicker(this, R.id.multiple_np, 1, multiple_num);

        // checkbox settings
        max_cb.setText("Max = " + DataModel.restoreMaxPoint(this));
        max_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int visibility;
                if (isChecked)
                    visibility = LinearLayout.INVISIBLE;
                else
                    visibility = LinearLayout.VISIBLE;
                findViewById(R.id.card_np).setVisibility(visibility);
                findViewById(R.id.multiple_np).setVisibility(visibility);
                findViewById(R.id.multiple_tv).setVisibility(visibility);
                findViewById(R.id.divider).setVisibility(visibility);
            }
        });

        // add commit button listener
        commitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Player loser = null;
                Player winner = null;

                // get payment
                int payment;
                if (max_cb.isChecked()) {
                    payment = max;
                } else {
                    int nbCard = Utils.getNumberPickerValue(ScoringActivity.this, R.id.card_np);
                    int multiple = Utils.getNumberPickerValue(ScoringActivity.this, R.id.multiple_np);
                    payment =java.lang.Math.min(nbCard * multiple, max);
                }

                // once clicked, hide console bar
                consoleBar.setVisibility(LinearLayout.INVISIBLE);

                for (Player p : playerList) {
                    if (p.isWinner) {
                        winner = p;
                    } else if (p.isSelected) {
                        p.isSelected = false;
                        loser = p;
                    }
                }

                // commit the transaction and add it to history
                Transaction t = new Transaction(loser, winner, payment);
                t.commit();
                assert loser != null;
                transactionHistory[loser.number - 1] = t;

                // show or hide next round based on if all transactions are done
                boolean isDone = true;
                for (Player p : playerList) {
                    assert winner != null;
                    if (p.number != winner.number) {
                        if(transactionHistory[p.number - 1] == null) {
                            isDone = false;
                            break;
                        }
                    }
                }

                if (isDone) {
                    nextRoundBtn.setEnabled(true);
                } else {
                    nextRoundBtn.setEnabled(false);
                }

                loser.isProcessed = true;

                // make multiple part appear
                max_cb.setChecked(false);

                // update list view
                adapter.notifyDataSetChanged();
            }
        });

        rollbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Player p : playerList) {
                    if (p.isSelected) {
                        transactionHistory[p.number - 1].rollback();
                        transactionHistory[p.number - 1] = null;
                        p.isProcessed = false;
                        break;
                    }
                }
                // make multiple part appear
                max_cb.setChecked(false);

                // toggle button
                rollbackBtn.setEnabled(false);
                commitBtn.setEnabled(true);
                nextRoundBtn.setEnabled(false);
                calculatorLayout.setVisibility(LinearLayout.VISIBLE);

                // show toast
                Toast.makeText(ScoringActivity.this, R.string.rollback_toast, Toast.LENGTH_SHORT).show();

                // update list view
                adapter.notifyDataSetChanged();
            }
        });

        final Intent intent = new Intent(getApplicationContext(), ChooseWinnerActivity.class);
        nextRoundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Player p : playerList) {
                    p.resetUI();
                }
                DataModel.persistAllPlayers(ScoringActivity.this, playerList);
                startActivity(intent);
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
