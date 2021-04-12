package com.my.library_ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.my.library_base.base.BaseActivity;
import com.my.library_base.constants.ARouterConstants;
import com.my.library_ui.R;
import com.my.library_ui.R2;
import com.my.library_ui.TitleBar;

import butterknife.BindView;

@Route(path = ARouterConstants.PATH_UI_WEB_VIEW)
public class WebViewActivity extends BaseActivity {
    @BindView(R2.id.ui_webview)
    LinearLayout mLayout;

    @BindView(R2.id.ui_titlebar)
    TitleBar mTitleBar;

    @Autowired(name = ARouterConstants.ARGS_URL)
    String url;

    private AgentWeb mAgentWeb;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.ui_activity_webview;
    }

    @Override
    public void resume() {
        mTitleBar.setOnBackClick(() -> finish());
    }

    @Override
    public void destroy() {

    }

    @Override
    public void initData() {
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) mLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);

        //获取网页的标题
        mAgentWeb.getWebCreator().getWebView().setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (!TextUtils.isEmpty(title)) {
                    mTitleBar.setTitle(title);
                }
                super.onReceivedTitle(view, title);
            }
        });
    }
}
