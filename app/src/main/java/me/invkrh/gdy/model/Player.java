package me.invkrh.gdy.model;

import android.graphics.Color;

import me.invkrh.gdy.common.DataModel;
import me.invkrh.gdy.common.Utils;

/**
 * Created by invkrh on 2015/3/15.
 */

public class Player {

    public int number = 0;
    public int color = 0;
    public String name = "";
    public int points = 0;
    public boolean isWinner = false;
    public boolean isInProcess = false;

    public Player(int p_number, int p_color, String p_name, int p_points) {
        number = p_number;
        color = p_color;
        name = p_name;
        points = p_points;
    }

}
