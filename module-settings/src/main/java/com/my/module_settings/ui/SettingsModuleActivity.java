package com.my.module_settings.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.my.library_base.init.ARouterPath;
import com.my.library_base.utils.Utils;
import com.my.module_settings.R;
import com.my.module_settings.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterPath.SETTINGS_MAIN_ACTIVITY)
public class SettingsModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity_settings_module);

        ButterKnife.bind(this);
    }

    @OnClick({R2.id.settings_test_setings_module_btn})
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.settings_test_setings_module_btn) {
            Utils.aRouteGoto(ARouterPath.APP_MAIN_ACTIVITY, null);
        }
    }
}