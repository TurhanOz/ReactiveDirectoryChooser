package com.turhanoz.android.reactivedirectorychooser.observer;


import android.util.Log;

import com.turhanoz.android.reactivedirectorychooser.event.DataSetChangedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Observer;

public class ListDirectoryObserver implements Observer<File> {
    DirectoryTree dataSet;
    EventBus bus;

    public ListDirectoryObserver(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;

        dataSet.directoryList.clear();
    }

    @Override
    public void onCompleted() {
        dataSet.directoryList.sort();
        if(dataSet.getParentDirectory() !=null) {
            dataSet.directoryList.add(0, dataSet.getParentDirectory());
        }
        bus.post(new DataSetChangedEvent());
        Log.d("TAG", "onCompleted ListDirectoryObserver");
    }

    @Override
    public void onError(Throwable e) {
        Log.d("TAG", "onError : " + e.toString());
        bus.post(new OperationFailedEvent());
    }

    @Override
    public void onNext(File file) {
        dataSet.directoryList.add(file);
    }
}
