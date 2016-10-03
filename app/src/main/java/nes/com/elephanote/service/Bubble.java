package nes.com.elephanote.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;

import nes.com.elephanote.R;
import nes.com.elephanote.adaptor.TextPropertyAdapter;

public class Bubble extends Service {
    public static ListPopupWindow listPopupWindow;
    public static TextView counterView;
    private Context context;
    private WindowManager windowManager;
    private View parentlayout;
    private ImageView floatingFaceBubble;
    private ImageButton closeButton;
    private int isClickedTwice = 0;
    private boolean isClicked = false;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        ClipboardListener.isBubbleVisible = true;
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        createBubbleView();
        createSubBubbleView();
        addViewToWindowManager();

    }

    private void createBubbleView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        parentlayout = inflater.inflate(R.layout.bubble_layout, null);
        floatingFaceBubble = (ImageView) parentlayout.findViewById(R.id.bubble_image);
        floatingFaceBubble.setImageResource(R.drawable.elephant);
        parentlayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (closeButton.getVisibility() == View.VISIBLE) {
                    isClickedTwice++;
                    if (isClickedTwice == 2) {
                        closeButton.setVisibility(closeButton.GONE);
                        isClickedTwice = 0;
                    }
                }
                isClicked = !isClicked;
                if (isClicked == true) {
                    displayPopupWindow(view);
                } else {
                    listPopupWindow.dismiss();
                }
            }

        });

        counterView = (TextView) parentlayout.findViewById(R.id.bubble_counter);
        counterView.setText("1");
        closeButton = (ImageButton) parentlayout.findViewById(R.id.bubble_close_button);
        closeButton.setVisibility(closeButton.GONE);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowManager.removeView(parentlayout);
                ClipboardListener.isBubbleVisible = false;
                ClipboardListener.selectionCount = 0;
                TextPropertyAdapter.selectedNotes.clear();
                stopSelf();
            }
        });

    }

    private void createSubBubbleView() {
        listPopupWindow = new ListPopupWindow(Bubble.this);
    }

    private void addViewToWindowManager() {
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 0;
        params.y = 100;
        windowManager.addView(parentlayout, params);
        addParentLayoutListeners(params);
    }

    private void addParentLayoutListeners(final WindowManager.LayoutParams params) {
        try {
            parentlayout.setOnTouchListener(new View.OnTouchListener() {
                private int initialX;
                private int initialY;
                private float initialTouchX;
                private float initialTouchY;

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = event.getRawX();
                            initialTouchY = event.getRawY();
                            break;
                        case MotionEvent.ACTION_UP:
                            break;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int) (event.getRawX() - initialTouchX);
                            params.y = initialY + (int) (event.getRawY() - initialTouchY);
                            windowManager.updateViewLayout(v, params);
                            break;
                    }
                    return false;
                }
            });

            parentlayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    closeButton.setVisibility(closeButton.VISIBLE);
                    return false;
                }
            });

            parentlayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (closeButton.getVisibility() == View.VISIBLE) {
                        isClickedTwice++;
                        if (isClickedTwice == 2) {
                            closeButton.setVisibility(closeButton.GONE);
                            isClickedTwice = 0;
                        }
                    }
                    isClicked = !isClicked;
                    if (isClicked == true) {
                        displayPopupWindow(view);
                    } else {
                        listPopupWindow.dismiss();
                    }
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayPopupWindow(View anchorView) {
        listPopupWindow.setAdapter(new TextPropertyAdapter(context, ClipboardListener.selectedNotes));
        listPopupWindow.setAnchorView(anchorView);
        listPopupWindow.setWidth(500);
        listPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        listPopupWindow.show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
