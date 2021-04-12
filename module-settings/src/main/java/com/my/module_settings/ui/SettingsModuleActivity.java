package com.my.module_settings.ui;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.my.library_base.base.BaseActivity;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_base.utils.Utils;
import com.my.module_settings.R;
import com.my.module_settings.R2;

import butterknife.OnClick;

@Route(path = ARouterConstants.PATH_SETTINGS_MAIN_ACTIVITY)
public class SettingsModuleActivity extends BaseActivity {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.settings_activity_settings_module;
    }

    @OnClick({R2.id.settings_test_setings_module_btn})
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.settings_test_setings_module_btn) {
            Utils.aRouteGoto(ARouterConstants.PATH_APP_MAIN_ACTIVITY, null);
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initData() {

    }
}