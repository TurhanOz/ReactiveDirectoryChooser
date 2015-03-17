package com.turhanoz.android.reactivedirectorychooser.utils;


import android.content.Context;
import android.util.DisplayMetrics;

public class ConvertUtils {
    public static int convertDpToPixel(Context context, int dp){
        float scaleFactor = (1.0f / DisplayMetrics.DENSITY_DEFAULT) * context.getResources().getDisplayMetrics().densityDpi;

        return (int)(dp * scaleFactor);
    }
}
