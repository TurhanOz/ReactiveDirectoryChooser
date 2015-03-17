package com.turhanoz.android.reactivedirectorychooser.model;


import android.support.annotation.NonNull;

import java.io.File;
import java.net.URI;

public class CustomFile extends File
{
    String name;
    public CustomFile(File dir, String name) {
        super(dir, name);
    }

    public CustomFile(String path) {
        super(path);
    }

    public CustomFile(String dirPath, String name) {
        super(dirPath, name);
    }

    public CustomFile(URI uri) {
        super(uri);
    }

    public void setName(String name){
        this.name = name;
    }

    @NonNull
    @Override
    public String getName() {
        if(this.name != null){
            return this.name;
        }
        return super.getName();
    }
}
