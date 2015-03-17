package com.turhanoz.android.reactivedirectorychooser.observable;

import java.io.File;

import rx.Observable;
import rx.functions.Func1;

public class ListDirectoryObservable extends ListFileObservable {

    public Observable<File> create(final File rootDirectory) {
        return super.create(rootDirectory)
                .filter(isDirectory());
    }

    private Func1<File, Boolean> isDirectory() {
        return new Func1<File, Boolean>() {
            @Override
            public Boolean call(File file) {
                return file.isDirectory();
            }
        };
    }

}