package com.turhanoz.android.reactivedirectorychooser.observable;

import com.turhanoz.android.reactivedirectorychooser.exception.DirectoryExistsException;
import com.turhanoz.android.reactivedirectorychooser.exception.PermissionDeniedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowEnvironment;

import java.io.File;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class MakeDirectoryObservableTest {
    MakeDirectoryObservable sut;
    Observer mockObserver;

    @Before
    public void setUp() throws Exception {
        sut = new MakeDirectoryObservable();
        mockObserver = mock(Observer.class);
    }

    @Test
    public void shouldThroughPermissionDeniedExceptionIfRootFolderNotWritable() throws Exception {
        File stubRootDirectory = mock(File.class);
        when(stubRootDirectory.canWrite()).thenReturn(false);

        sut.create(stubRootDirectory, "fileName")
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mockObserver);

        verify(mockObserver).onError(any(PermissionDeniedException.class));
        verify(mockObserver, never()).onCompleted();
    }

    @Test
    public void shouldThroughDirectoryExistsException() throws Exception {
        File rootDirectory = ShadowEnvironment.getExternalStorageDirectory();
        String folderName = "folderName";
        createDirectory(rootDirectory, folderName);

        sut.create(rootDirectory, folderName)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mockObserver);

        verify(mockObserver).onError(any(DirectoryExistsException.class));
        verify(mockObserver, never()).onCompleted();
    }

    private void createDirectory(File rootDirectory, String folderName) {
        File newDirectory = new File(rootDirectory, folderName);
        newDirectory.mkdir();
    }

    @Test
    public void shouldMakeDirectory() throws Exception {
        File rootDirectory = ShadowEnvironment.getExternalStorageDirectory();
        String folderName = "folderName";

        sut.create(rootDirectory, folderName)
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mockObserver);

        ArgumentCaptor<File> argumentCaptor = ArgumentCaptor.forClass(File.class);
        verify(mockObserver).onNext(argumentCaptor.capture());
        verify(mockObserver).onCompleted();
        assertEquals(rootDirectory.toString() + File.separator + folderName, argumentCaptor.getValue().getAbsolutePath());
    }
}