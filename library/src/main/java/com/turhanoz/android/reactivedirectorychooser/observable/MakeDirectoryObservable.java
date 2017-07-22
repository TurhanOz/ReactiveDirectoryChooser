package com.turhanoz.android.reactivedirectorychooser.observable;

import com.turhanoz.android.reactivedirectorychooser.exception.DirectoryExistsException;
import com.turhanoz.android.reactivedirectorychooser.exception.PermissionDeniedException;

import java.io.File;
import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


public class MakeDirectoryObservable {
    public Observable<File> create(final File rootDirectory, final String directoryName) {
        return Observable.create(new ObservableOnSubscribe<File>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<File> e) throws Exception {
                if (!rootDirectory.canWrite()) {
                    e.onError(new PermissionDeniedException());
                }
                File newDirectory = new File(rootDirectory, directoryName);

                if (newDirectory.exists()) {
                    e.onError(new DirectoryExistsException());
                } else {
                    boolean isDirectoryCreated = newDirectory.mkdir();
                    if (isDirectoryCreated) {
                        e.onNext(newDirectory);
                        e.onComplete();
                    } else {
                        e.onError(new IOException());
                    }
                }
            }
        });
    }
}
