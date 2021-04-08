package com.my.library_ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TitleBar extends ConstraintLayout {
    private ImageView backView;
    private TextView titleView;
    private OnBackClick onBackClick;

    public TitleBar(@NonNull Context context) {
        super(context);
        initView(context);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.ui_title_bar, this);
        backView = view.findViewById(R.id.ui_title_bar_back);
        titleView = view.findViewById(R.id.ui_title_bar_title);

        backView.setOnClickListener(v -> {
            if (null != onBackClick) {
                onBackClick.onBackClick();
            }
        });
    }

    public void setTitle(String title) {
        if (null != titleView) {
            titleView.setText(title);
        }
    }

    public void setOnBackClick(OnBackClick onBackClick) {
        this.onBackClick = onBackClick;
    }

    public interface OnBackClick {
        void onBackClick();
    }
}
