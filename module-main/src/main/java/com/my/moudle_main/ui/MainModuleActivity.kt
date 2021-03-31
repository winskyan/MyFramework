package com.my.moudle_main.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import com.alibaba.android.arouter.facade.annotation.Route
import com.my.library_base.init.ARouterPath
import com.my.library_base.utils.Utils.aRouteGoto
import com.my.moudle_main.R
import com.my.moudle_main.R2

@Route(path = ARouterPath.MAIN_MAIN_ACTIVITY)
class MainModuleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_main_module)
        ButterKnife.bind(this)
    }

    @OnClick(R2.id.main_test_main_module_btn)
    fun onClick(v: View?) {
        val viewId = v?.id
        if (viewId == R.id.main_test_main_module_btn) {
            aRouteGoto(ARouterPath.SETTINGS_MAIN_ACTIVITY, null)
        }
    }
}