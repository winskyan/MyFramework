package com.my.module_settings.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.my.library_base.init.ARouterPath
import com.my.library_base.utils.Utils.aRouteGoto
import com.my.module_settings.R
import com.my.module_settings.R2

@Route(path = ARouterPath.SETTINGS_MAIN_ACTIVITY)
class SettingsModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity_settings_module)
        ButterKnife.bind(this)
    }

    @OnClick(R2.id.settings_test_setings_module_btn)
    fun onClick(v: View) {
        val viewId = v.id
        if (viewId == R.id.settings_test_setings_module_btn) {
            aRouteGoto(ARouterPath.APP_MAIN_ACTIVITY, null)
        }
    }
}