package com.turhanoz.android.reactivedirectorychooser.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

public class DirectoryList extends ArrayList<File> {

    public void sort() {
        Collections.sort(this, new FileComparator());
    }
}
