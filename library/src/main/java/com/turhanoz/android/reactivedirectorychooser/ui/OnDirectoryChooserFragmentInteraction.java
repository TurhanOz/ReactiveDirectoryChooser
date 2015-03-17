package com.turhanoz.android.reactivedirectorychooser.ui;


import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryCancelEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryChosenEvent;

public interface OnDirectoryChooserFragmentInteraction {

    public void onEvent(OnDirectoryChosenEvent event);

    public void onEvent(OnDirectoryCancelEvent event);
}
