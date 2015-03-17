package com.turhanoz.android.reactivedirectorychooser.utils;

import android.util.DisplayMetrics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class ConvertUtilsTest {
    @Test
    public void shouldConvertDpToPixel() throws Exception {
        int dp = 20;
        DisplayMetrics displayMetrics = Robolectric.getShadowApplication().getResources().getDisplayMetrics();

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_MEDIUM;
        shouldConvertDpToPixel(dp, 20);

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_HIGH;
        shouldConvertDpToPixel(dp, 30);

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_XHIGH;
        shouldConvertDpToPixel(dp, 40);
    }


    private void shouldConvertDpToPixel(int dp, int expectedPx) throws Exception {
        int px = ConvertUtils.convertDpToPixel(Robolectric.getShadowApplication().getApplicationContext(), dp);
        assertEquals(expectedPx, px);
    }

}