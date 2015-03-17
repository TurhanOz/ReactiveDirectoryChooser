package com.turhanoz.android.reactivedirectorychooser.observable;

import java.io.File;

import rx.Observable;
import rx.Subscriber;

public class ListFileObservable {
    public Observable<File> create(final File rootDirectory) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                File[] childDirectories = rootDirectory.listFiles();
                for (File child : childDirectories) {
                    subscriber.onNext(child);
                }
                subscriber.onCompleted();
            }
        });
    }
}
