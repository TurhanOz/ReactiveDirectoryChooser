package com.turhanoz.reactivedirectorychooser.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.turhanoz.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.reactivedirectorychooser.model.DirectoryTree;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.io.File;

import de.greenrobot.event.EventBus;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class DirectoryAdapterTest {
    DirectoryAdapter sut;
    DirectoryTree dataSet;
    EventBus mockBus;

    @Before
    public void setUp() throws Exception {
        mockBus = mock(EventBus.class);
        dataSet = new DirectoryTree(mockBus);
        sut = new DirectoryAdapter(dataSet.directoryList, mockBus);
    }

    @Test
    public void onFileClickedShouldUpdateDirectoryTree() throws Exception {
        File expectedClickedFile = mock(File.class);
        ViewGroup fakeViewGroup = new RelativeLayout(Robolectric.getShadowApplication().getApplicationContext());
        DirectoryAdapter.ViewHolder viewHolder = spy(sut.onCreateViewHolder(fakeViewGroup, 0));
        when(viewHolder.getPosition()).thenReturn(0);
        dataSet.directoryList.add(expectedClickedFile);

        viewHolder.fileClicked(mock(View.class));

        verify(mockBus).post(eq(new UpdateDirectoryTreeEvent(expectedClickedFile)));
    }
}