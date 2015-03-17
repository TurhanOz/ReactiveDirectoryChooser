package com.turhanoz.android.reactivedirectorychoosersample;

/**
 * android API getSupportFragmentManager().getFragments() is not working !
 * Instead of creating value, we waste time trying to solve or workaround public API issues...
 * Thanks
 */
public class FragmentCounter {
    private int count = 0;

    public FragmentCounter(int initialCount) {
        this.count = initialCount;
    }

    public void fragmentAdded(){
        count++;
    }

    public void fragmentRemoved(){
        count--;
    }

    public int getCount() {
        return count;
    }
}
