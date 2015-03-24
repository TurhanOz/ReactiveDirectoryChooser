package com.turhanoz.android.reactivedirectorychooser.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.turhanoz.android.reactivedirectorychooser.event.DataSetChangedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.MakeDirectoryEvent;
import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.operation.ListDirectoryOperation;
import com.turhanoz.android.reactivedirectorychooser.operation.MakeDirectoryOperation;
import com.turhanoz.android.reactivedirectorychooser.ui.DirectoryAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;

import de.greenrobot.event.EventBus;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class DirectoryControllerTest {
    DirectoryController sut;
    EventBus mockBus;


    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        Context context = Robolectric.getShadowApplication().getApplicationContext();
        sut = new DirectoryController(context, mockBus, mock(RecyclerView.class));

        sut.makeDirectoryOperation = mock(MakeDirectoryOperation.class);
        sut.listDirectoryOperation = mock(ListDirectoryOperation.class);
    }

    @Test
    public void onDataSetChangedEventShouldNotifyAdapter() throws Exception {
        sut.adapter = mock(DirectoryAdapter.class);

        sut.onEvent(new DataSetChangedEvent());

        verify(sut.adapter).notifyDataSetChanged();
    }

    @Test
    public void shouldCollaborateWithListDirectoryOperation() throws Exception {
        File mockRootDirectory = mock(File.class);
        UpdateDirectoryTreeEvent event = new UpdateDirectoryTreeEvent(mockRootDirectory);

        sut.onEvent(event);

        verify(sut.listDirectoryOperation).compute(mockRootDirectory);
    }

    @Test
    public void shouldCollaborateWithMakeDirectoryOperation() throws Exception {
        File mockRootDirectory = mock(File.class);
        MakeDirectoryEvent event = new MakeDirectoryEvent(mockRootDirectory, "newDirectory");

        sut.onEvent(event);

        verify(sut.makeDirectoryOperation).compute(mockRootDirectory, "newDirectory");
    }
}