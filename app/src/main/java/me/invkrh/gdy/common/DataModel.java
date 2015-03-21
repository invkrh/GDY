package me.invkrh.gdy.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import me.invkrh.gdy.R;
import me.invkrh.gdy.model.Player;

/**
 * Created by invkrh on 2015/3/16.
 */

public class DataModel {
    private static SharedPreferences getSharedPref(Context ctx) {
        String fileKey = ctx.getString(R.string.gdy_file_key);
        return ctx.getSharedPreferences(fileKey, Context.MODE_PRIVATE);
    }

    public static void persistPlayer(Context ctx, Player player) {
        SharedPreferences.Editor editor = getSharedPref(ctx).edit();
        editor.putString(player.number + "", new Gson().toJson(player));
        editor.apply();
    }

    public static Player getPlayerById(Context ctx, int index) {
        String playerStr = getSharedPref(ctx).getString(index + "", "");
        Player retVal = null;
        if (playerStr.equals("")) {
            throw new IllegalStateException("Can not retrieve player " + index);
        } else {
            retVal = new Gson().fromJson(playerStr, Player.class);
        }
        return retVal;
    }

//    public static String getPlayerNameById(Context ctx, int index) {
//        String playerStr = getSharedPref(ctx).getString(index + "", "");
//        String retVal = "";
//        if (!playerStr.equals("")) {
//            retVal = new Gson().fromJson(playerStr, Player.class).name;
//        }
//        return retVal;
//    }

    public static void persistAllPlayers(Context ctx, List<Player> playerList) {
        SharedPreferences.Editor editor = getSharedPref(ctx).edit();
        for (Player p : playerList) {
            editor.putString(p.number + "", new Gson().toJson(p));
        }
        editor.apply();
    }

    public static ArrayList<Player> restoreAllPlayers(Context ctx) {
        ArrayList<Player> playerList = new ArrayList<Player>();
        SharedPreferences sharedPref = getSharedPref(ctx);
        int player_num = restorePlayerNumber(ctx);
        for (int i = 1; i <= player_num; i++) {
            String playerStr = sharedPref.getString(i + "", "");
            playerList.add(new Gson().fromJson(playerStr, Player.class));
        }
        return playerList;
    }

    public static void persistSettings(Context ctx, int num, int max, int init) {
        SharedPreferences.Editor editor = getSharedPref(ctx).edit();
        editor.putInt(ctx.getString(R.string.num_player), num)
                .putInt(ctx.getString(R.string.max_points), max)
                .putInt(ctx.getString(R.string.init_points), init);
        editor.apply();
    }

    public static int restorePlayerNumber(Context ctx) {
        String playerNumberKey = ctx.getString(R.string.num_player);
        int num_player = getSharedPref(ctx).getInt(playerNumberKey, -1);
        if (num_player == -1) {
            throw new IllegalStateException("number of player can not be negative, sharedPreference might be broken.");
        }
        return num_player;
    }

    public static int restoreInitialPoints(Context ctx) {
        String initPointsKey = ctx.getString(R.string.init_points);
        int initPoints = getSharedPref(ctx).getInt(initPointsKey, -1);
        if (initPoints == -1) {
            throw new IllegalStateException("initial points can not be negative, sharedPreference might be broken.");
        }
        return initPoints;
    }

    public static int restoreMaxPoint(Context ctx) {
        String maxPointKey = ctx.getString(R.string.max_points);
        int max_point = getSharedPref(ctx).getInt(maxPointKey, -1);
        if (max_point == -1) {
            throw new IllegalStateException("number of player can not be negative, sharedPreference might be broken.");
        }
        return max_point;
    }
}
