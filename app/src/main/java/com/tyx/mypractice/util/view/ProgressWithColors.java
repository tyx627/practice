package com.tyx.mypractice.util.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ProgressWithColors extends BaseProgressWithColors {

    public ProgressWithColors(Context context) {
        super(context);
    }

    public ProgressWithColors(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ProgressWithColors(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public String[] setColors() {
        return new String[]{"#ff0000", "#00ff00", "#0000ff"};
//        return new String[]{"#ff0000"};
    }

    @Override
    public String[] setTexts() {
        return new String[0];
    }

}
