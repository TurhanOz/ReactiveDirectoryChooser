package com.turhanoz.android.reactivedirectorychooser.observer;

import com.turhanoz.android.reactivedirectorychooser.event.DataSetChangedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryList;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class ListDirectoryObserverTest {
    ListDirectoryObserver sut;
    EventBus mockBus;
    DirectoryTree directoryTree;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        directoryTree = new DirectoryTree(mockBus);
        sut = new ListDirectoryObserver(directoryTree, mockBus);
    }

    @Test
    public void shouldClearDirectoryTreeOnInstanciation() throws Exception {
        directoryTree.directoryList.add(mock(File.class));

        new ListDirectoryObserver(directoryTree, mockBus);

        assertTrue(directoryTree.directoryList.isEmpty());
    }

    @Test
    public void onNextShouldUpdateDirectoryTree() throws Exception {
        File mockFile1 = mock(File.class);
        File mockFile2 = mock(File.class);
        File mockFile3 = mock(File.class);

        sut.onNext(mockFile1);
        sut.onNext(mockFile2);
        sut.onNext(mockFile3);

        assertTrue(directoryTree.directoryList.contains(mockFile1));
        assertTrue(directoryTree.directoryList.contains(mockFile2));
        assertTrue(directoryTree.directoryList.contains(mockFile3));
    }

    @Test
    public void onErrorShouldNotifyOperationFailedEvent() throws Exception {
        sut.onError(new Exception());

        verify(mockBus).post(any(OperationFailedEvent.class));
    }

    @Test
    public void onCompleteShouldSortDirectoryTree() throws Exception {
        DirectoryList spyDirectoryList = spy(new DirectoryList());
        directoryTree.directoryList = spyDirectoryList;

        sut.onCompleted();

        verify(directoryTree.directoryList).sort();
    }

    @Test
    public void onCompleteShouldAddParentDirectoryIfExists() throws Exception {
        File mockParentFile = mock(File.class);
        directoryTree.setParentDirectory(mockParentFile);

        sut.onCompleted();

        assertThat(directoryTree.directoryList.get(0), is(mockParentFile));
    }

    @Test
    public void onCompleteShouldNotAddMissingParent() throws Exception {
        directoryTree.setParentDirectory(null);

        sut.onCompleted();

        assertTrue(directoryTree.directoryList.isEmpty());
    }

    @Test
    public void onCompleteShouldNotifyDataSetChanged() throws Exception {
        sut.onCompleted();

        verify(mockBus).post(any(DataSetChangedEvent.class));
    }
}