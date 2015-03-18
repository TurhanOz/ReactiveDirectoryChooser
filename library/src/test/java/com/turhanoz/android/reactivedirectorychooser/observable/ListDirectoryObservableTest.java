package com.turhanoz.android.reactivedirectorychooser.observable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class ListDirectoryObservableTest {
    ListDirectoryObservable sut;
    Observer mockObserver;

    @Before
    public void setUp() throws Exception {
        sut = new ListDirectoryObservable();
        mockObserver = mock(Observer.class);
    }

    @Test
    public void observableShouldReturnOnlyChildDirectories() throws Exception {
        File stubRootDirectory = mock(File.class);
        File childDirectory1 = createStubDirectory();
        File childFile1 = createStubFile();
        File childDirectory2 = createStubDirectory();
        File childrenContent[] = {childDirectory1, childFile1, childDirectory2};
        when(stubRootDirectory.listFiles()).thenReturn(childrenContent);

        sut.create(stubRootDirectory)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mockObserver);

        verify(mockObserver).onNext(childDirectory1);
        verify(mockObserver).onNext(childDirectory2);
        verify(mockObserver, times(2)).onNext(any(File.class));
        verify(mockObserver, never()).onNext(childFile1);
        verify(mockObserver).onCompleted();
    }

    private File createStubDirectory(){
        File stubDirectory = createStubFile();
        when(stubDirectory.isDirectory()).thenReturn(true);
        return stubDirectory;
    }

    private File createStubFile(){
        File stubFile = mock(File.class);
        return stubFile;
    }
}