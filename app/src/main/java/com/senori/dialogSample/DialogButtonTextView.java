package com.senori.dialogSample;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class DialogButtonTextView extends AppCompatTextView {

    private OnClickListener mClickListener;

    public DialogButtonTextView(Context context) {
        super(context);
    }

    public DialogButtonTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DialogButtonTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        super.setOnClickListener(l);
        this.mClickListener = l;
    }

    public OnClickListener getOnClickListener() {
        return mClickListener;
    }
}
