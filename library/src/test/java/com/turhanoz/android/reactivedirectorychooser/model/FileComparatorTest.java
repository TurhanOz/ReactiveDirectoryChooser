package com.turhanoz.android.reactivedirectorychooser.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = "library/src/main/AndroidManifest.xml", emulateSdk = 18)
public class FileComparatorTest{

    @Test
    public void shouldProperlySortList() throws Exception {
        ArrayList<File> files = new ArrayList<File>();

        populateFile(files, "a");
        populateFile(files, "i");
        populateFile(files, "B");
        populateFile(files, ".a");

        Collections.shuffle(files);
        Collections.sort(files, new FileComparator());

        assertThat(files.get(0).getAbsolutePath(), is(".a"));
        assertThat(files.get(1).getAbsolutePath(), is("B"));
        assertThat(files.get(2).getAbsolutePath(), is("a"));
        assertThat(files.get(3).getAbsolutePath(), is("i"));
    }

    private void populateFile(ArrayList<File> files, String absolutePath) {
        File stubFile = mock(File.class);
        when(stubFile.getAbsolutePath()).thenReturn(absolutePath);
        files.add(stubFile);
    }

}