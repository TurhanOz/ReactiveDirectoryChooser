package com.turhanoz.android.reactivedirectorychooser.operation;

import com.turhanoz.android.reactivedirectorychooser.model.CustomFile;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.reactivedirectorychooser.BuildConfig;

import junit.framework.Assert;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;

import static junit.framework.Assert.assertNull;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21, manifest = "library/src/main/AndroidManifest.xml")
public class ListDirectoryOperationTest {
    ListDirectoryOperation sut;
    DirectoryTree directoryTree;
    EventBus mockBus;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        directoryTree = new DirectoryTree(mockBus);
        sut = new ListDirectoryOperation(directoryTree, mockBus);

    }

    @Test
    public void shouldCreateNewDisposableWhenRootDirectoryCouldBeRead() throws Exception {
        File stubRootFile = mock(File.class);
        when(stubRootFile.canRead()).thenReturn(true);

        sut.compute(stubRootFile);

        Assert.assertEquals(1, sut.disposables.size());
    }

    @Test
    public void shouldNotCreateAddNewDisposableWhenRootDirectoryCouldNotBeRead() throws Exception {
        File stubRootFile = mock(File.class);
        when(stubRootFile.canRead()).thenReturn(false);

        sut.compute(stubRootFile);

        Assert.assertEquals(0, sut.disposables.size());
    }

    @Test
    public void shouldUpdateDirectoryTreeRootAndParentIfExists() throws Exception {
        File stubCurrentDirectory = mock(File.class);
        File parentDirectory = mock(File.class);
        when(parentDirectory.getPath()).thenReturn("/some/parent/path");
        CustomFile expectedParentDirectory = new CustomFile(parentDirectory.getPath());
        when(stubCurrentDirectory.canRead()).thenReturn(true);
        when(stubCurrentDirectory.getParentFile()).thenReturn(parentDirectory);

        sut.compute(stubCurrentDirectory);

        assertThat(directoryTree.getRoot(), is(stubCurrentDirectory));
        assertThat(directoryTree.getParentDirectory(), Is.<File>is(expectedParentDirectory));
    }

    @Test
    public void shouldOnlyUpdateDirectoryTreeRootAndNotParentIfDoesNotExist() throws Exception {
        File stubCurrentDirectory = mock(File.class);

        when(stubCurrentDirectory.canRead()).thenReturn(true);
        when(stubCurrentDirectory.getParentFile()).thenReturn(null);

        sut.compute(stubCurrentDirectory);

        assertThat(directoryTree.getRoot(), is(stubCurrentDirectory));
        assertNull(directoryTree.getParentDirectory());
    }
}