package com.turhanoz.android.reactivedirectorychooser.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.turhanoz.android.reactivedirectorychooser.event.UpdateDirectoryTreeEvent;
import com.turhanoz.android.reactivedirectorychooser.model.DirectoryList;
import com.turhanoz.reactivedirectorychooser.R;

import java.io.File;

import de.greenrobot.event.EventBus;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryAdapter.ViewHolder> {
    final EventBus bus;
    private DirectoryList dataSet;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView;

        public ViewHolder(View root) {
            super(root);
            textView = (TextView) root.findViewById(R.id.text1);
            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            bus.post(new UpdateDirectoryTreeEvent(dataSet.get(getAdapterPosition())));
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
                .inflate(R.layout.item, parent, false);
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