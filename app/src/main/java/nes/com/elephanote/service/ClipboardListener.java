package nes.com.elephanote.service;

import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import nes.com.elephanote.model.Note;


public class ClipboardListener extends Service {
    public static List<Note> selectedNotes;
    public static boolean isBubbleVisible = false;
    public static int selectionCount = 0;
    public static ClipboardManager cbManager;

    public void onCreate() {
        super.onCreate();
        final Context context = this;
        cbManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        selectedNotes = new ArrayList<Note>();

        cbManager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if (!isBubbleVisible) {
                    new BackgroundTask(context).execute((Void) null);
                    selectionCount++;
                    selectedNotes.add(new Note(cbManager.getText().toString()));
                } else {              //if bubble_layout created
                    selectionCount++;
                    selectedNotes.add(new Note(cbManager.getText().toString()));
                    Bubble.counterView.setText(selectionCount + "");
                }
            }
        });

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}

