package com.turhanoz.android.reactivedirectorychooser.observable;

import com.turhanoz.android.reactivedirectorychooser.exception.DirectoryExistsException;
import com.turhanoz.android.reactivedirectorychooser.exception.PermissionDeniedException;

import java.io.File;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;


public class MakeDirectoryObservable {
    public Observable<File> create(final File rootDirectory, final String directoryName) {
        return Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                if (!rootDirectory.canWrite()) {
                    subscriber.onError(new PermissionDeniedException());
                }
                File newDirectory = new File(rootDirectory, directoryName);

                if (newDirectory.exists()) {
                    subscriber.onError(new DirectoryExistsException());
                } else {
                    boolean isDirectoryCreated = newDirectory.mkdir();
                    if (isDirectoryCreated) {
                        subscriber.onNext(newDirectory);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new IOException());
                    }
                }
            }
        });
    }
}
