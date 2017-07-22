package com.turhanoz.android.reactivedirectorychooser.observable;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class ListFileObservable {
    public Observable<File> create(final File rootDirectory) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                File[] childDirectories = rootDirectory.listFiles();
                for (File child : childDirectories) {
                    e.onNext(child);
                }
                e.onComplete();
            }
        });
    }
}
