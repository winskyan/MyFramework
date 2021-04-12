package com.my.module_settings.ui.test;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.my.library_base.base.BaseFragment;
import com.my.module_settings.R;


public class DFragment extends BaseFragment {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.settings_fragment_d;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }
}
