package com.turhanoz.android.reactivedirectorychooser.controller;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.turhanoz.android.reactivedirectorychooser.event.DataSetChangedEvent;
import com.turhanoz.android.reactivedirectorychooser.event.MakeDirectoryEvent;
import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryTree;
import com.turhanoz.android.reactivedirectorychooser.operation.ListDirectoryOperation;
import com.turhanoz.android.reactivedirectorychooser.operation.MakeDirectoryOperation;
import com.turhanoz.android.reactivedirectorychooser.ui.DirectoryAdapter;

import de.greenrobot.event.EventBus;

public class DirectoryController {
    EventBus bus;
    DirectoryTree dataSet;
    RecyclerView.Adapter adapter;

    ListDirectoryOperation listDirectoryOperation;
    MakeDirectoryOperation makeDirectoryOperation;

    public DirectoryController(Context context, EventBus bus, RecyclerView recyclerView) {
        this.bus = bus;
        this.dataSet = new DirectoryTree(bus);

        initOperations();
        configureRecyclerView(context, recyclerView);
    }

    private void initOperations() {
        listDirectoryOperation = new ListDirectoryOperation(dataSet, bus);
        makeDirectoryOperation = new MakeDirectoryOperation(dataSet, bus);
    }

    private void configureRecyclerView(Context context, RecyclerView recyclerView) {
        adapter = new DirectoryAdapter(dataSet.directoryList, bus);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    public void onEvent(UpdateDirectoryTreeEvent event) {
        listDirectoryOperation.compute(event.rootDirectory);
    }

    public void onEvent(MakeDirectoryEvent event) {
        makeDirectoryOperation.compute(event.root, event.name);
    }

    public void onEvent(DataSetChangedEvent event) {
        adapter.notifyDataSetChanged();
    }
}
