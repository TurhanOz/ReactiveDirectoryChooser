package com.turhanoz.android.reactivedirectorychooser.model;


import java.io.File;
import java.util.Comparator;

public class FileComparator  implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        return o1.getAbsolutePath().compareTo(o2.getAbsolutePath());
    }
}
