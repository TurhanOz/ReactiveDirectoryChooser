package com.turhanoz.android.reactivedirectorychooser.observer;

import android.util.Log;

import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Observer;


public class MakeDirectoryObserver implements Observer<File> {
    DirectoryTree dataSet;
    EventBus bus;
    File createdDirectory;

    public MakeDirectoryObserver(DirectoryTree dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
    }

    @Override
    public void onCompleted() {
        if(isCreatedDirectoryInCurrentRootDirectory()){
            bus.post(new UpdateDirectoryTreeEvent(dataSet.getRoot()));
        }
        Log.d("TAG", "onCompleted MakeDirectoryObserver");
    }

    private boolean isCreatedDirectoryInCurrentRootDirectory(){
        if(createdDirectory == null){
            return false;
        }

        File rootDirectory = dataSet.getRoot();
        File parentDirectoryOfCreatedDirectory = createdDirectory.getParentFile();

        return rootDirectory.getAbsolutePath().equals(parentDirectoryOfCreatedDirectory.getAbsolutePath());
    }

    @Override
    public void onError(Throwable e) {
        Log.d("TAG", "onError : " + e.toString());
        bus.post(new OperationFailedEvent());
    }

    @Override
    public void onNext(File file) {
        createdDirectory = file;
    }
}
