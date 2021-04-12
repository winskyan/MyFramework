package com.my.library_base.base;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.my.library_base.R;
import com.zackratos.ultimatebarx.ultimatebarx.UltimateBarX;

public class BaseInit {
    public static void initStatusBar(FragmentActivity activity) {
        UltimateBarX.with(activity)
                .color(activity.getResources().getColor(R.color.status_bar_bg))
                .fitWindow(true)
                .light(false)
                .applyStatusBar();
    }

    public static void initTitleBar(AppCompatActivity activity) {
        if (activity.getSupportActionBar() != null) {
            activity.getSupportActionBar().hide();
        }
    }
}
