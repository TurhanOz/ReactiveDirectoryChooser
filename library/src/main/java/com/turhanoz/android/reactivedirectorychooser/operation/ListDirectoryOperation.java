package com.turhanoz.android.reactivedirectorychooser.operation;

import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.model.CustomFile;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.android.reactivedirectorychooser.observable.ListDirectoryObservable;
import com.turhanoz.android.reactivedirectorychooser.observer.ListDirectoryObserver;

import java.io.File;

import de.greenrobot.event.EventBus;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class ListDirectoryOperation {
    final CompositeDisposable disposables;
    DirectoryTree dataSet;
    EventBus bus;

    public ListDirectoryOperation(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
        disposables = new CompositeDisposable();
    }

    public void compute(File rootDirectory) {
        if (rootDirectory.canRead()) {
            cancelPreviousOperation();
            updateDataSet(rootDirectory);
            Observable<File> observable = new ListDirectoryObservable().create(rootDirectory);
            DisposableObserver<File> observer = new ListDirectoryObserver(dataSet, bus);

            disposables.add(observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(observer));
        } else {
            bus.post(new OperationFailedEvent());
        }


    }

    public void cancelPreviousOperation() {
        if (disposables.size() > 0) {
            disposables.clear();
        }
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
