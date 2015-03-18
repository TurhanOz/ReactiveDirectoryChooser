package com.turhanoz.android.reactivedirectorychooser.observer;

import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class MakeDirectoryObserverTest {
    MakeDirectoryObserver sut;
    EventBus mockBus;
    DirectoryTree directoryTree;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        directoryTree = new DirectoryTree(mockBus);
        sut = new MakeDirectoryObserver(directoryTree, mockBus);
    }

    @Test
    public void onErrorShouldNotifyOperationFailedEvent() throws Exception {
        sut.onError(new Exception());

        verify(mockBus).post(any(OperationFailedEvent.class));
    }

    @Test
    public void shouldNotifyUpdateDirectoryTreeEventWhenDirectoryCreationIsInCurrentFolder() throws Exception {
        File stubRootDirectory = mock(File.class);
        File stubCreatedDirectory = mock(File.class);
        UpdateDirectoryTreeEvent expectedEvent = new UpdateDirectoryTreeEvent(stubRootDirectory);

        when(stubRootDirectory.getAbsolutePath()).thenReturn("path");
        when(stubCreatedDirectory.getParentFile()).thenReturn(stubRootDirectory);
        directoryTree.setRootDirectoryAndNotify(stubRootDirectory);

        sut.onNext(stubCreatedDirectory);
        sut.onCompleted();

        verify(mockBus).post(eq(expectedEvent));
    }

    @Test
    public void shouldNotNotifyUpdateDirectoryTreeEventWhenDirectoryCreationIsInCurrentFolder() throws Exception {
        File stubRootDirectory = mock(File.class);
        File stubCreatedDirectory = mock(File.class);
        File stubParentDirectorOfCreatedDirectory = mock(File.class);

        when(stubRootDirectory.getAbsolutePath()).thenReturn("path");
        when(stubParentDirectorOfCreatedDirectory.getAbsolutePath()).thenReturn("anotherPath");
        when(stubCreatedDirectory.getParentFile()).thenReturn(stubParentDirectorOfCreatedDirectory);
        directoryTree.setRootDirectoryAndNotify(stubRootDirectory);

        sut.onNext(stubCreatedDirectory);
        sut.onCompleted();

        verify(mockBus, never()).post(eq(UpdateDirectoryTreeEvent.class));
    }
}