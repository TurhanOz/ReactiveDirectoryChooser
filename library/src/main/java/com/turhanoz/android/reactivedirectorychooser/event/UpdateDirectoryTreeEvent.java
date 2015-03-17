package com.turhanoz.android.reactivedirectorychooser.event;


import java.io.File;

public class UpdateDirectoryTreeEvent {
    public File rootDirectory;

    public UpdateDirectoryTreeEvent(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateDirectoryTreeEvent that = (UpdateDirectoryTreeEvent) o;

        if (rootDirectory != null ? !rootDirectory.equals(that.rootDirectory) : that.rootDirectory != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return rootDirectory != null ? rootDirectory.hashCode() : 0;
    }
}
