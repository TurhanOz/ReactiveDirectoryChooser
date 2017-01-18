package com.turhanoz.android.reactivedirectorychooser.controller;

import android.text.TextUtils;

import java.io.File;
import java.util.Locale;

public class ExternalStorageController {

    public boolean hasMultipleExternalStorages() {
        return hasExternalPrimaryStorage() && hasExternalSecondaryStorage();
    }

    public boolean hasExternalPrimaryStorage() {
        return getExternalPrimaryStoragePath() != null;
    }

    public boolean hasExternalSecondaryStorage() {
        return getExternalSecondaryStoragePath() != null;
    }

    public String getExternalPrimaryStoragePath() {
        return System.getenv("EXTERNAL_STORAGE");
    }

    public String getExternalSecondaryStoragePath()
    {
        String path = System.getenv("SECONDARY_STORAGE");
        if(TextUtils.isEmpty(path)){
            System.getenv("EXTERNAL_SDCARD_STORAGE");
        }
        return  path;
    }

    public File getPrimaryFileSystem() {
        return new File(getExternalPrimaryStoragePath());
    }

    public File getSecondaryFileSystem() {
        return new File(getExternalSecondaryStoragePath());
    }

    public boolean isFileInPrimaryFileSystem(File file) {
        return isFileInFileSystem(file, hasExternalPrimaryStorage(), getExternalPrimaryStoragePath());
    }

    public boolean isFileInSecondaryFileSystem(File file) {
        return isFileInFileSystem(file, hasExternalSecondaryStorage(), getExternalSecondaryStoragePath());
    }

    private boolean isFileInFileSystem(File file, boolean hasExternalStorage, String externalStoragePath){
        if (file == null)
            return false;
        if (!hasExternalStorage)
            return false;

        return file.getAbsolutePath().toLowerCase(Locale.getDefault()).contains(externalStoragePath);

    }
}
