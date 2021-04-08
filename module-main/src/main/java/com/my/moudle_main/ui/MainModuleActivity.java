package com.my.moudle_main.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_base.utils.Utils;
import com.my.moudle_main.R;
import com.my.moudle_main.R2;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = ARouterConstants.PATH_MAIN_MAIN_ACTIVITY)
public class MainModuleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_main_module);

        ButterKnife.bind(this);
    }

   @OnClick({R2.id.main_test_main_module_btn})
    public void onClick(View v) {
        int viewId = v.getId();
       if (viewId == R.id.main_test_main_module_btn) {
           Utils.aRouteGoto(ARouterConstants.PATH_SETTINGS_MAIN_ACTIVITY, null);
       }

    }
}