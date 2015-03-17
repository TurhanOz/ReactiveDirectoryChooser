package com.turhanoz.android.reactivedirectorychooser.operation;


import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.android.reactivedirectorychooser.observable.MakeDirectoryObservable;
import com.turhanoz.android.reactivedirectorychooser.observer.MakeDirectoryObserver;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MakeDirectoryOperation {
    Subscription subscription;
    DirectoryTree dataSet;
    EventBus bus;

    public MakeDirectoryOperation(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
    }

    public void compute(File rootDirectory, String name) {
        cancelPreviousOperation();

        Observable<File> observable = new MakeDirectoryObservable().create(rootDirectory, name);
        Observer<File> observer = new MakeDirectoryObserver(dataSet, bus);

        subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void cancelPreviousOperation() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

}
