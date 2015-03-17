package com.turhanoz.android.reactivedirectorychooser.ui;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.software.shell.fab.ActionButton;
import com.turhanoz.android.reactivedirectorychooser.controller.DirectoryController;
import com.turhanoz.android.reactivedirectorychooser.event.CurrentRootDirectoryChangedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryCancelEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OnDirectoryChosenEvent;
import com.turhanoz.android.reactivedirectorychooser.event.OperationFailedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.utils.BusUtils;
import com.turhanoz.reactivedirectorychooser.R;

import java.io.File;

import de.greenrobot.event.EventBus;

public class DirectoryChooserFragment extends DialogFragment implements View.OnClickListener{
    RecyclerView recyclerView;
    TextView cardView;
    ActionButton floatingActionButton;
    Button selectDirectoryButton;

    EventBus bus;
    DirectoryController directoryController;
    File currentRootDirectory;

    public static DirectoryChooserFragment newInstance(File rootDirectory) {
        DirectoryChooserFragment directoryChooserFragment = new DirectoryChooserFragment();

        Bundle args = new Bundle();
        args.putSerializable("rootDirectory", rootDirectory);
        directoryChooserFragment.setArguments(args);

        return directoryChooserFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentRootDirectory(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        BusUtils.register(bus, getActivity());
    }

    @Override
    public void onDestroy() {
        BusUtils.unregister(bus, getActivity());
        super.onDestroy();
    }

    private void setCurrentRootDirectory(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentRootDirectory = (File) savedInstanceState.getSerializable("currentRootDirectory");
        } else {
            setCurrentDirectoryFromArgumentsOrDefault();
        }
    }

    private void setCurrentDirectoryFromArgumentsOrDefault() {
        File rootDirectoryFromArgument = (File) getArguments().getSerializable("rootDirectory");
        if (rootDirectoryFromArgument == null) {

            currentRootDirectory = Environment.getExternalStorageDirectory();
        } else {
            currentRootDirectory = rootDirectoryFromArgument;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("currentRootDirectory", currentRootDirectory);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_directory_chooser, container, false);
        initGui(rootView);
        initBus();
        intController();
        updateDirectoryTree();

        return rootView;
    }

    private void initGui(View rootView) {
        //Can not use ButterKnife on library project !
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view1);
        cardView = (TextView) rootView.findViewById(R.id.info_text);
        floatingActionButton = (ActionButton) rootView.findViewById(R.id.action_button);
        selectDirectoryButton = (Button) rootView.findViewById(R.id.select_button);

        floatingActionButton.setOnClickListener(this);
        selectDirectoryButton.setOnClickListener(this);
    }

    private void initBus() {
        bus = new EventBus();
        bus.register(this);
    }

    private void intController() {
        directoryController = new DirectoryController(getActivity().getApplicationContext(), bus, recyclerView);
        BusUtils.register(bus, directoryController);
    }

    private void updateDirectoryTree() {
        directoryController.onEvent(new UpdateDirectoryTreeEvent(currentRootDirectory));
    }

    public void onEvent(OperationFailedEvent event) {
        Log.d("TAG", getString(R.string.operation_not_allowed));
        Toast.makeText(getActivity(), getString(R.string.operation_not_allowed), Toast.LENGTH_SHORT).show();
    }

    public void onEvent(CurrentRootDirectoryChangedEvent event) {
        currentRootDirectory = event.getCurrentDirectory();
        cardView.setText(event.getCurrentDirectory().toString());
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        bus.post(new OnDirectoryCancelEvent());
        super.onCancel(dialog);
    }

    @Override
    public void onClick(View v) {
        if(v == floatingActionButton){
            floatingActionButtonClicked(v);
        }
        else if(v == selectDirectoryButton){
            selectDirectoryButtonClicked(v);
        }
    }

    private void floatingActionButtonClicked(View view) {
        new PromptDirectoryDialog(getActivity(), bus).createAndShow(currentRootDirectory);
    }

    private void selectDirectoryButtonClicked(View view) {
        bus.post(new OnDirectoryChosenEvent(currentRootDirectory));
        dismiss();
    }
}