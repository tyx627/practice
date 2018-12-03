package com.tyx.mypractice.util.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.tyx.mypractice.R;
import com.tyx.mypractice.util.CommonUtil;

/**
 * 横向的选择器
 * @author tyx
 */
public class HorizontalPickerView extends View {

    private Context mContext;
    /**
     * 选中的item的字体颜色
     */
    private int selectColor;
    /**
     * item与item之间的间隔
     */
    private float itemInterval;
    private Paint normalTxtPaint;
    private float txtSize;
    private Paint selectTxtPaint;

    public HorizontalPickerView(Context context) {
        this(context, null);
    }

    public HorizontalPickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalPickerView);
        selectColor = typedArray.getColor(R.styleable.HorizontalPickerView_selectColor, Color.parseColor("#FF789E"));
        itemInterval = typedArray.getDimension(R.styleable.HorizontalPickerView_itemInterval, CommonUtil.dp2px(mContext, 39));
        txtSize = typedArray.getDimension(R.styleable.HorizontalPickerView_txtSize, CommonUtil.sp2px(mContext, 20));
        typedArray.recycle();
        initView();
    }

    private void initView() {
        initPaint();

    }

    private void initPaint() {
        // 初始化画笔，普通状态的item的文字画笔
        normalTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        normalTxtPaint.setTextAlign(Paint.Align.CENTER);
        normalTxtPaint.setColor(Color.parseColor("#CCCCCC"));
        normalTxtPaint.setTextSize(txtSize);
        // 初始化画笔，选中状态的item的文字画笔
        selectTxtPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        selectTxtPaint.setTextAlign(Paint.Align.CENTER);
        selectTxtPaint.setColor(selectColor);
        selectTxtPaint.setTextSize(txtSize);
        // 选中之后，字体要稍微变大点
        selectTxtPaint.setTextScaleX(1.2f);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (MeasureSpec.AT_MOST == widthMode || MeasureSpec.UNSPECIFIED == widthMode){

        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
    }
}
