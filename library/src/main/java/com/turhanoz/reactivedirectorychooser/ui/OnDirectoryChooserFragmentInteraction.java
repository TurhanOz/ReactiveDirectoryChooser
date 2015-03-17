package com.turhanoz.reactivedirectorychooser.ui;


import com.turhanoz.reactivedirectorychooser.event.OnDirectoryCancelEvent;
import com.turhanoz.reactivedirectorychooser.event.OnDirectoryChosenEvent;

public interface OnDirectoryChooserFragmentInteraction {

    public void onEvent(OnDirectoryChosenEvent event);

    public void onEvent(OnDirectoryCancelEvent event);
}
