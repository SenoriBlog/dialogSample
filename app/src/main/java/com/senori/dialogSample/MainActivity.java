package com.senori.dialogSample;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private View dialogView = null;
    Handler mainHandler = null;

    /**
     * アクティビティ生成時
     * @param savedInstanceState 状態保存情報(未使用)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainHandler = new Handler(Looper.getMainLooper());
        setContentView(R.layout.activity_main);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(buttonListener);
    }

    /**
     * ボタンリスナー
     */
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View.OnClickListener positiveListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeCustomDialog();
                }
            };
            View.OnClickListener negativeListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    closeCustomDialog();
                }
            };
            showCustomDialog(
                    "Hello Android!",
                    getResources().getString(R.string.general_OK),
                    getResources().getString(R.string.general_cancel),
                    positiveListener,
                    negativeListener);
        }
    };

    /**
     * カスタムダイアログ表示
     */
    @SuppressLint("ClickableViewAccessibility")
    private void showCustomDialog(final String message,
                                  final String positiveButtonText,
                                  final String negativeButtonText,
                                  final View.OnClickListener positiveListener,
                                  final View.OnClickListener negativeListener) {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                dialogView = inflater.inflate(R.layout.dialog_layout,null);
                addContentView(dialogView, new LinearLayout.LayoutParams
                        (ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

                LinearLayout dialogLayout = (LinearLayout) dialogView.findViewById(R.id.dialog_layout);

                TextView messageTextView = dialogView.findViewById(R.id.dialogMessage);
                DialogButtonTextView negativeTextView = dialogView.findViewById(R.id.negativeText);
                DialogButtonTextView positiveTextView = dialogView.findViewById(R.id.positiveText);

                if (message != null) {
                    messageTextView.setText(message);
                    messageTextView.requestFocus();
                } else {
                    messageTextView.setVisibility(View.GONE);
                }

                if (positiveButtonText == null && negativeButtonText == null) {
                    LinearLayout textArea = (LinearLayout) dialogView.findViewById(R.id.dialogTextArea);
                    textArea.setVisibility(View.GONE);
                } else {
                    if (positiveButtonText != null) {
                        positiveTextView.setText(positiveButtonText);
                        positiveTextView.setOnClickListener(positiveListener);
                        positiveTextView.requestFocus();
                    } else {
                        positiveTextView.setVisibility(View.GONE);
                    }

                    if (negativeButtonText != null) {
                        negativeTextView.setText(negativeButtonText);
                        negativeTextView.setOnClickListener(negativeListener);
                        negativeTextView.requestFocus();
                    } else {
                        negativeTextView.setVisibility(View.GONE);
                    }
                }

                dialogLayout.setOnTouchListener(new View.OnTouchListener(){
                    @Override
                    public boolean onTouch (View v, MotionEvent event) {
                        // ダイアログ以外の部分のタッチを無効
                        return true;
                    }
                });
            }
        });
    }

    /**
     * カスタムダイアログClose
     */
    public void closeCustomDialog() {
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                if (dialogView != null) {
                    ViewGroup parent = (ViewGroup) dialogView.getParent();
                    parent.removeView(dialogView);
                    dialogView = null;
                }
            }
        });
    }
}