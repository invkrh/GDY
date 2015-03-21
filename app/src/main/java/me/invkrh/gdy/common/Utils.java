package me.invkrh.gdy.common;

import android.app.Activity;
import android.content.Context;
import android.widget.NumberPicker;

import java.util.Arrays;

/**
 * Created by invkrh on 2015/3/20.
 */

public class Utils {
    public static int safeLongToInt(long l) {
        if (l < Integer.MIN_VALUE || l > Integer.MAX_VALUE) {
            throw new IllegalArgumentException
                    (l + " cannot be cast to int without changing its value.");
        }
        return (int) l;
    }

    public static int getNumberPickerValue(Activity ctx, int id) {
        NumberPicker np = (NumberPicker) ctx.findViewById(id);
        int valIdx = np.getValue();
        String[] valArr = np.getDisplayedValues();
        return Integer.parseInt(valArr[valIdx]);
    }

    public static NumberPicker setNumberPicker(Activity cnt, int id, int value, String[] displayedValues) {
        int valueIdx = Arrays.asList(displayedValues).indexOf(Integer.toString(value));
        int min = 0;
        int max = displayedValues.length - 1;

        NumberPicker np = (NumberPicker) cnt.findViewById(id);
        np.setDisplayedValues(displayedValues);
        np.setMinValue(min);
        np.setMaxValue(max);
        np.setValue(valueIdx);
        np.setWrapSelectorWheel(true);
        return np;
    }
}
