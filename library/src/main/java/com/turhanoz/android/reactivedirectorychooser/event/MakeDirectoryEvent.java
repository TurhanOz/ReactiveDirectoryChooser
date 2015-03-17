package com.turhanoz.android.reactivedirectorychooser.event;


import java.io.File;

public class MakeDirectoryEvent {
    public File root;
    public String name;

    public MakeDirectoryEvent(File root, String name) {
        this.root = root;
        this.name = name;
    }
}
