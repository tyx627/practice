package com.tyx.mypractice.util.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ProgressWithColors extends BaseProgressWithColors {

    public ProgressWithColors(Context context) {
        super(context);
        setProgress(500);
    }

    public ProgressWithColors(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setProgress(500);
    }

    public ProgressWithColors(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setProgress(500);
    }

    @Override
    public String[] setColors() {
        return new String[]{"#ff0000", "#00ff00", "#0000ff"};
//        return new String[]{"#ff0000"};
    }

    @Override
    public String[] setTexts() {
        return new String[]{"正常", "偏高", "超高"};
    }

}
