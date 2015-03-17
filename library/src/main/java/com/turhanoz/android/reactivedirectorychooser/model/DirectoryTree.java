package com.turhanoz.android.reactivedirectorychooser.model;


import com.turhanoz.android.reactivedirectorychooser.event.CurrentRootDirectoryChangedEvent;


import java.io.File;

import de.greenrobot.event.EventBus;

public class DirectoryTree {
    File root;
    File parent;
    public DirectoryList directoryList;
    EventBus bus;

    public DirectoryTree(EventBus bus){
        this.bus = bus;
        directoryList = new DirectoryList();
    }

    public void setRootDirectoryAndNotify(File rootDirectory){
        this.root = rootDirectory;
        bus.post(new CurrentRootDirectoryChangedEvent(rootDirectory));
    }

    public void setParentDirectory(File parentDirectory){
        this.parent = parentDirectory;
    }

    public File getRoot() {
        return root;
    }

    public File getParentDirectory() {
        return parent;
    }
}
