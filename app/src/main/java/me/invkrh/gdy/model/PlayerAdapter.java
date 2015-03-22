package me.invkrh.gdy.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import me.invkrh.gdy.R;

/**
 * Created by invkrh on 2015/3/16.
 */

public class PlayerAdapter extends ArrayAdapter<Player> {
    // View lookup cache
    private static class ViewHolder {
        TextView name;
        TextView color;
        TextView points;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showHighlightItem(View itemView) {
        itemView.setBackground(this.getContext().getResources().getDrawable(R.drawable.abc_list_selector_disabled_holo_light));
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void hideHighlightItem(View itemView) {
        itemView.setBackground(this.getContext().getResources().getDrawable(R.drawable.abc_list_selector_holo_light));
    }

    public PlayerAdapter(Context context, ArrayList<Player> players) {
        super(context, 0, players);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Player player = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.detailed_player_item, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.player_name);
            viewHolder.color = (TextView) convertView.findViewById(R.id.player_color);
            viewHolder.points = (TextView) convertView.findViewById(R.id.player_points);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Populate the data into the template view using the data object
        viewHolder.color.setBackgroundColor(player.color);
        viewHolder.points.setText(player.points + "");
        Drawable bg = convertView.getBackground();
        if (player.isWinner) {
            viewHolder.name.setText("\u265A \u2192" + player.name);
            showHighlightItem(convertView);
        } else {

            if (player.isProcessed) {
                viewHolder.name.setText("\u221A \u2192 " + player.name);
            } else {
                viewHolder.name.setText(player.name);
            }

            if (player.isSelected)
                showHighlightItem(convertView);
            else
                hideHighlightItem(convertView);
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
