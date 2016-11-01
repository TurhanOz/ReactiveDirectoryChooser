package com.turhanoz.android.reactivedirectorychooser.utils;

import android.util.DisplayMetrics;

import com.turhanoz.reactivedirectorychooser.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21, manifest = "library/src/main/AndroidManifest.xml")
public class ConvertUtilsTest {
    @Test
    public void shouldConvertDpToPixel() throws Exception {
        int dp = 20;
        DisplayMetrics displayMetrics = ShadowApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics();

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_MEDIUM;
        shouldConvertDpToPixel(dp, 20);

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_HIGH;
        shouldConvertDpToPixel(dp, 30);

        displayMetrics.densityDpi = DisplayMetrics.DENSITY_XHIGH;
        shouldConvertDpToPixel(dp, 40);
    }


    private void shouldConvertDpToPixel(int dp, int expectedPx) throws Exception {
        int px = ConvertUtils.convertDpToPixel(ShadowApplication.getInstance().getApplicationContext(), dp);
        assertEquals(expectedPx, px);
    }

}