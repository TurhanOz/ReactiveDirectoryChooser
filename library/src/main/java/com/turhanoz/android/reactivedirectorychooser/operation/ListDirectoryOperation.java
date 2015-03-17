package com.turhanoz.android.reactivedirectorychooser.operation;

import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.model.CustomFile;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.android.reactivedirectorychooser.observable.ListDirectoryObservable;
import com.turhanoz.android.reactivedirectorychooser.observer.ListDirectoryObserver;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ListDirectoryOperation {
    Subscription subscription;
    DirectoryTree dataSet;
    EventBus bus;

    public ListDirectoryOperation(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
    }

    public void compute(File rootDirectory) {
        if (rootDirectory.canRead()) {
            cancelPreviousOperation();
            updateDataSet(rootDirectory);
            Observable<File> observable = new ListDirectoryObservable().create(rootDirectory);
            Observer<File> observer = new ListDirectoryObserver(dataSet, bus);

            subscription = observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(observer);
        } else {
            bus.post(new OperationFailedEvent());
        }


    }

    public void cancelPreviousOperation() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
        subscription = null;
    }

    private void updateDataSet(File rootDirectory) {
        dataSet.setRootDirectoryAndNotify(rootDirectory);
        updateParentDirectory(rootDirectory);
    }

    private void updateParentDirectory(File rootDirectory) {
        File parentDirectory = rootDirectory.getParentFile();
        dataSet.setParentDirectory(parentDirectory);
        if (parentDirectory != null) {
            CustomFile customParentDirectory = new CustomFile(parentDirectory.getPath());
            customParentDirectory.setName("../");
            dataSet.setParentDirectory(customParentDirectory);
        }

    }
}
