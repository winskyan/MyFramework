package com.my.moudle_main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.my.library_base.base.BaseActivity;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_base.utils.Utils;
import com.my.module_settings.ui.test.AFragment;
import com.my.module_settings.ui.test.BFragment;
import com.my.module_settings.ui.test.CFragment;
import com.my.module_settings.ui.test.DFragment;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

@Route(path = ARouterConstants.PATH_MAIN_MAIN_ACTIVITY)
public class MainModuleActivity extends BaseActivity {


    @BindView(R2.id.main_navigationBar)
    EasyNavigationBar navigationBar;

    private String[] tabText = {"首页", "发现", "消息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me};
    //选中时icon
    private int[] selectIcon = {R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1};

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.main_activity_main_module;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void initData() {
        tabText = new String[]{Utils.getStringByResId(getContext(), R.string.tab1), Utils.getStringByResId(getContext(), R.string.tab2)
                , Utils.getStringByResId(getContext(), R.string.tab3), Utils.getStringByResId(getContext(), R.string.tab4)};
        //未选中icon
        normalIcon = new int[]{
                R.mipmap.index, R.mipmap.find, R.mipmap.message, R.mipmap.me
        };
        //选中时icon
        selectIcon = new int[]{
                R.mipmap.index1, R.mipmap.find1, R.mipmap.message1, R.mipmap.me1
        };

        fragments.add(new AFragment());
        fragments.add(new BFragment());
        fragments.add(new CFragment());
        fragments.add(new DFragment());

        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .smoothScroll(true)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();
    }
}