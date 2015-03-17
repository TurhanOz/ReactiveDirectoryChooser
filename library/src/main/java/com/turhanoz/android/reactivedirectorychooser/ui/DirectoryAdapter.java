package com.turhanoz.android.reactivedirectorychooser.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryList;

import java.io.File;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.greenrobot.event.EventBus;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {
    final EventBus bus;
    private DirectoryList dataSet;

    public class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(android.R.id.text1) TextView textView;

        public ViewHolder(View root) {
            super(root);
            ButterKnife.inject(this, root);
        }

        @OnClick(android.R.id.text1)
        public void fileClicked(View view) {
            bus.post(new UpdateDirectoryTreeEvent(dataSet.get(getPosition())));
        }
    }

    public DirectoryAdapter(DirectoryList dataSet, EventBus bus) {
        this.dataSet = dataSet;
        this.bus = bus;
    }

    @Override
    public DirectoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View root = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_selectable_list_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(root);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        File file = dataSet.get(position);
        holder.textView.setText(file.getName());
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}