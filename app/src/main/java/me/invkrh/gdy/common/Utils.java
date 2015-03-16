package me.invkrh.gdy.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import me.invkrh.gdy.R;

/**
 * Created by invkrh on 2015/3/16.
 */

public class Utils {
    public static int getPlayerNumber (Context ctx) {
        String fileKey = ctx.getString(R.string.gdy_file_key);
        String playerNumberKey = ctx.getString(R.string.num_player);
        SharedPreferences sharedPref = ctx.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
        int num_player = sharedPref.getInt(playerNumberKey, -1);
        if (num_player == -1) {
            throw new IllegalStateException("number of player can not be negative, sharedPreference might be broken.");
        }
        return num_player;
    }
}
