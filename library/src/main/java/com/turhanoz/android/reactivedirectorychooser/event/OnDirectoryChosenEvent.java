package com.turhanoz.android.reactivedirectorychooser.event;

import java.io.File;

public class OnDirectoryChosenEvent {
    File file;

    public OnDirectoryChosenEvent(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
