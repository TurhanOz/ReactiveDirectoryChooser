package com.turhanoz.android.reactivedirectorychooser.operation;

import com.turhanoz.android.reactivedirectorychooser.model.CustomFile;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;
import rx.Subscription;

import static junit.framework.Assert.assertNull;
import static junit.framework.TestCase.assertSame;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
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
    public void shouldCreateNewSubscriptionWhenRootDirectoryCouldBeRead() throws Exception {
        Subscription mockSubscription = mock(Subscription.class);
        sut.subscription = mockSubscription;
        File stubRootFile = mock(File.class);
        when(stubRootFile.canRead()).thenReturn(true);

        sut.compute(stubRootFile);

        assertNotSame(mockSubscription, sut.subscription);
    }

    @Test
    public void shouldNotCreateNewSubscriptionWhenRootDirectoryCouldNotBeRead() throws Exception {
        Subscription mockSubscription = mock(Subscription.class);
        sut.subscription = mockSubscription;
        File stubRootFile = mock(File.class);
        when(stubRootFile.canRead()).thenReturn(false);

        sut.compute(stubRootFile);

        assertSame(mockSubscription, sut.subscription);
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