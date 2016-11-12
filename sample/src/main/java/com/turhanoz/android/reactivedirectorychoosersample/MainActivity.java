package com.turhanoz.android.reactivedirectorychoosersample;

import android.Manifest;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryCancelEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryChosenEvent;
import com.turhanoz.android.reactivedirectorychooser.ui.DirectoryChooserFragment;
import com.turhanoz.android.reactivedirectorychooser.ui.OnDirectoryChooserFragmentInteraction;

import java.io.File;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity implements OnDirectoryChooserFragmentInteraction, PermissionListener {
    @InjectView(R.id.activity_ui_host)  LinearLayout activityUiHost;
    @InjectView(R.id.button_rdc_floating) Button selectFloatingDirectoryChooserButton;
    @InjectView(R.id.button_rdc) Button selectDirectoryChooserButton;
    @InjectView(R.id.fragment_host) FrameLayout fragmentHost;
    @InjectView(R.id.info_text) TextView infoText;

    FragmentCounter fragmentCounter;
    File currentRootDirectory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initPermissions();
        initCurrentRootDirectory(savedInstanceState);
        initFragmentCounter(savedInstanceState);
        showOrHideActivityUiHost();
        updateInfoText();
    }

    private void initPermissions() {
        new TedPermission(this)
                .setPermissionListener(this)
                .setDeniedMessage("you can't use sample if denied")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void initCurrentRootDirectory(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentRootDirectory = (File) savedInstanceState.getSerializable("currentRootDirectory");
        }
    }

    private void initFragmentCounter(Bundle savedInstanceState) {
        int fragmentCount = 0;
        if (savedInstanceState != null) {
            fragmentCount = savedInstanceState.getInt("fragmentCount");
        }
        fragmentCounter = new FragmentCounter(fragmentCount);
    }

    private void showOrHideActivityUiHost() {
        if (fragmentCounter.getCount() == 0) {
            activityUiHost.setVisibility(View.VISIBLE);
        } else {
            activityUiHost.setVisibility(View.GONE);
        }
    }

    private void updateInfoText() {
        infoText.setText(currentRootDirectory == null ? "no folder selected":currentRootDirectory.getAbsolutePath());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("fragmentCount", fragmentCounter.getCount());
        outState.putSerializable("currentRootDirectory", currentRootDirectory);
        super.onSaveInstanceState(outState);
    }


    private void addDirectoryChooserFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            DialogFragment directoryChooserFragment = DirectoryChooserFragment.newInstance(currentRootDirectory);
            getSupportFragmentManager().beginTransaction().addToBackStack("RDC").add(R.id.fragment_host, directoryChooserFragment, "RDC").commit();
            fragmentCounter.fragmentAdded();
        }
        showOrHideActivityUiHost();
    }

    private void addDirectoryChooserAsFloatingFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            DialogFragment directoryChooserFragment = DirectoryChooserFragment.newInstance(currentRootDirectory);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            directoryChooserFragment.show(transaction, "RDC");
            fragmentCounter.fragmentAdded();
        }
    }

    @Override
    public void onEvent(OnDirectoryChosenEvent event) {
        currentRootDirectory = event.getFile();
        getSupportFragmentManager().popBackStack();
        fragmentCounter.fragmentRemoved();
        showOrHideActivityUiHost();
        updateInfoText();
    }

    @Override
    public void onEvent(OnDirectoryCancelEvent event) {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        if (fragmentCounter.getCount() == 0) {
            this.finish();
        } else {
            getSupportFragmentManager().popBackStack();
            fragmentCounter.fragmentRemoved();
            showOrHideActivityUiHost();
        }
    }

    @OnClick(R.id.button_rdc_floating)
    public void selectFloatingDirectoryChooserButtonClicked(View view) {
        addDirectoryChooserAsFloatingFragment(null);
    }

    @OnClick(R.id.button_rdc)
    public void selectDirectoryChooserButtonClicked(View view) {
        addDirectoryChooserFragment(null);
    }

    @Override
    public void onPermissionGranted() {

    }

    @Override
    public void onPermissionDenied(ArrayList<String> arrayList) {

    }
}
