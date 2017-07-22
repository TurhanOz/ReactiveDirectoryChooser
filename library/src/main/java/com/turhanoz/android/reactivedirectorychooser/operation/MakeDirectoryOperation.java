package com.turhanoz.android.reactivedirectorychooser.operation;


import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.android.reactivedirectorychooser.observable.MakeDirectoryObservable;
import com.turhanoz.android.reactivedirectorychooser.observer.MakeDirectoryObserver;

import java.io.File;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MakeDirectoryOperation {
    private final CompositeDisposable disposables;
    DirectoryTree dataSet;
    EventBus bus;

    public MakeDirectoryOperation(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
        disposables = new CompositeDisposable();

    }

    public void compute(File rootDirectory, String name) {
        cancelPreviousOperation();

        Observable<File> observable = new MakeDirectoryObservable().create(rootDirectory, name);
        DisposableObserver<File> observer = new MakeDirectoryObserver(dataSet, bus);

        disposables.add(observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer));
    }

    public void cancelPreviousOperation() {
        if (disposables.size() > 0) {
            disposables.clear();
        }
    }

}
