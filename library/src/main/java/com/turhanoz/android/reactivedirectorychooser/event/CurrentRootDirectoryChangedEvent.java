package com.turhanoz.android.reactivedirectorychooser.event;


import java.io.File;

public class CurrentRootDirectoryChangedEvent {
    File currentDirectory;

    public CurrentRootDirectoryChangedEvent(File currentDirectory) {
        this.currentDirectory = currentDirectory;
    }

    public File getCurrentDirectory() {
        return currentDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CurrentRootDirectoryChangedEvent that = (CurrentRootDirectoryChangedEvent) o;

        if (currentDirectory != null ? !currentDirectory.equals(that.currentDirectory) : that.currentDirectory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return currentDirectory != null ? currentDirectory.hashCode() : 0;
    }
}
