package com.turhanoz.android.reactivedirectorychooser.model;

import com.turhanoz.android.reactivedirectorychooser.event.CurrentRootDirectoryChangedEvent;

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
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class DirectoryTreeTest{
   DirectoryTree sut;
    EventBus mockBus;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        sut = new DirectoryTree(mockBus);
    }

    @Test
    public void changingRootDirectoryShouldNotifyThroughBus() throws Exception {
        File mockRootDirectory = mock(File.class);
        CurrentRootDirectoryChangedEvent expectedEvent = new CurrentRootDirectoryChangedEvent(mockRootDirectory);

        sut.setRootDirectoryAndNotify(mockRootDirectory);

        verify(mockBus).post(eq(expectedEvent));
    }
}