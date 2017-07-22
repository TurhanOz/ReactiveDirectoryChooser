package com.turhanoz.android.reactivedirectorychooser.operation;

import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.reactivedirectorychooser.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;
import io.reactivex.disposables.CompositeDisposable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk=21, manifest = "library/src/main/AndroidManifest.xml")
public class MakeDirectoryOperationTest {
    MakeDirectoryOperation sut;
    DirectoryTree directoryTree;
    EventBus mockBus;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        directoryTree = new DirectoryTree(mockBus);
        sut = new MakeDirectoryOperation(directoryTree, mockBus);

    }

    @Test
    public void shouldCreateNewDisposable() throws Exception {
        sut.compute(mock(File.class), "");

        assertEquals(1, sut.disposables.size());
    }
}