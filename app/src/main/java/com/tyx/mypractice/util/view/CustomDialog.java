package com.tyx.mypractice.util.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * 自定义通用的dialog类
 * Created by tyx on 2017/12/1 0001.
 */

public class CustomDialog extends Dialog {

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView();
    }

    public static final class Builder{

        private Context mContext;
        private View contentView;   // 自定义的dialog布局
        private String title;       // dialog的标题
        private int titleId;        // dialog的标题的布局id
        private int titleStyleId;   // dialog的标题的样式id
        private String content;     // dialog的内容
        private int contentId;      // dialog的内容的布局id
        private int contentStyleId; // dialog的内容的样式id
        private String positiveText;    // 确定按钮的文字
        private int positiveId;         // 确定按钮的文字的布局id
        private int positiveStyleId;    // 确定按钮的文字的样式id
        private String negativeText;    // 取消按钮的文字
        private int negativeId;         // 取消按钮的文字的布局id
        private int negativeStyleId;    // 取消按钮的文字的样式id
        private DialogClickListener clickListener;  // 按钮的回调接口
        private int dialogStyleId;      // dialog的样式

        public Builder(Context context){
            this.mContext = context;
        }

        public Builder(Context context, View view){
            this.mContext = context;
            this.contentView = view;
        }

        public Builder(Context context, int resId){
            this.mContext = context;
            this.contentView = LayoutInflater.from(context).inflate(resId, null);
        }

        /**
         * 设置title，使用默认样式
         * @param resId title的布局id
         * @param title dialog的title
         * @return builder
         */
        public Builder setTitle(int resId, String title){
            this.titleId = resId;
            this.title = title;
            return this;
        }

        /**
         * 设置title，使用自定义样式
         * @param resId title的布局id
         * @param title dialog的title
         * @param themeResId 自定义样式
         * @return builder
         */
        public Builder setTitle(int resId, String title, int themeResId){
            this.titleId = resId;
            this.title = title;
            this.titleStyleId = themeResId;
            return this;
        }

        /**
         * 设置title，使用默认样式
         * @param resId title的布局id
         * @param titleResId dialog的title的资源id
         * @return builder
         */
        public Builder setTitle(int resId, int titleResId){
            this.titleId = resId;
            this.title = mContext.getResources().getString(titleResId);
            return this;
        }

        /**
         * 设置title，使用自定义样式
         * @param resId title的布局id
         * @param titleResId dialog的title的资源id
         * @param themeResId 自定义样式
         * @return builder
         */
        public Builder setTitle(int resId, int titleResId, int themeResId){
            this.titleId = resId;
            this.title = mContext.getResources().getString(titleResId);
            this.titleStyleId = themeResId;
            return this;
        }

        /**
         * 设置content，使用默认样式
         * @param resId content的布局id
         * @param content dialog的content
         * @return builder
         */
        public Builder setContent(int resId, String content){
            this.contentId = resId;
            this.content = content;
            return this;
        }

        /**
         * 设置content，使用自定义样式
         * @param resId content的布局id
         * @param content dialog的content
         * @param themeResId 自定义样式
         * @return builder
         */
        public Builder setContent(int resId, String content, int themeResId){
            this.contentId = resId;
            this.content = content;
            this.contentStyleId = themeResId;
            return this;
        }

        /**
         * 设置content，使用默认样式
         * @param resId content的布局id
         * @param contentResId dialog的content的资源id
         * @return builder
         */
        public Builder setContent(int resId, int contentResId){
            this.contentId = resId;
            this.content = mContext.getResources().getString(contentResId);
            return this;
        }

        /**
         * 设置content，使用自定义样式
         * @param resId content的布局id
         * @param contentResId dialog的content的资源id
         * @param themeResId 自定义样式
         * @return builder
         */
        public Builder setContent(int resId, int contentResId, int themeResId){
            this.contentId = resId;
            this.content = mContext.getResources().getString(contentResId);
            this.contentStyleId = themeResId;
            return this;
        }

        /**
         * 设置确定按钮，使用默认样式
         * @param resId 确定按钮的id
         * @param positiveText 确定按钮的文字
         * @return builder
         */
        public Builder setPositiveText(int resId, String positiveText){
            this.positiveId = resId;
            this.positiveText = positiveText;
            return this;
        }

        /**
         * 设置确定按钮，使用自定义样式
         * @param resId 确定按钮的id
         * @param positiveText 确定按钮的文字
         * @param themeResId 自定义样式
         * @return
         */
        public Builder setPositiveText(int resId, String positiveText, int themeResId){
            this.positiveId = resId;
            this.positiveText = positiveText;
            this.positiveStyleId = themeResId;
            return this;
        }

        /**
         * 设置确定按钮，使用默认样式
         * @param resId 确定按钮的id
         * @param positiveTextResId 确定按钮的文字的资源id
         * @return builder
         */
        public Builder setPositiveText(int resId, int positiveTextResId){
            this.positiveId = resId;
            this.positiveText = mContext.getResources().getString(positiveTextResId);
            return this;
        }

        /**
         * 设置确定按钮，使用自定义样式
         * @param resId 确定按钮的id
         * @param positiveTextResId 确定按钮的文字的资源id
         * @param themeResId 自定义样式
         * @return
         */
        public Builder setPositiveText(int resId, int positiveTextResId, int themeResId){
            this.positiveId = resId;
            this.positiveText = mContext.getResources().getString(positiveTextResId);
            this.positiveStyleId = themeResId;
            return this;
        }

        /**
         * 设置确定按钮的点击事件
         * @param clickListener 确定按钮的监听
         * @return builder
         */
        public Builder setPositiveListener(DialogClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }

        /**
         * 设置取消按钮，使用默认样式
         * @param resId 取消按钮的id
         * @param negativeText 取消按钮的文字
         * @return builder
         */
        public Builder setNegativeText(int resId, String negativeText){
            this.negativeId = resId;
            this.negativeText = negativeText;
            return this;
        }

        /**
         * 设置取消按钮，使用自定义样式
         * @param resId 取消按钮的id
         * @param negativeText 取消按钮的文字
         * @param themeResId 自定义样式
         * @return
         */
        public Builder setNegativeText(int resId, String negativeText, int themeResId){
            this.negativeId = resId;
            this.negativeText = negativeText;
            this.negativeStyleId = themeResId;
            return this;
        }

        /**
         * 设置取消按钮，使用默认样式
         * @param resId 取消按钮的id
         * @param negativeTextResId 取消按钮的文字的资源id
         * @return builder
         */
        public Builder setNegativeText(int resId, int negativeTextResId){
            this.negativeId = resId;
            this.negativeText = mContext.getResources().getString(negativeTextResId);
            return this;
        }

        /**
         * 设置取消按钮，使用自定义样式
         * @param resId 取消按钮的id
         * @param negativeTextResId 取消按钮的文字的资源id
         * @param themeResId 自定义样式
         * @return
         */
        public Builder setNegativeText(int resId, int negativeTextResId, int themeResId){
            this.negativeId = resId;
            this.negativeText = mContext.getResources().getString(negativeTextResId);
            this.negativeStyleId = themeResId;
            return this;
        }

        /**
         * 设置取消按钮的点击事件
         * @param clickListener 取消按钮的监听
         * @return builder
         */
        public Builder setNegativeListener(DialogClickListener clickListener){
            this.clickListener = clickListener;
            return this;
        }

        /**
         * 设置dialog的样式
         * @param styleId dialog的样式
         * @return builder
         */
        public Builder setDialogStyle(int styleId){
            this.dialogStyleId = styleId;
            return this;
        }

        /**
         * 构建dialog
         * @return
         */
        public CustomDialog build(){
            final CustomDialog dialog;
            if (dialogStyleId != 0){
                dialog = new CustomDialog(mContext, dialogStyleId);
            } else {
                dialog = new CustomDialog(mContext);
            }

            dialog.setContentView(contentView);
            TextView tv_title = (TextView) contentView.findViewById(titleId);
            if (null != tv_title) {
                if (titleStyleId != 0){
                    tv_title.setTextAppearance(mContext, titleStyleId);
                }
                if (TextUtils.isEmpty(title)) {
                    tv_title.setVisibility(View.GONE);
                } else {
                    tv_title.setVisibility(View.VISIBLE);
                    tv_title.setText(title);
                }
            }
            TextView tv_content = contentView.findViewById(contentId);
            if (null != tv_content) {
                if (contentStyleId != 0) {
                    tv_content.setTextAppearance(mContext, contentStyleId);
                }
                if (TextUtils.isEmpty(content)){
                    tv_content.setVisibility(View.GONE);
                } else {
                    tv_content.setVisibility(View.VISIBLE);
                    tv_content.setText(content);
                }
            }
            final TextView tv_positive = contentView.findViewById(positiveId);
            if (null != tv_positive) {
                if (positiveStyleId != 0) {
                    tv_positive.setTextAppearance(mContext, positiveStyleId);
                }
                if (TextUtils.isEmpty(positiveText)){
                    tv_positive.setVisibility(View.GONE);
                } else {
                    tv_positive.setVisibility(View.VISIBLE);
                    tv_positive.setText(positiveText);
                    if (null != clickListener){
                        tv_positive.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                clickListener.click(dialog, tv_positive);
                            }
                        });
                    }
                }
            }
            final TextView tv_negative = contentView.findViewById(negativeId);
            if (null != tv_negative) {
                if (negativeStyleId != 0) {
                    tv_negative.setTextAppearance(mContext, negativeStyleId);
                }
                if (TextUtils.isEmpty(negativeText)){
                    tv_negative.setVisibility(View.GONE);
                } else {
                    tv_negative.setVisibility(View.VISIBLE);
                    tv_negative.setText(negativeText);
                    if (null != clickListener){
                        tv_negative.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                clickListener.click(dialog, tv_negative);
                            }
                        });
                    }
                }
            }

            return dialog;
        }

    }

    public interface DialogClickListener{
        void click(CustomDialog dialog, View view);
    }
}
