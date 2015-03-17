package com.turhanoz.android.reactivedirectorychooser.ui;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.widget.EditText;

import com.turhanoz.android.reactivedirectorychooser.event.MakeDirectoryEvent;
import com.turhanoz.android.reactivedirectorychooser.utils.ConvertUtils;
import com.turhanoz.reactivedirectorychooser.R;

import java.io.File;

import de.greenrobot.event.EventBus;

public class PromptDirectoryDialog {
    final EditText input;
    Context context;
    EventBus bus;

    public PromptDirectoryDialog(Context context, EventBus bus) {
        input = new EditText(context);
        int paddingPx = ConvertUtils.convertDpToPixel(context, 16);
        input.setPadding(paddingPx, paddingPx, paddingPx, paddingPx);
        this.context = context;
        this.bus = bus;
    }

    public AlertDialog createAndShow(final File currentDirectory) {
        return new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.directory_name))
                .setMessage(context.getString(R.string.directory_name_instruction))
                .setView(input)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        Editable value = input.getText();
                        bus.post(new MakeDirectoryEvent(currentDirectory, value.toString()));
                    }
                }).setNegativeButton("Cancel", null)
                .show();
    }
}
