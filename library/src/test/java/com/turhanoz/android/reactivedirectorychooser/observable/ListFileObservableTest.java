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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class ListFileObservableTest {
    ListFileObservable sut;
    Observer mockObserver;

    @Before
    public void setUp() throws Exception {
        sut = new ListFileObservable();
        mockObserver = mock(Observer.class);
    }

    @Test
    public void shouldReturnAllChildFiles() throws Exception {
        File stubRootDirectory = mock(File.class);
        File mockFile1 = mock(File.class);
        File mockFile2 = mock(File.class);
        File mockFile3 = mock(File.class);
        File childrenContent[] = {mockFile1, mockFile2, mockFile3};
        when(stubRootDirectory.listFiles()).thenReturn(childrenContent);

        sut.create(stubRootDirectory)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mockObserver);

        verify(mockObserver).onNext(mockFile1);
        verify(mockObserver).onNext(mockFile1);
        verify(mockObserver).onNext(mockFile3);
        verify(mockObserver, times(3)).onNext(any(File.class));
        verify(mockObserver).onCompleted();
    }
}