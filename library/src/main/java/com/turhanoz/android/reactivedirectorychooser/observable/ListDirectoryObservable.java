package com.turhanoz.android.reactivedirectorychooser.observable;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;


public class ListDirectoryObservable extends ListFileObservable {

    public Observable<File> create(final File rootDirectory) {
        return super.create(rootDirectory)
                .filter(isDirectory());
    }

    private Predicate<File> isDirectory() {
        return new Predicate<File>() {
            @Override
            public boolean test(@NonNull File file) throws Exception {
                return file.isDirectory();
            }
        };
    }


}